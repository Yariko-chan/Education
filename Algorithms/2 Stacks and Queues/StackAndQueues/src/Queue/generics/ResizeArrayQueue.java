package queue.generics;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Diana on 28.01.2017.
 */
public class ResizeArrayQueue<Item> implements Iterable<Item>{
    private Item[] qu;
    private int first, last;

    public ResizeArrayQueue() {
        qu = (Item[]) new Object[2];
        first = 0;
        last = 0;
    }

    public void enqueue(Item item) {
        if (last == qu.length) {
            if (first > 0) {
                moveQueue();
            } else {
                resize(qu.length*2);
            }
        }
        qu[last++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) return null;
        Item item = qu[first];
        qu[first++] = null;
        if (size() <= qu.length/4) {
            moveQueue();
            resize(qu.length/2);
        }
        return item;
    }

    public boolean isEmpty() {
        return first == last;
    }

    public int size() {
        return last-first;
    }

    private void resize(int length) {
        Item[] copy  = (Item[]) new Object[length];
        for (int i = 0; i < last; i++) copy[i] = qu[i];
        qu = copy;
        copy = null;
    }

    private void moveQueue() {
        int j = 0;
        for (int i = first; i < last; i++, j++){
            qu[j] = qu[i];
            qu[i] = null;
        }
        last = last - first;
        first = 0;
    }

    public int getSize() {
        return qu.length;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ResizeArrayQueueIterator();
    }

    private class ResizeArrayQueueIterator implements Iterator<Item>{
        int current = first;

        @Override
        public boolean hasNext() {
            return current < last;
        }

        @Override
        public Item next() {
            if (current >= last) throw new NoSuchElementException();
            return qu[current++];
        }
    }
}
