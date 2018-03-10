import edu.princeton.cs.algs4.Queue;

import java.util.Iterator;

/**
 * Created by Diana on 27.02.2017.
 */
public class RedBlackBST<Key extends Comparable<Key>, Value> {
    private Node root;

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        Key key;
        Value value;
        Node left;
        Node right;
        int size;
        boolean color;

        public Node(Key key, Value value) {
            this.key = key;
            this.value = value;
        }

        public Node(Key key, Value value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
        }

        public Node(Key key, Value value, int i, boolean color) {
            this.key = key;
            this.value = value;
            this.size = size;
            this.color = color;
        }
    }

    // rotate right-lean node to be left-lean
    // maintains symmetric order
    private Node rotateLeft(Node n) {
        assert isRed(n.right);
        Node x = n.right;
        n.right = x.left;
        x.left = n;
        x.color = n.color;
        n.color = RED;
        return x;
    }

    // after some insert operations
    // to maintains symmetric order
    private Node rotateRight(Node n) {
        assert isRed(n.left);

        Node x = n.left;
        n.left = x.right;
        x.right = n;
        x.color = n.color;
        n.color = RED;

        return x;
    }

    // flip four node     b
    //        (abc)      / \              |
    //        / | \  to a   c             b
    //                 / \ / \           / \
    // four node in BST is actually ->  a   c
    // where b has two red links       / \ / \
    // so we jast need to chanhe colors
    private void flipColors(Node b) {
        assert !isRed(b);
        assert isRed(b.left);
        assert isRed(b.right);

        b.left.color = BLACK;
        b.right.color = BLACK;
        b.color = RED;
    }

    public boolean isRed(Node n) {
        if (null == n) return false;
        return n.color == RED;
    }

    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    private Node put(Node n, Key key, Value value) {

        if (null == n) return new Node(key, value, 1, RED);
        int c = key.compareTo(n.key);
        if (c > 0)      n.right = put(n.right, key, value);
        else if (c < 0) n.left  = put(n.left,  key, value);
        else /* (c == 0) */ n.value = value;

        if (!isRed(n.left) && isRed(n.right))n = rotateLeft(n);
        if ( isRed(n.left) && isRed(n.left.left)) n = rotateRight(n);
        if ( isRed(n.left) && isRed(n.right)) flipColors(n);

        n.size = 1 + size(n.left) + size(n.right);
        return n;
    }

    public Value get(Key key) {

        if (null == key) return null;
        Node n = root;
        while (null != n) {
            int c = key.compareTo(n.key);
            if (c > 0)      n = n.right;
            else if (c < 0) n = n.left;
            else /* (c == 0) */ return n.value;
        }

        return null;
    }

    public void delete(Key key) {
        root = delete(root, key);
    }

    private Node delete(Node n, Key key) {
        if (null == n) return null;
        int c = key.compareTo(n.key);
        if (c < 0) n.left = delete(n.left, key);
        else if (c > 0) n.right = delete(n.right, key);
        else {
            if (null == n.left) return n.right;
            if (null == n.right) return n.left;

            Node t = n;
            n.key = min(t.right);
            n.left = t.left;
            n.right = deleteMin(t.right);
        }
        n.size = 1 + size(n.left) + size(n.right);
        return n;
    }

    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if (null == node) return 0;
        return node.size;
    }

    public Iterator iterator() {
        return null;
    }

    public boolean contains(Key key) {
        return null != get(key);
    }

    public boolean isEmpty() {
        return root == null;
    }

    public Key min() {
        if (null == root) return null;
//        Node n = root;
//        while (null != n.left) {
//            n = n.left;
//        }
//        return n.key;
        return min(root);
    }

    private Key min(Node n) {
        if (null == n.left) return n.key;
        return (min(n.left));
    }

    public Key max() {
        if (null == root) return null;
        Node n = root;
        while (null != n.right) {
            n = n.right;
        }
        return n.key;
    }

    // largest key less than or equal to key
    public Key floor(Key key) {
        Node node = floor(root, key);
        if (null != node) return node.key;
        return null;
    }

    private Node floor(Node node, Key key) {
        if (null == node) return null;

        Node x = node;
        int c = key.compareTo(x.key);
        if (0 == c) return x;
        if (c < 0)  return floor(x.left, key);

        Node t = floor(x.right, key);
        if (null != t) return t;
        return x;
    }

    // smallest key greater than or equal to key
    public Key ceiling(Key key) {
        Node node = ceiling(root, key);
        if (null != node) return node.key;
        return null;
    }

    private Node ceiling(Node node, Key key) {
        if (null == node) return null;

        Node x = node;
        int c = key.compareTo(x.key);
        if (0 == c) return x;
        if (c > 0) return ceiling(x.right, key);

        Node t = ceiling(x.left, key);
        if (null != t) return t;
        return x;
    }

    // number of keys less than key
    public int rank(Key key) {
        if (null == key) return 0;
        return rank(root, key);
    }

    private int rank(Node x, Key key) {
        if (null == x) return 0;

        int c = key.compareTo(x.key);
        if (c > 0) return 1 + size(x.left) + rank(x.right, key);
        else if (c < 0) return rank(x.left, key);
        else return size(x.left);
    }

    // key of rank k
    public Key select(int k) {
        return null;
    }

    // delete smallest key
    public void deleteMin() {
        if (null == root) return;
        root = deleteMin(root);
    }

    private Node deleteMin(Node n) {
        if (null == n.left) return n.right;
        n.left = deleteMin(n.left);
        n.size = 1 + size(n.left) + size(n.right);
        return n;
    }

    // delete largest key
    public void deleteMax() {
        if (null == root) return;
        root = deleteMax(root);
    }

    private Node deleteMax(Node n) {
        if (null == n.right) return n.left;
        n.right = deleteMax(n.right);
        n.size = 1 + size(n.left) + size(n.right);
        return n;
    }

    // number of keys in [lo..hi]
    public int size(Key lo, Key hi) {
        return -1;
    }

    // keys in [lo..hi], in sorted order
    public Iterable<Key> keys(Key lo, Key hi) {
        return null;
    }

    // all keys in the table, in sorted order
    public Iterable<Key> keys() {
        Queue<Key> q = new Queue<Key>();
        inorder(root, q);
        return q;
    }

    private void inorder(Node node, Queue<Key> q) {
        if (null == node) return;
        inorder(node.left, q);
        q.enqueue(node.key);
        inorder(node.right, q);
    }
}
