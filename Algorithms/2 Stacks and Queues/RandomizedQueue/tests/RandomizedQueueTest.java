import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Created by Diana on 29.01.2017.
 */
public class RandomizedQueueTest {
    RandomizedQueue<Integer> qu = new RandomizedQueue<>();

    @Test
    public void isEmpty() throws Exception {
        assertTrue(qu.isEmpty());

        qu.enqueue(1);
        qu.enqueue(2);
        assertFalse(qu.isEmpty());

        qu.dequeue();
        qu.dequeue();
        assertTrue(qu.isEmpty());
    }

    @Test
    public void size() throws Exception {
        assertEquals(0, qu.size());

        qu.enqueue(1);
        qu.enqueue(2);
        assertEquals(2, qu.size());

        qu.dequeue();
        qu.dequeue();
        assertEquals(0, qu.size());

    }

    @Test(expected = NullPointerException.class)
    public void enqueue() throws Exception {
        qu.enqueue(1);
        assertEquals(1, (int)qu.sample());
        qu.enqueue(2);
        qu.enqueue(3);
        int n = qu.sample();
        assertTrue(n >= 1 && n <= 3);

        qu.dequeue();
        qu.dequeue();
        qu.dequeue();
        for (int i = 0; i < 10; i++) {
            qu.enqueue(1);
        }
        assertTrue(1 == qu.sample());

        qu.enqueue(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void dequeue() throws Exception {
        qu.enqueue(2);
        assertEquals(2, (int) qu.dequeue());

        for (int i = 0; i < 10; i++) {
            qu.enqueue(1);
        }
        assertTrue(1 == qu.dequeue());
        for (int i = 0; i < 9; i++) {
            assertTrue(1 == qu.dequeue());
        }

        qu.dequeue();
    }

    @Test(expected = NoSuchElementException.class)
    public void sample() throws Exception {
        int count = 10;
        for (int i = 0; i < count; i++) {
            qu.enqueue(1);
            assertTrue(1 == qu.sample());
        }
        assertEquals(count, qu.size());
        for (int i = 0; i < count; i++) {
            qu.dequeue();
        }
        assertTrue(qu.isEmpty());
        qu.sample();
    }

    @Test
    public void iterator() throws Exception {
        int count = 5;
        boolean[] iterated = new boolean[count];
        for (int i = 0; i < iterated.length; i++) {
            qu.enqueue(i);
        }
        for (int n:qu) {
            iterated[n] = true;

            boolean[] iterated_inner = new boolean[count];
            for (int m:qu) {
                iterated_inner[m] = true;
            }
            for (boolean b:iterated_inner) {
                assertTrue(b);
            }
        }
        for (boolean b:iterated) {
            assertTrue(b);
        }
    }

}