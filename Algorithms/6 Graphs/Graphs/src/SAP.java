

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SAP {

    private final Digraph dg;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException();
        }
        this.dg = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v < 0 || w < 0 || v >= dg.V() || w >= dg.V()) {
            throw new IllegalArgumentException();
        }
        BreadthFirstDirectedPaths vBfp = new BreadthFirstDirectedPaths(dg, v);
        BreadthFirstDirectedPaths wBfp = new BreadthFirstDirectedPaths(dg, w);
        int ancestor = sap(v, vBfp, wBfp);
        if (ancestor == -1) {
            return -1;
        } else {
            return vBfp.distTo(ancestor) + wBfp.distTo(ancestor);
        }
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (v < 0 || w < 0 || v >= dg.V() || w >= dg.V()) {
            throw new IllegalArgumentException();
        }
        BreadthFirstDirectedPaths vBfp = new BreadthFirstDirectedPaths(dg, v);
        BreadthFirstDirectedPaths wBfp = new BreadthFirstDirectedPaths(dg, w);
        return sap(v, vBfp, wBfp);
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if ( v == null || w == null){
            throw new IllegalArgumentException();
        }
        BreadthFirstDirectedPaths vBfp = new BreadthFirstDirectedPaths(dg, v);
        BreadthFirstDirectedPaths wBfp = new BreadthFirstDirectedPaths(dg, w);
        int ancestor = sap(v, vBfp, wBfp);
        if (ancestor == -1) {
            return -1;
        } else {
            return vBfp.distTo(ancestor) + wBfp.distTo(ancestor);
        }
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if ( v == null || w == null){
            throw new IllegalArgumentException();
        }
        BreadthFirstDirectedPaths vBfp = new BreadthFirstDirectedPaths(dg, v);
        BreadthFirstDirectedPaths wBfp = new BreadthFirstDirectedPaths(dg, w);
        return sap(v, vBfp, wBfp);
    }

    /**
     * Loop through the queue of adj(v) and their's adj
     * check v.bfo() + w.bfo()
     * find min sum
     * @param v
     */
    private int sap(int v, BreadthFirstDirectedPaths vBfp, BreadthFirstDirectedPaths wBfp) {
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(v);
        boolean[] marked = new boolean[dg.V()];
        marked[v] = true;

        int minLength = dg.E() + 1; // max is E()
        int minAncestor = -1;
        while (!queue.isEmpty()){
            int node = queue.dequeue();

            Iterator<Integer> nodeAdj = dg.adj(node).iterator();
            while (nodeAdj.hasNext()) {
                int next = nodeAdj.next();
                if (!marked[next]) {
                    queue.enqueue(next);
                    marked[next] = true;
                }
            }


            if (vBfp.hasPathTo(node) && wBfp.hasPathTo(node)){
                int dist = vBfp.distTo(node) + wBfp.distTo(node);
                if (dist < minLength) {
                    minLength = dist;
                    minAncestor = node;
                }
            }
        }

        return minAncestor;
    }

    private int sap(Iterable<Integer> v, BreadthFirstDirectedPaths vBfp, BreadthFirstDirectedPaths wBfp) {
        Queue<Integer> queue = new Queue<>();
        boolean[] marked = new boolean[dg.V()];
        for (Integer node : v) {
            queue.enqueue(node);
            marked[node] = true;
        }

        int minLength = dg.E() + 1; // max is E()
        int minAncestor = -1;
        while (!queue.isEmpty()){
            int node = queue.dequeue();

            Iterator<Integer> nodeAdj = dg.adj(node).iterator();
            while (nodeAdj.hasNext()) {
                int next = nodeAdj.next();
                if (!marked[next]) {
                    queue.enqueue(next);
                    marked[next] = true;
                }
            }


            if (vBfp.hasPathTo(node) && wBfp.hasPathTo(node)){
                int dist = vBfp.distTo(node) + wBfp.distTo(node);
                if (dist < minLength) {
                    minLength = dist;
                    minAncestor = node;
                }
            }
        }

        return minAncestor;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        Digraph dg = new Digraph(7);
        dg.addEdge(1, 0);
        dg.addEdge(5, 0);
        dg.addEdge(4, 5);
        dg.addEdge(3, 4);
        dg.addEdge(2, 3);
        dg.addEdge(1, 2);
        dg.addEdge(6, 1);

        List<Integer> v = new ArrayList<>(2);
        v.add(1);
        v.add(6);
        List<Integer> w = new ArrayList<>(2);
        w.add(4);
        w.add(5);

        SAP sap = new SAP(dg);
        int i = sap.ancestor(v, w);
    }
}
