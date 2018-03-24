import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.Map;

public class WordNet {

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        Map<String, Integer> stringToId = new HashMap<>();
        Map<Integer, String[]> idToString = new HashMap<>();

        In in = new In(synsets);
        while (in.hasNextLine()) {
            String synonim = in.readLine();
            String[] parts = synonim.split(",");
            int id = Integer.valueOf(parts[0]);
            String[] syns = parts[1].split("\b \b");
            idToString.put(id, syns);
            for (String s : syns) {
                stringToId.put(s, id);
            }
        }
        in.close();

        Digraph dg = new Digraph(idToString.size());
        in = new In(hypernyms);
        while (in.hasNextLine()) {
            String hypernym = in.readLine();
            String[] parts = hypernym.split(",");
            int from = Integer.valueOf(parts[0]);
            for (int i = 1; i < parts.length; i++) {
                int to = Integer.valueOf(parts[i]);
                dg.addEdge(from, to);
            }
        }

        String s = "123";
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        Digraph dg = new Digraph(10);
        dg.addEdge(5, 1);
        dg.addEdge(1, 5);
        DirectedCycle cycle = new DirectedCycle(dg);
        if (cycle.hasCycle()) {
            throw new IllegalArgumentException();
        }
        return null;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return false;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        return 0;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        return null;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet(args[0], args[1]);
    }
}
