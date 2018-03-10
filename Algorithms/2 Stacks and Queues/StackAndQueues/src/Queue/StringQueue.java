package queue;

/**
 * Created by Diana on 28.01.2017.
 */
public interface StringQueue {
    public void enqueue(String s);
    public String dequeue();
    public boolean isEmpty();
    public int size();
}
