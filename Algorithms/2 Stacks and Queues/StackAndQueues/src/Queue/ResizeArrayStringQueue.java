package queue;

/**
 * Created by Diana on 28.01.2017.
 */
public class ResizeArrayStringQueue implements StringQueue {
    private String[] qu;
    private int first, last;

    public ResizeArrayStringQueue() {
        qu = new String[2];
        first = 0;
        last = 0;
    }

    @Override
    public void enqueue(String s) {
        if (last == qu.length) {
            if (first > 0) {
                moveQueue();
            } else {
                resize(qu.length*2);
            }
        }
        qu[last++] = s;
    }

    @Override
    public String dequeue() {
        if (isEmpty()) return null;
        String item = qu[first];
        qu[first++] = null;
        if (size() <= qu.length/4) {
            moveQueue();
            resize(qu.length/2);
        }
        return item;
    }

    @Override
    public boolean isEmpty() {
        return first == last;
    }

    @Override
    public int size() {
        return last-first;
    }

    private void resize(int length) {
        String[] copy  = new String[length];
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
}
