import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ParallelAnalysis {
    public class Instruction {
        protected int[] input;
        protected int output;

        protected int time;

        public Instruction(int[] input, int output) {
            this.input = input;
            this.output = output;

            time = 0;
        }

        public int[] getInput() {
            return this.input;
        }

        public int getOutput() {
            return this.output;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getTime() {
            return this.time;
        }
    }

    public class Processor {
        protected ArrayList<ArrayList<Instruction>> cron;
        protected int first_available;
        protected int cores;

        public Processor(int cores) {
            this.cron = new ArrayList<ArrayList<Instruction>>();

            this.first_available = 0;
            this.cores = cores;
        }

        public void add(Instruction in) {
            this.first_available = this.add(this.first_available, in);
        }

        public int getTotalTime() {
            return cron.size();
        }

        public int add(int time, Instruction in) {
            int level = time - 1;
            ArrayList<Instruction> current;

            // Individuazione primo livello libero
            do {
                level++;

                try {
                    current = this.cron.get(level);
                } catch (IndexOutOfBoundsException e) {
                    current = new ArrayList<Instruction>();
                    this.cron.add(current);
                }
            } while (this.cron.get(level).size() >= this.cores);
            current.add(in);

            in.setTime(level);

            return level;
        }
    }

    protected Instruction[] instructions;
    protected Processor processor;

    public ParallelAnalysis(int id, String[] rows) {
        instructions = new Instruction[rows.length];

        processor = new Processor(rows.length);

        String[] pieces;
        for (int i = 0; i < rows.length; i++) {
            pieces = rows[i].split(" ");

            int[] input = new int[pieces.length - 2];
            for (int k = 1; k < pieces.length - 1; k++) {
                input[k - 1] = Integer.parseInt(pieces[k]);
            }

            int output = Integer.parseInt(pieces[pieces.length - 1]);

            instructions[i] = new Instruction(input, output);
        }
    }

    public int execute() {
        HashMap<Integer, Instruction> vars = new HashMap<Integer, Instruction>();

        for (int i = 0; i < instructions.length; i++) {
            int max_time = 0;

            // Controllo di dipendenza
            for (int var : instructions[i].getInput()) {
                Instruction prev = vars.get(var);

                if (prev != null) {
                    int time = vars.get(var).getTime() + 1;

                    if (time > max_time) {
                        max_time = time;
                    }
                }
            }

            processor.add(max_time, instructions[i]);

            vars.put(instructions[i].getOutput(), instructions[i]);
        }

        return processor.getTotalTime();
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int times = scan.nextInt();
        scan.nextLine();

        ParallelAnalysis p;
        for (int time = 0; time < times; time++) {
            int number = scan.nextInt();
            scan.nextLine();

            String[] rows = new String[number];
            for (int i = 0; i < number; i++) {
                rows[i] = scan.nextLine();
            }

            p = new ParallelAnalysis(time + 1, rows);

            System.out.println((time + 1) + " " + p.execute());
        }
    }
}