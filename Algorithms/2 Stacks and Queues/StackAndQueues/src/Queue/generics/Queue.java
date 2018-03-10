package queue.generics;

/**
 * Created by Diana on 28.01.2017.
 */
public interface Queue<Item> {
    public void enqueue(Item s);
    public Item dequeue();
    public boolean isEmpty();
    public int size();
}
