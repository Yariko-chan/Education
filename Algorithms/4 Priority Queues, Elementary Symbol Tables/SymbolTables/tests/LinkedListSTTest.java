import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Diana on 24.02.2017.
 */
public class LinkedListSTTest {
    ST<String, Integer> st;

    @Before
    public void setUp() throws Exception {
        st = new LinkedListST<>();
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

}