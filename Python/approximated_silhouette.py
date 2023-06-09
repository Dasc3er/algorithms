import functools
import math
from typing import Union

import numpy as np
import pandas as pd
from sklearn.cluster import KMeans
from sklearn.metrics import pairwise_distances_chunked
from sklearn.preprocessing import LabelEncoder
from tqdm.notebook import tqdm


def pps_silhouette(
        V: pd.DataFrame,
        C: KMeans,
        t: int = 1024,
        delta: float = 0.01,
        c: int = 1,
        epsilon: float = None,
        metric: str = "euclidean",
        show_progress=True,
):
    """
    Approximated silhouette via the Probability Proportional to Size methodology.
    Code partially based on scikit-learn function sklearn.metrics.silhouette_score, to compute the distances in a
    faste, pre-built way.

    This function returns an approximation of the silhouette such that with probability at least 1 − delta the error
    to the true silhouette is a mere additive O(epsilon).

    The original algorithm works by defining a constant c which is combined to epsilon to determine the sample size t
    for each cluster.
    Following the methodology defined by the paper itself, the direct definition of the sample size is more practical.

    :param show_progress:
    :param metric:
    :source: https://arxiv.org/abs/2003.01430

    :param V: Pandas DataFrame of shape (n_samples, n_features), containing the points
    :param C: Complete clustering object (KMeans)
    :param t: Expected sample size
    :param c: Chosen internal constant
    :param epsilon: PPS error threshold
    :param delta: PPS probability threshold
    :return:
    """
    # Compute the sample size if epsilon is defined
    if epsilon is not None:
        t = compute_sample_size(V, C, epsilon, delta, c)
    else:
        epsilon = compute_epsilon(V, C, t, delta, c)

    # Check the input values
    if t <= 0:
        raise ValueError("Invalid sample size")

    if c <= 0:
        raise ValueError("Invalid chosen constant size")

    if epsilon < 0 or epsilon > 1:
        raise ValueError("Invalid error threshold")

    if delta < 0 or delta > 1:
        raise ValueError("Invalid probability threshold")

    # Parameter identification
    k = C.n_clusters
    n = V.shape[0]

    # Label standardization
    label_encoder = LabelEncoder()
    labels = label_encoder.fit_transform(C.labels_)
    cluster_sizes = np.bincount(labels)

    # STEP 1: Sample selection based on a Probability Proportional to Size methodology
    # Define probability for each element (p_e) in a single vector
    p = np.ones(n)

    # Define vector of selected elements for the sampling
    S = np.zeros(n, dtype=np.bool8)

    base_selection_prob = 2 * math.log(2 * k / delta, math.e)
    for cluster_id, cluster_size in enumerate(cluster_sizes):
        cluster_elements_index = np.where(labels == cluster_id)

        # If the size of the required sample is greater than the sieze of the cluster, select all elements
        if t >= cluster_size:
            S[cluster_elements_index] = 1
            continue

        # Otherwise, sample some elements via Poisson uniform random sampling
        prob = base_selection_prob / cluster_size
        random_values = np.random.rand(cluster_size)

        S_0_index = cluster_elements_index[0][np.where(random_values <= prob)]

        # Compute the local distances to the selected samples
        local_distances_results = list(pairwise_distances_chunked(
            V.loc[cluster_elements_index],
            V.loc[S_0_index],
            metric=metric
        ))
        local_distances = np.concatenate(local_distances_results)

        # Compute the "weights" for all samples via the sum of all distances
        W_C = local_distances.sum(axis=0)

        gamma_values_weighted = (local_distances / W_C).max(axis=1)
        min_gamma = np.full(cluster_size, 1 / cluster_size)
        gamma_values = np.vstack((
            gamma_values_weighted,
            min_gamma
        )).max(axis=0)

        prob_values = np.vstack((
            t * gamma_values,
            np.ones(cluster_size)
        )).min(axis=0)
        p[cluster_elements_index] = prob_values

        # Final sampling based on the new probabilities
        random_values = np.random.rand(cluster_size)
        selected_indexes = cluster_elements_index[0][np.where(random_values <= prob_values)]
        S[selected_indexes] = 1

    p_bar = None
    if show_progress:
        p_bar = tqdm(range(n), leave=False)

    # STEP 2: compute the silhouette
    # Identify which elements where selected as samples
    selected_samples = np.where(S)

    reduce_func = functools.partial(
        _reduce_weighted_distances,
        n_clusters=k,
        probs=p[selected_samples],
        labels=labels[selected_samples],
        p_bar=p_bar,
    )

    # Storage for all silhouettes
    silhouettes = np.zeros(n)
    for cluster_id, cluster_size in enumerate(cluster_sizes):
        # Identify which elements are in the cluster
        cluster_elements_index = np.where(labels == cluster_id)

        # Compute the distances for each element to the selected samples in chunks
        # This divides the cluster elements in chunks, computing all the distances to the selected samples
        distances_results = list(pairwise_distances_chunked(
            V.loc[cluster_elements_index],
            V.loc[selected_samples],
            reduce_func=reduce_func,
            metric=metric
        ))
        # Combine the weighted distances in a single vector
        W_hat = np.concatenate(distances_results)

        # Compute the within cluster distances approximated via the weighted distances
        a_hat = W_hat[:, cluster_id] / (cluster_size - 1)

        # Compute the between cluster distances approximated via the weighted distances
        W_hat[:, cluster_id] = np.inf
        b_hat = (W_hat / cluster_sizes).min(axis=1)

        # Compute the approximated silhouette for the elements of the cluster
        s_hat = (b_hat - a_hat) / np.vstack((
            a_hat,
            b_hat
        )).max(axis=0)

        # Store the silhouettes (as a way to check dimensions)
        silhouettes[cluster_elements_index[0]] = s_hat

    # Compute the final silhouette value
    silhouette = silhouettes.sum() / n

    # Fix progress bar uncompleted
    if show_progress:
        p_bar.update(p_bar.total - p_bar.n)

    return silhouette


def get_cluster_sizes(C: KMeans):
    """
    Returns the cluster sizes for the clustering.
    :param C:
    :return:
    """
    return np.bincount(C.labels_)


def compute_epsilon(
        V: Union[pd.DataFrame, int],
        C: Union[KMeans, int],
        t: int,
        delta: float = 0.01,
        c: int = 1,
):
    """
    Compute the expected epsilon starting from the parameters of the PPS algorithm.

    :param V: Pandas DataFrame of shape (n_samples, n_features), containing the points
    :param C: Complete clustering object (KMeans)
    :param c: Chosen internal constant
    :param t: Expected sample size
    :param delta: PPS probability threshold
    :return: PPS error threshold
    """
    k = C.n_clusters if isinstance(C, KMeans) else C
    n = V.shape[0] if isinstance(V, pd.DataFrame) else V

    epsilon = math.sqrt(c * math.log(4 * n * k / delta, math.e) / (2 * t))

    return epsilon


def compute_error_upper_bound(
        epsilon: float,
):
    """
    Compute the error bound for the approximated silhouette with respect to the standard one, from the
    parameters of the PPS algorithm.
    Given s_hat approximated silhouette and s standard one: |s_hat - s| < 4 * epsilon / (1 - epsilon)

    Note that this error bound is reached with probability at least 1 - delta by the algorithm executed with the
    defined epsilon, custom delta and a suitable choice of constant c > 0.

    :param epsilon: PPS error threshold
    :return: Error bound
    """

    return 4 * epsilon / (1 - epsilon)


def compute_epsilon_from_error_upper_bound(
        error_bound: float,
):
    """
    Compute the epsilon starting from the error bound for the approximated silhouette with respect to the standard one,
    from the parameters of the PPS algorithm.

    :param error_bound: Error bound
    :return: Epsilon
    """

    return error_bound / (4 + error_bound)


def compute_sample_size(
        V: Union[pd.DataFrame, int],
        C: Union[KMeans, int],
        epsilon: float,
        delta: float = 0.01,
        c: int = 1,
):
    """
    Compute the expected sample size for each cluster starting from the standard parameters of the PPS algorithm.

    :param V: Pandas DataFrame of shape (n_samples, n_features), containing the points
    :param C: Complete clustering object (KMeans)
    :param c: Chosen internal constant
    :param epsilon: PPS error threshold
    :param delta: PPS probability threshold
    :return: Expected sample size
    """
    k = C.n_clusters if isinstance(C, KMeans) else C
    n = V.shape[0] if isinstance(V, pd.DataFrame) else V

    t = math.ceil(c / (2 * epsilon ** 2) * math.log(4 * n * k / delta, math.e))

    return t


def _reduce_weighted_distances(D_chunk, start, n_clusters, probs, labels, p_bar):
    clust_dists = np.zeros(
        (len(D_chunk), n_clusters),
        dtype=D_chunk.dtype
    )

    if p_bar is not None:
        p_bar.update(D_chunk.shape[0])

    weighted_distances = D_chunk / probs
    for i in range(len(D_chunk)):
        clust_dists[i] += np.bincount(
            labels,
            weights=weighted_distances[i],
            minlength=n_clusters
        )

    return clust_dists
