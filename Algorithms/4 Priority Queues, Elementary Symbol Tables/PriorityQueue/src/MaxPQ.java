import java.util.NoSuchElementException;

/**
 * Created by Diana on 16.02.2017.
 */
public class MaxPQ<Key extends Comparable<Key>> {
    private Key[] pq;
    private int size = 0;

    public MaxPQ (int capacity) {
        pq = (Key[]) new Comparable[capacity + 1];
    }

    public void insert(Key k) {
        pq[++size] = k;
        swim(size);
    }
    public Key deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue is empty.");
        Key res = pq[1];
        Helper.exch(pq, 1, size);
        pq[size--] = null;
        sink(1);
        return res;
    }

    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue is empty.");
        return pq[1];
    }

    private void swim(int i) {
        while (i > 1 && Helper.less(pq[i/2], pq[i])) {
            Helper.exch(pq, i/2, i);
            i /= 2;
        }
    }

    private void sink(int i) {
        while (i*2 <= size) {
            int j = 2*i;
            if (j < size && Helper.less(pq[j], pq[j + 1]))
                j++;
            if (Helper.less(pq[j], pq[i]))
                break;
            Helper.exch(pq, i, j);
            i = j;
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
