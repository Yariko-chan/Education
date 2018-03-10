import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Diana on 24.02.2017.
 */
public class LinkedListST<Key, Value> implements ST<Key, Value> {
    LinkedList<STNode> ll;

    private class STNode<Key, Value> {
        private Key key;
        private Value value;

        public STNode(Key key, Value value) {
            this.key = key;
            this.value = value;
        }

        public Key getKey() {
            return key;
        }

        public Value getValue() {
            return value;
        }

        public void setValue(Value value) {
            this.value = value;
        }
    }

    public LinkedListST() {
        ll = new LinkedList<STNode>();
    }

    @Override
    public void put(Key key, Value value) {
        STNode distinct = search(key);
        if (null != distinct) distinct.setValue(value);
        else {
            STNode newNode = new STNode(key, value);
            ll.add(newNode);
        }
    }

    @Override
    public Value get(Key key) {
        STNode node = search(key);
        if (null == node) return null;
        return (Value) node.getValue();
    }

    @Override
    public void delete(Key key) {
        STNode node = search(key);
        if (null != node) ll.remove(node);
    }

    @Override
    public int size() {
        return ll.size();
    }

    @Override
    public Iterator iterator() {
        return ll.iterator();
    }

    @Override
    public boolean contains(Key key) {
        return (null != search(key));
    }

    @Override
    public boolean isEmpty() {
        return ll.isEmpty();
    }

    private STNode search(Key key) {
        for (STNode node:ll) {
            if (key.equals(node.getKey())) {
                return node;
            }
        }
        return null;
    }
}
