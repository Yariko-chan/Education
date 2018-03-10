/**
 * Created by Diana on 26.01.2017.
 */
public class PathCompressionQU implements UF{
    private final int[] id;

    public PathCompressionQU(int N) {
        id = new int[N];
        for (int i = 0; i < N; i++){
            id[i] = i;
        }
    }

    @Override
    public void union(int p, int q) {
        int pRoot = root(p);
        int qRoot = root(q);
        id[pRoot] = qRoot;
    }

    @Override
    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    private int root(int i){
        while (i != id[i]) {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }
}
