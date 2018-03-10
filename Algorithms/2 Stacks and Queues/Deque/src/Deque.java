import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Diana on 29.01.2017.
 */
public class Deque<Item> implements Iterable<Item> {
    private int size = 0;
    private Node first; // first sentinel node
    private Node last; // last sentinel node


    private class Node {
        private Node prev;
        private Item item;
        private Node next;
    }

    public Deque() {
        first = new Node();
        last = new Node();
        first.next = last;
        last.prev = first;
    }

    public boolean isEmpty() {
        return first.next == last && last.prev == first;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException();
        Node oldfirst = first;
        first = new Node();
        oldfirst.item = item;
        oldfirst.prev = first;
        first.next = oldfirst;

        size++;
    }

    public void addLast(Item item)  {
        if (item == null) throw new NullPointerException();
        Node oldLast = last;
        last = new Node();
        oldLast.item = item;
        oldLast.next = last;
        last.prev = oldLast;

        size++;
    }
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Node oldFirst = first;
        first = first.next;
        //oldFirst = null;   // remove old sentinel
        Item item = first.item;  // return item from first
        first.item = null; // make node first sentinel
        first.prev = null;

        size--;
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Node oldLast = last;
        last = last.prev;
        //oldLast = null;
        Item item = last.item;
        last.item = null;
        last.next = null;

        size--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first.next;

        @Override
        public boolean hasNext() {
            return current.item != null;
        }

        @Override
        public Item next() {
            if (current.item == null) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
    }
}
