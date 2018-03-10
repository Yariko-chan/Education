import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by Diana on 29.01.2017.
 */
public class DequeTest {
    Deque<Integer> d = new Deque<Integer>();

    @Test
    public void isEmpty() throws Exception {
        assertTrue(d.isEmpty());
        d.addFirst(1);
        assertFalse(d.isEmpty());
        d.removeFirst();
        assertTrue(d.isEmpty());
    }

    @Test
    public void size() throws Exception {
        assertEquals(0, d.size());
        d.addFirst(1);
        assertEquals(1, d.size());
        d.removeFirst();
        assertEquals(0, d.size());
    }

    @Test
    public void addFirst() throws Exception {
        //add to empty deque
        d.addFirst(1);
        assertFalse(d.isEmpty());
        assertEquals(1, d.size());

        //add to non-empty deque
        d.addFirst(2);
        assertFalse(d.isEmpty());
        assertEquals(2, d.size());
    }

    @Test
    public void addLast() throws Exception {
        //add to empty deque
        d.addLast(1);
        assertFalse(d.isEmpty());
        assertEquals(1, d.size());

        //add to non-empty deque
        d.addLast(2);
        assertFalse(d.isEmpty());
        assertEquals(2, d.size());
    }

    @Test
    public void removeFirst() throws Exception {
        //remove from non-empty deque
        d.addFirst(1);
        d.addFirst(2);
        assertEquals(2, (int)d.removeFirst());
        assertEquals(1, (int)d.removeFirst());

        assertTrue(d.isEmpty());
        assertEquals(0, d.size());

        //non-empty - empty - non-empty
        d.addLast(1);
        d.addLast(2);
        assertFalse(d.isEmpty());
        assertEquals(2, d.size());
        assertEquals(1, (int) d.removeFirst());
        assertEquals(2, (int) d.removeFirst());
    }

    @Test
    public void removeLast() throws Exception {
        d.addLast(1);
        d.addLast(2);
        assertEquals(2, (int) d.removeLast());
        assertEquals(1, (int) d.removeLast());

        assertTrue(d.isEmpty());
        assertEquals(0, d.size());

        d.addFirst(1);
        d.addFirst(2);
        assertFalse(d.isEmpty());
        assertEquals(2, d.size());
        assertEquals(1, (int) d.removeLast());
        assertEquals(2, (int) d.removeLast());
    }

    @Test
    public void iterator() throws Exception {
        //if iterator goes from first to last
        d.addFirst(4);
        d.addFirst(3);
        d.addFirst(2);
        d.addFirst(1);
        d.addLast(5);
        d.addLast(6);
        d.addLast(7);
        d.addLast(8);
        int i = 1;
        for (int n:d){
            assertEquals(i, n);
            i++;
        }
        //nested iterator
        i = 1;
        for (int n:d) {
            int j = 1;
            assertEquals(i, n);
            for (int m:d) {
                assertEquals(j, m);
                j++;
            }
            i++;
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void removeFromEmptyFirst() {
        d.removeFirst();
    }

    @Test(expected = NoSuchElementException.class)
    public void removeFromEmptyLast() {
        d.removeLast();
    }

    @Test(expected = NoSuchElementException.class)
    public void removeFromEmptyAfterNonEmpty() {
        d.addFirst(1);
        d.addLast(2);
        d.removeFirst();
        d.removeLast();
        d.removeLast();
    }

    @Test(expected = NullPointerException.class)
    public void addNullFirst() {
        d.addFirst(null);
    }

    @Test(expected = NullPointerException.class)
    public void addNullLast() {
        d.addLast(null);
    }

    @Test
    public void main() throws Exception {

    }

}