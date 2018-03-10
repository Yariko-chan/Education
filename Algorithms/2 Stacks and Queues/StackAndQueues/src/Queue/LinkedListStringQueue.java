package queue;

/**
 * Created by Diana on 28.01.2017.
 */
public class LinkedListStringQueue implements StringQueue{
    Node first = null;
    Node last = null;
    int count = 0;

    private class Node {
        String item;
        Node next;
    }

    @Override
    public void enqueue(String s) {
        Node oldLast = last;
        last = new Node();
        last.item = s;
        last.next = null;
        if (isEmpty()) first = last;
        else oldLast.next = last;
        count++;
    }

    @Override
    public String dequeue() {
        if (isEmpty()) return null;
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
    public int size() {
        return count;
    }
}
