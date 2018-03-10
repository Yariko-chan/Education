import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Diana on 30.01.2017.
 */
public class DequeFailedTests {

    @Test
    public void testRandom2() throws Exception {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.enqueue(168);
        assertFalse(rq.isEmpty());
        assertEquals(168, (int)rq.dequeue());
        rq.enqueue(246);
        assertEquals(246, (int)rq.dequeue());
        assertTrue(rq.isEmpty());
        rq.enqueue(45);
    }

    @Test
    public void testRandomness14() throws Exception {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.enqueue(168);
        rq.enqueue(246);
        rq.enqueue(45);
        rq.enqueue(7);
        rq.enqueue(394);
        rq.enqueue(5145);
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
    }

    @Test
    public void testLoitering6b() throws Exception {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        rq.enqueue("AJTPNDQHWI");
        rq.dequeue();
        rq.enqueue("NIGUSTUFGF");
        rq.enqueue("SKEHVZXWMJ");
        rq.dequeue();
        rq.dequeue();
        rq.enqueue("QBUICEFUAM");
        rq.dequeue();
        rq.enqueue("ISZBSDLVLM");
    }
}
