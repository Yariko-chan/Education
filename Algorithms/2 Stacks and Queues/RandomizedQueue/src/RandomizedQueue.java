import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Diana on 29.01.2017.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] qu;
    private int size;

    public RandomizedQueue() {
        qu = (Item[]) new Object[2];
        size = 0;
    }


    private void resize(int length) {
       /* Item[] copy = (Item[]) new Object[length];
        for (int i = 0; i < size; i++) {
            copy[i] = qu[i];
        }
        qu = copy;*/
       qu = Arrays.copyOf(qu, length);
    }
    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        if (size == qu.length)  resize(qu.length*2);
        qu[size] = item;
        size++;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int n = StdRandom.uniform(0, size);
        Item item = qu[n];
        for (int i = n; i < size-1; i++) {
            qu[i] = (null == qu[i+1]) ? null : qu[i+1];
        }
        qu[--size] = null;
        if (size > 0 && size <= qu.length/4) resize(qu.length/2);
        return item;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int n = StdRandom.uniform(0, size);
        return qu[n];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int current = 0;
        private int[] order;

        public RandomizedQueueIterator() {
            order = new int[size];
            for (int i = 0; i < order.length; i++) {
                order[i] = i;
            }
            StdRandom.shuffle(order); // getting random order of iterating queue
        }

        @Override
        public boolean hasNext() {
            return current < order.length;
        }

        @Override
        public Item next() {
            if (current >= order.length) throw new NoSuchElementException();
            int random = order[current++];
            return qu[random];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) { }
}
