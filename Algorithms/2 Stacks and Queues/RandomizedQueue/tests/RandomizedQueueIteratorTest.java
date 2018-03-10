import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Created by Diana on 29.01.2017.
 */
public class RandomizedQueueIteratorTest {
    RandomizedQueue<Integer> qu;
    private Iterator<Integer> it;

    @Before
    public void setUp() throws Exception {
        qu = new RandomizedQueue<>();
        qu.enqueue(1);
        qu.enqueue(1);
        it = qu.iterator();
    }

    @Test
    public void hasNext() throws Exception {
        assertTrue(it.hasNext());
        it.next();
        assertTrue(it.hasNext());
        it.next();
        assertFalse(it.hasNext());
    }

    @Test (expected = NoSuchElementException.class)
    public void next() throws Exception {
        assertEquals(1, (int)it.next());
        assertEquals(1, (int)it.next());
        it.next();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void remove() throws Exception {
        it.remove();
    }

}