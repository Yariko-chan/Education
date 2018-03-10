package stack.generics;

/**
 * Created by Diana on 28.01.2017.
 */
public interface Stack<Item> {
    public void push(Item item);
    public Item pop();
    public boolean isEmpty();
    public int count();
}
