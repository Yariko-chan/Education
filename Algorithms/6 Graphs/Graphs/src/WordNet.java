
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Topological;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WordNet {
    private Digraph dg;
    private Map<String, Integer> stringToId;
    private Map<Integer, String[]> idToString;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }
        stringToId = new HashMap<>();
        idToString = new HashMap<>();

        In in = new In(synsets);
        while (in.hasNextLine()) {
            String synonim = in.readLine();
            String[] parts = synonim.split(",");
            int id = Integer.valueOf(parts[0]);
            String[] syns = parts[1].split(" ");
            idToString.put(id, syns);
            for (String s : syns) {
                stringToId.put(s, id);
            }
        }
        in.close();

        dg = new Digraph(idToString.size());
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

        // check graph is rooted DAG
        Digraph revers = dg.reverse();
        Topological t = new Topological(revers);
        if (!t.hasOrder()) {
            throw new IllegalArgumentException("Graph doesnt have order");
        }
        Iterator<Integer> iterator = t.order().iterator();
        int root = iterator.next();
        BreadthFirstDirectedPaths reverseBFO = new BreadthFirstDirectedPaths(revers, root);
        boolean result = true;
        while (iterator.hasNext()) {
            if (!reverseBFO.hasPathTo(iterator.next())) {
                result = false;
                break;
            }
        }
        if (!result) {
            throw new IllegalArgumentException("Graph isnt rooted DAG");
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return stringToId.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        ArrayList<String> strings = new ArrayList<>(stringToId.keySet());
        return strings.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        int aId = stringToId.get(nounA);
        int bId = stringToId.get(nounB);
        SAP sap = new SAP(dg);
        return sap.length(aId, bId);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        int aId = stringToId.get(nounA);
        int bId = stringToId.get(nounB);
        SAP sap = new SAP(dg);
        int sapId = sap.ancestor(aId, bId);
        String[] syns = idToString.get(sapId);
        StringBuilder synset = new StringBuilder();
        for (String s : syns) {
            synset.append(s).append(" ");
        }
        synset.deleteCharAt(synset.length() - 1);
        return synset.toString();
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet(args[0], args[1]);
        wordNet.isNoun("entity");

    }
}
