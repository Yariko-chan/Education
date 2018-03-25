
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Topological;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class WordNet {
    private SAP sap;
    private Map<String, Integer[]> stringToId;
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
            int id = Integer.parseInt(parts[0]);
            String[] syns = parts[1].split(" ");
            idToString.put(id, syns);
            for (String s : syns) {
                if (stringToId.containsKey(s)) {
                    Integer[] ids = stringToId.get(s);
                    Integer[] newIds = new Integer[ids.length + 1];
                    System.arraycopy(ids, 0, newIds, 0, ids.length);
                    newIds[newIds.length - 1] = id;
                    stringToId.put(s, newIds);
                } else {
                    stringToId.put(s, new Integer[]{id});
                }
            }
        }
        in.close();

        Digraph dg = new Digraph(idToString.size());
        in = new In(hypernyms);
        while (in.hasNextLine()) {
            String hypernym = in.readLine();
            String[] parts = hypernym.split(",");
            int from = Integer.parseInt(parts[0]);
            for (int i = 1; i < parts.length; i++) {
                int to = Integer.parseInt(parts[i]);
                dg.addEdge(from, to);
            }
        }
        sap = new SAP(dg);

        // check graph is rooted DAG
        Topological t = new Topological(dg);
        if (!t.hasOrder()) {
            throw new IllegalArgumentException("Graph doesnt have order");
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
        return stringToId.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        List<Integer> aIDs = Arrays.asList(stringToId.get(nounA));
        List<Integer> bIDs = Arrays.asList(stringToId.get(nounB));
        return sap.length(aIDs, bIDs);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        List<Integer> aIDs = Arrays.asList(stringToId.get(nounA));
        List<Integer> bIDs = Arrays.asList(stringToId.get(nounB));
        int sapId = sap.ancestor(aIDs, bIDs);
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
        WordNet wordNet = new WordNet("synsets.txt", "hypernyms.txt");
        wordNet.distance("security_blanket", "thing");
    }
}
