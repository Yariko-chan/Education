package stack.generics;

import stack.StringStack;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Diana on 28.01.2017.
 */

/* Can't implement push method so not implements*/
public class LinkedListStack<Item> implements Iterable<Item>{
    Node first;
    int count;

    private class Node {
        Item item;
        Node next;
    }

    public LinkedListStack() {
        first = null;
        count = 0;
    }

    public void push(Item item) {
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        count++;
    }

    public Item pop() {
        Item item = first.item;
        first = first.next;
        count--;
        return item;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int count() {
        return count;
    }

    @Override
    public Iterator<Item> iterator() {
        return new LinkedListStackIterator();
    }

    private class LinkedListStackIterator implements Iterator<Item> {
        Node current = first; //next node after we received last

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

        @Override
        public void remove() {
            throw  new UnsupportedOperationException();
        }
    }
}
