package queue.generics;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Diana on 28.01.2017.
 */
public class LinkedListQueue<Item> implements Iterable<Item>{
    Node first = null;
    Node last = null;
    int count = 0;

    private class Node {
        Item item;
        Node next;
    }

    public void enqueue(Item item) {
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) first = last;
        else oldLast.next = last;
        count++;
    }

    public Item dequeue() {
        if (isEmpty()) return null;
        Item item = first.item;
        first = first.next;
        count--;
        return item;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return count;
    }

    @Override
    public Iterator<Item> iterator() {
        return new LinkedListQueueIterator();
    }

    private class LinkedListQueueIterator implements Iterator<Item>{
        Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (current == null) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}
