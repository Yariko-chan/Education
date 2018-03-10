import org.junit.Test;
import queue.generics.LinkedListQueue;
import queue.generics.ResizeArrayQueue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Diana on 28.01.2017.
 */
public class GenericQueueTest {
    private String string1 = "String1";
    private String string2 = "String2";

    @Test
    public void LinkedListQueueTest() {
        LinkedListQueue qu = new LinkedListQueue();
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
    public void ResizeArrayQueueTest() {
        ResizeArrayQueue qu = new ResizeArrayQueue();
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
    public void ResizeTest(){
        ResizeArrayQueue qu = new ResizeArrayQueue();
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

    @Test
    public void iterationTest() {
        LinkedListQueue<String> llQu = new LinkedListQueue<>();
        llQu.enqueue(string1);
        llQu.enqueue(string2);
        llQu.enqueue(string1);
        llQu.enqueue(string2);
        llQu.enqueue(string1);
        llQu.enqueue(string2);
        for (String s:llQu){
            System.out.println(s);
        }

        ResizeArrayQueue<String> raQu = new ResizeArrayQueue<>();
        raQu.enqueue(string1);
        raQu.enqueue(string2);
        raQu.enqueue(string1);
        raQu.enqueue(string2);
        raQu.enqueue(string1);
        raQu.enqueue(string2);
        for (String s:raQu){
            System.out.println(s);
        }
    }
}
