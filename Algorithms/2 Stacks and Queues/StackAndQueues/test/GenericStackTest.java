import org.junit.Test;
import stack.generics.LinkedListStack;
import stack.ResizeArrayStringStack;
import stack.StringStack;
import stack.generics.ResizeArrayStack;

import static org.junit.Assert.*;

/**
 * Created by Diana on 28.01.2017.
 */
public class GenericStackTest {
    private String string1 = "String1";
    private String string2 = "String2";

    @Test
    public void LinkedListStackTest() {
        LinkedListStack<String> stack = new LinkedListStack<>();
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.count());
        stack.push(string1);
        stack.push(string2);
        assertEquals(string2, stack.pop());
        assertFalse(stack.isEmpty());
        assertEquals(1, stack.count());
        assertEquals(string1, stack.pop());
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.count());
    }

    @Test
    public void ResizeArrayStackTest() {
        ResizeArrayStack<String> stack = new ResizeArrayStack<>();
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.count());
        stack.push(string1);
        stack.push(string2);
        assertEquals(string2, stack.pop());
        assertFalse(stack.isEmpty());
        assertEquals(1, stack.count());
        assertEquals(string1, stack.pop());
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.count());
    }

    @Test
    public void ResizeTest(){
        ResizeArrayStack stack = new ResizeArrayStack();
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

    @Test
    public void iterationTest() {
        LinkedListStack<String> llStack  = new LinkedListStack<>();
        llStack.push(string1);
        llStack.push(string2);
        llStack.push(string1);
        llStack.push(string2);
        llStack.push(string1);
        llStack.push(string2);
        for (String s:llStack){
            System.out.println(s);
        }

        ResizeArrayStack<String> raStack  = new ResizeArrayStack<>();
        raStack.push(string1);
        raStack.push(string2);
        raStack.push(string1);
        raStack.push(string2);
        raStack.push(string1);
        raStack.push(string2);
        for (String s:raStack){
            System.out.println(s);
        }
    }

}