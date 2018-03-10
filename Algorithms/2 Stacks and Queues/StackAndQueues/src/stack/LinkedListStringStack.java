package stack;

/**
 * Created by Diana on 28.01.2017.
 */
public class LinkedListStringStack implements StringStack {
    Node first;
    int count;

    private class Node {
        String item;
        Node next;
    }

    public LinkedListStringStack() {
        first = null;
        count = 0;
    }

    @Override
    public void push(String s) {
        Node oldfirst = first;
        first = new Node();
        first.item = s;
        first.next = oldfirst;
        count++;
    }

    @Override
    public String pop() {
        String item = first.item;
        first = first.next;
        count--;
        return item;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public int count() {
        return count;
    }
}
