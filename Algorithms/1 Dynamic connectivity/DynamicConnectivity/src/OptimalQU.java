/**
 * Created by Diana on 26.01.2017.
 */
public class OptimalQU implements UF {
    private final int[] id;
    private final int[] sz;

    public OptimalQU(int N) {
        id = new int[N];
        sz = new int[N];
        for (int i = 0; i < N; i++){
            id[i] = i;
            sz[i] = 1;
        }
    }

    @Override
    public void union(int p, int q) {
        int pRoot = root(p);
        int qRoot = root(q);
        if (pRoot == qRoot) return;
        if (sz[pRoot] < sz[qRoot]) {
            id[pRoot] = qRoot;
            sz[qRoot] += sz[pRoot];
        }
        else {
            id[qRoot] = pRoot;
            sz[pRoot] += sz[qRoot];
        }
    }

    @Override
    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    private int root(int i) {
        while (i != id[i]) {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }
}
