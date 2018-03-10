/**
 * Created by Diana on 26.01.2017.
 */
public class Network {
    private int[] net;

    public Network(int N) {
        net = new int[N];
    }

    public void union(int p, int q){
        if (!areFriends(p, q)){
            if (p > q) net[p] = q;
            else net[q] = p;
        }
    }

    private boolean areFriends(int p, int q) {
        return net[p] == net[q];
    }

    public void areAllConnected(){

    }
}
