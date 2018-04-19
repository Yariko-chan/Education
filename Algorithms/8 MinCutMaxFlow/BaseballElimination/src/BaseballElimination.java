
import edu.princeton.cs.algs4.In;
import sun.rmi.runtime.Log;

public class BaseballElimination {
    private String[] names;
    private int[] w;
    private int[] l;
    private int[] r;
    private int[][] g;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {

        System.out.println("start parsing " + filename);
        In in = new In(filename);
        if (in.hasNextLine()) {
            int count = Integer.valueOf(in.readLine());
            w = new int[count];
            l = new int[count];
            r = new int[count];
            g = new int[count][count];

            for (int i = 0; i < count; i++) {
                if (!in.hasNextLine()) {
                    throw new IllegalArgumentException("Not enough lines, count is " + count +
                            ", file ends after " + i + " line");
                }
                String line = in.readLine();
                System.out.println("Line: " + line);

                String[] parts = line.split(" ");
                names[i] = parts[0];
                w[i] = Integer.valueOf(parts[1]);
                l[i] = Integer.valueOf(parts[2]);
                r[i] = Integer.valueOf(parts[3]);
                for (int j = 0; j < count; j++) {
                    g[i][j] = Integer.valueOf(parts[4 + j]);
                }
                System.out.print(" Parsed: " + names[i] + " " + w[i] + " " + l[i] + " " + r[i]);
                for (int j = 0; j < count; j++) {
                    System.out.print(" " + g[i][j]);
                }
                System.out.println();
            }
        }
        System.out.println("end");
    }

    // number of teams
    public int numberOfTeams() {
        return 0;
    }

    // all teams
    public Iterable<String> teams() {
        return null;
    }

    // number of wins for given team
    public int wins(String team) {
        return 0;
    }

    // number of losses for given team
    public int losses(String team) {

        return 0;
    }

    // number of remaining games for given team
    public int remaining(String team) {

        return 0;
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {

        return 0;
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {

        return false;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        return null;
    }

    public static void main(String[] args) {
        BaseballElimination be = new BaseballElimination(args[0]);
    }
}
