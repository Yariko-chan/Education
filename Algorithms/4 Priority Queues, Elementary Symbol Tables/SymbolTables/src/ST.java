import java.util.Iterator;

/**
 * Created by Diana on 24.02.2017.
 */
public interface ST<Key, Value> {
    public void put(Key key, Value value);
    public Value get(Key key);
    public void delete(Key key);
    public int size();
    public Iterator iterator();
    public boolean contains(Key key);
    public boolean isEmpty();
}
