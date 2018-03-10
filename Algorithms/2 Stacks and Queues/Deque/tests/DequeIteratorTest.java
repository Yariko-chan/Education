import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Created by Diana on 29.01.2017.
 */
public class DequeIteratorTest {
    Deque<Integer> d = new Deque<>();

    @Test
    public void hasNext() throws Exception {
        d.addFirst(1);
        d.addLast(2);
        Iterator it = d.iterator();
        assertTrue(it.hasNext());
        d.removeFirst();
        d.removeFirst();
        assertFalse(it.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void next() throws Exception {
        d.addFirst(1);
        d.addLast(2);
        Iterator it = d.iterator();
        assertEquals(1, it.next());
        assertEquals(2, it.next());
        it.next();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void remove() throws Exception {
        d.addFirst(1);
        d.addLast(2);
        Iterator it = d.iterator();
        it.remove();
    }

}