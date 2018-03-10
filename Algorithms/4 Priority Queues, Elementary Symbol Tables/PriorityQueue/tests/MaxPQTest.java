import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Created by Diana on 16.02.2017.
 */
public class MaxPQTest {
    MaxPQ<Integer> pq;

    @Before
    public void setUp() throws Exception {
        pq = new MaxPQ<Integer>(10);
    }

    @Test
    public void insert() throws Exception {

    }

    @Test(expected = NoSuchElementException.class)
    public void deleteMax() throws Exception {
        pq.insert(0);
        pq.insert(0);
        pq.insert(0);
        assertEquals((Integer) 0, pq.deleteMax());
        pq.insert(5);
        pq.insert(3);
        assertEquals((Integer) 5, pq.deleteMax());
        assertEquals((Integer) 3, pq.deleteMax());
        assertEquals((Integer) 0, pq.deleteMax());
        assertEquals((Integer) 0, pq.deleteMax());
        pq.deleteMax();
    }

    @Test(expected = NoSuchElementException.class)
    public void max() throws Exception {
        pq.insert(0);
        pq.insert(0);
        pq.insert(0);
        assertEquals((Integer) 0, pq.max());
        pq.insert(5);
        pq.insert(3);
        assertEquals((Integer) 5, pq.max());
        pq.deleteMax();
        pq.deleteMax();
        pq.deleteMax();
        pq.deleteMax();
        pq.deleteMax();
        pq.max();
    }

    @Test
    public void size() throws Exception {
        assertEquals(0, pq.size());

        pq.insert(0);
        assertEquals(1, pq.size());
        pq.insert(1);
        assertEquals(2, pq.size());

        pq.deleteMax();
        assertEquals(1, pq.size());
        pq.deleteMax();
        assertEquals(0, pq.size());
    }

    @Test
    public void isEmpty() throws Exception {
        assertTrue(pq.isEmpty());

        pq.insert(0);
        assertFalse(pq.isEmpty());
        pq.deleteMax();
        assertTrue(pq.isEmpty());
    }

}