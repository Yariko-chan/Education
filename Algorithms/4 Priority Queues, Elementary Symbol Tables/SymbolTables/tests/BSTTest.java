import edu.princeton.cs.algs4.Queue;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Diana on 24.02.2017.
 */
public class BSTTest {
    BST<String, Integer> st;

    @Before
    public void setUp() throws Exception {
        st = new BST<>();
    }

    @Test
    public void put() throws Exception {
        st.put("A", 1);
        assertEquals((Integer) 1, (Integer) st.get("A"));
        st.put("A", 2);
        assertEquals((Integer) 2, (Integer) st.get("A"));
        st.put("A", null);
        assertNull(st.get("A"));
    }

    @Test
    public void get() throws Exception {
        assertNull(st.get("A"));
        st.put("A", 1);
        assertEquals((Integer) 1, (Integer) st.get("A"));
        assertEquals((Integer) 1, (Integer) st.get("A"));
    }

    @Test
    public void delete() throws Exception {
        st.delete("A");
        st.put("A", 1);
        assertEquals((Integer) 1, (Integer) st.get("A"));
        st.delete("A");
        assertNull(st.get("A"));
    }

    @Test
    public void size() throws Exception {
        assertEquals(0, st.size());
        st.put("A", 1);
        assertEquals(1, st.size());
        st.put("B", 2);
        assertEquals(2, st.size());
        st.put("A", 3);
        assertEquals(2, st.size());
        int i = st.get("A");
        assertEquals(2, st.size());
        st.delete("A");
        assertEquals(1, st.size());
    }

    @Test
    public void iterator() throws Exception {

    }

    @Test
    public void contains() throws Exception {
        assertFalse(st.contains("A"));
        st.put("A", 1);
        assertTrue(st.contains("A"));
        st.get("A");
        assertTrue(st.contains("A"));
        st.delete("A");
        assertFalse(st.contains("A"));
    }

    @Test
    public void isEmpty() throws Exception {
        assertTrue(st.isEmpty());
        st.put("A", 1);
        assertFalse(st.isEmpty());
        st.get("A");
        assertFalse(st.isEmpty());
        st.delete("A");
        assertTrue(st.isEmpty());
    }

    @Test
    public void min() throws Exception {
        assertNull(st.min());
        st.put("B", 2);
        assertEquals("B", st.min());
        st.put("C", 3);
        assertEquals("B", st.min());
        st.put("A", 1);
        assertEquals("A", st.min());
    }

    @Test
    public void max() throws Exception {
        assertNull(st.max());
        st.put("B", 2);
        assertEquals("B", st.max());
        st.put("C", 3);
        assertEquals("C", st.max());
        st.put("A", 1);
        assertEquals("C", st.max());
    }

    @Test
    public void floor() throws Exception {
        assertNull(st.floor("A"));
        st.put("B", 2);
        assertEquals(null, st.floor("A"));
        assertEquals("B", st.floor("C"));
        st.put("A", 1);
        st.put("C", 3);
        st.put("G", 6);
        st.put("P", 11);
        assertEquals("P", st.floor("Q"));
        assertEquals("G", st.floor("N"));
        assertEquals("A", st.floor("A"));
        assertEquals("C", st.floor("D"));
    }

    @Test
    public void ceiling() throws Exception {
        assertNull(st.ceiling("A"));
        st.put("A", 1);
        assertEquals("A", st.ceiling("A"));
        assertEquals(null, st.ceiling("C"));
        st.put("B", 2);
        st.put("C", 3);
        st.put("G", 6);
        st.put("P", 11);
        assertEquals("P", st.ceiling("O"));
        assertEquals("P", st.ceiling("N"));
        assertEquals("A", st.ceiling("A"));
        assertEquals("G", st.ceiling("D"));
    }

    @Test
    public void rank() throws Exception {
        assertEquals(0, st.rank("A"));
        st.put("N", 2);
        st.put("E", 3);
        st.put("P", 6);
        st.put("C", 11);
        st.put("I", 2);
        st.put("O", 3);
        st.put("R", 6);
        st.put("A", 11);
        st.put("D", 2);
        st.put("G", 3);
        st.put("L", 6);
        st.put("Q", 11);
        st.put("S", 2);

        assertEquals(3, st.rank("E"));
        assertEquals(4, st.rank("F"));
        assertEquals(0, st.rank("A"));
        assertEquals(7, st.rank("N"));
        assertEquals(7, st.rank("M"));
        assertEquals(8, st.rank("O"));
        assertEquals(13, st.rank("T"));
        assertEquals(10, st.rank("Q"));
        assertEquals(12, st.rank("S"));
    }

    @Test
    public void select() throws Exception {

    }

    @Test
    public void deleteMin() throws Exception {
        st.put("N", 2);
        st.put("E", 3);
        st.put("P", 6);
        st.put("C", 11);
        st.put("I", 2);
        st.put("O", 3);
        st.put("R", 6);
        st.put("A", 11);
        st.put("D", 2);
        st.put("G", 3);
        st.put("L", 6);
        st.put("Q", 11);
        st.put("S", 2);
        assertEquals("A", st.min());
        assertEquals(13, st.size());
        st.deleteMin();
        assertEquals(12, st.size());
        assertEquals("C", st.min());
        st.deleteMin();
        assertEquals(11, st.size());
        assertEquals("D", st.min());
        st.deleteMin();
        assertEquals(10, st.size());
        assertEquals("E", st.min());
        st.deleteMin();
        assertEquals(9, st.size());
        assertEquals("G", st.min());
        st.deleteMin();
        assertEquals(8, st.size());
        assertEquals("I", st.min());
    }

    @Test
    public void deleteMax() throws Exception {
        st.put("N", 2);
        st.put("E", 3);
        st.put("P", 6);
        st.put("C", 11);
        st.put("I", 2);
        st.put("O", 3);
        st.put("R", 6);
        st.put("A", 11);
        st.put("D", 2);
        st.put("G", 3);
        st.put("L", 6);
        st.put("Q", 11);
        st.put("S", 2);
        assertEquals(13, st.size());
        assertEquals("S", st.max());
        st.deleteMax();
        assertEquals(12, st.size());
        assertEquals("R", st.max());
        st.deleteMax();
        assertEquals(11, st.size());
        assertEquals("Q", st.max());
        st.deleteMax();
        assertEquals(10, st.size());
        assertEquals("P", st.max());
        st.deleteMax();
        assertEquals(9, st.size());
        assertEquals("O", st.max());
        st.deleteMax();
        assertEquals(8, st.size());
        assertEquals("N", st.max());
    }

    @Test
    public void keys() throws Exception {
        Queue<String> keys = (Queue<String>) st.keys();
        assertEquals(0, keys.size());
        st.put("N", 2);
        st.put("E", 3);
        st.put("P", 6);
        st.put("C", 11);
        st.put("I", 2);
        st.put("O", 3);
        st.put("R", 6);
        st.put("A", 11);
        st.put("D", 2);
        st.put("G", 3);
        st.put("L", 6);
        st.put("Q", 11);
        st.put("S", 2);
        keys = (Queue<String>) st.keys();
        String[] keysTest = {"A", "C", "D", "E", "G", "I", "L", "N", "O", "P", "Q", "R", "S"};
        assertEquals(13, keys.size());
        int i = 0;
        for (String s:keys) {
            System.out.println(s);
            assertEquals(s, keysTest[i++]);
        }
    }

}