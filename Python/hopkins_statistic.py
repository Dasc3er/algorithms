from random import sample

import faiss
import numpy as np
from numpy.random import uniform


# function to compute hopkins's statistic for the dataframe X
def hopkins_statistic(X):
    X = np.ascontiguousarray(X.values)  # convert dataframe to a numpy array
    sample_size = int(X.shape[0] * 0.05)  # 0.05 (5%) based on paper by Lawson and Jures

    # a uniform random sample in the original data space
    X_uniform_random_sample = uniform(X.min(axis=0), X.max(axis=0), (sample_size, X.shape[1]))

    # a random sample of size sample_size from the original data X
    random_indices = sample(range(0, X.shape[0], 1), sample_size)
    X_sample = X[random_indices]

    # initialise unsupervised learner for implementing neighbor searches
    index = faiss.IndexFlatL2(X.shape[1])
    index.add(X.astype(np.float32))

    # u_distances = nearest neighbour distances from uniform random sample
    u_distances, u_indices = index.search(X_uniform_random_sample.astype(np.float32), 1)
    u_distances = np.sqrt(u_distances)

    u_distances = u_distances[:, 0]  # distance to the first (nearest) neighbour

    # w_distances = nearest neighbour distances from a sample of points from original data X
    w_distances, w_indices = index.search(X_sample.astype(np.float32), 2)
    w_distances = np.sqrt(w_distances)
    # distance to the second nearest neighbour (as the first neighbour will be the point itself, with distance = 0)
    w_distances = w_distances[:, 1]

    u_sum = np.sum(u_distances)
    w_sum = np.sum(w_distances)

    # compute and return hopkins' statistic
    H = u_sum / (u_sum + w_sum)

    return H
