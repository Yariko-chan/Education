package stack.generics;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Diana on 28.01.2017.
 */
public class ResizeArrayStack<Item> implements Iterable<Item>{
    private Item[] stack;
    private int first;

    public ResizeArrayStack() {
        stack = (Item[]) new Object[2];
        first = 0;
    }

    public void push(Item s) {
        if (first == stack.length) resize(2*stack.length);
        stack[first++] = s;
    }

    public Item pop() {
        if (first == 0) return null;
        Item item = stack[--first];
        stack[first] = null;
        if (first <= 0.25*stack.length) resize(stack.length/2);
        return item;
    }

    private void resize(int length) {
        Item[] copy  = (Item[]) new Object[length];
        for (int i = 0; i < first; i++) copy[i] = stack[i];
        stack = copy;
        copy = null;
    }

    public boolean isEmpty() {
        return first == 0;
    }

    public int count() {
        return first;
    }

    public int getSize(){
        return stack.length;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ResizeArrayStackIterator();
    }

    private class ResizeArrayStackIterator implements Iterator<Item> {
        int current = first - 1;

        @Override
        public boolean hasNext() {
            return current >= 0;
        }

        @Override
        public Item next() {
            if (current < 0) throw new NoSuchElementException();
            Item item = stack[current--];
            return item;
        }
    }

}
