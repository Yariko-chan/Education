import org.junit.Test;
import queue.LinkedListStringQueue;
import queue.ResizeArrayStringQueue;
import queue.StringQueue;
import stack.ResizeArrayStringStack;

import static org.junit.Assert.*;

/**
 * Created by Diana on 28.01.2017.
 */
public class StringQueueTest {
    private String string1 = "String1";
    private String string2 = "String2";

    public void queueTest(StringQueue qu){
        assertTrue(qu.isEmpty());
        assertEquals(0, qu.size());

        qu.enqueue(string1);
        qu.enqueue(string2);
        qu.enqueue(string2);
        assertFalse(qu.isEmpty());
        assertEquals(3, qu.size());

        assertEquals(string1, qu.dequeue());
        assertFalse(qu.isEmpty());
        assertEquals(2, qu.size());

        assertEquals(string2, qu.dequeue());
        assertFalse(qu.isEmpty());
        assertEquals(1, qu.size());

        assertEquals(string2, qu.dequeue());
        assertTrue(qu.isEmpty());
        assertEquals(0, qu.size());

        qu.enqueue(string1);
        qu.enqueue(string2);
        qu.enqueue(string2);
        assertFalse(qu.isEmpty());
        assertEquals(3, qu.size());
    }

    @Test
    public void LinkedListQueueTest() {
        StringQueue qu = new LinkedListStringQueue();
        queueTest(qu);
    }

    @Test
    public void ResizeArrayQueueTest() {
        StringQueue qu = new ResizeArrayStringQueue();
        queueTest(qu);
    }

    @Test
    public void ResizeTest(){
        ResizeArrayStringQueue qu = new ResizeArrayStringQueue();
        qu.enqueue(string1);
        qu.enqueue(string1);
        qu.enqueue(string1);
        qu.enqueue(string1);
        qu.enqueue(string1);
        assertEquals(5, qu.size());
        assertEquals(8, qu.getSize());
        qu.dequeue();
        qu.dequeue();
        qu.dequeue();
        assertEquals(2, qu.size());
        assertEquals(4, qu.getSize());

    }
}