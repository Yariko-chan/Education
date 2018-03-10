import org.junit.Test;
import stack.LinkedListStringStack;
import stack.ResizeArrayStringStack;
import stack.StringStack;

import static org.junit.Assert.*;

/**
 * Created by Diana on 28.01.2017.
 */
public class StringStackTest {
    String string1 = "String1";

    public static void main(String[] args) {
        StringStack stack = new LinkedListStringStack();
        stackTest(stack);

        stack = new ResizeArrayStringStack();
        stackTest(stack);
    }

    private static void stackTest(StringStack stack) {
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.count());
        stack.push("String1");
        stack.push("String2");
        assertEquals("String2", stack.pop());
        assertFalse(stack.isEmpty());
        assertEquals(1, stack.count());
        assertEquals("String1", stack.pop());
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.count());
    }

    @Test
    public void ResizeTest(){
        ResizeArrayStringStack stack = new ResizeArrayStringStack();
        stack.push(string1);
        stack.push(string1);
        stack.push(string1);
        stack.push(string1);
        stack.push(string1);
        assertEquals(5, stack.count());
        assertEquals(8, stack.getSize());
        stack.pop();
        stack.pop();
        stack.pop();
        assertEquals(2, stack.count());
        assertEquals(4, stack.getSize());

    }
}