import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.DepthFirstOrder;
import edu.princeton.cs.algs4.Digraph;

public class SAP {

    private final Digraph dg;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException();
        }
        this.dg = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v < 0 || w < 0 || v >= dg.V() || w >= dg.V() ) {
            throw new IllegalArgumentException();
        }
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (v < 0 || w < 0 || v >= dg.V() || w >= dg.V() ) {
            throw new IllegalArgumentException();
        }

    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if ( v == null || w == null) {
            throw new IllegalArgumentException();
        }
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if ( v == null || w == null) {
            throw new IllegalArgumentException();
        }
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }

    private void sap(int v, int w) {
        BreadthFirstDirectedPaths v_bfp = new BreadthFirstDirectedPaths(dg, v);
        BreadthFirstDirectedPaths w_bfp = new BreadthFirstDirectedPaths(dg, w);

        Iterable<Integer> outs = dg.adj(v);
        int next = outs.iterator().next();
        int minPathSize = dg.V();
        int minPathRoot = next;
        do {
            if (w_bfp.hasPathTo(next)) {
                int pathSize = w_bfp.distTo(next);
                if (pathSize < minPathSize) {
                    minPathRoot = next;
                    minPathSize = pathSize;
                }
                // check previous
            } else {
                // check next in iterable
            }
            next = v_bfp.pathTo(next).iterator().
        }
    }
}
