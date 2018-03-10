package stack;

/**
 * Created by Diana on 28.01.2017.
 */
public class ResizeArrayStringStack implements StringStack {
    private String[] stack;
    private int first;

    public ResizeArrayStringStack() {
        stack = new String[2];
        first = 0;
    }

    @Override
    public void push(String s) {
        if (first == stack.length) resize(2*stack.length);
        stack[first++] = s;
    }

    @Override
    public String pop() {
        if (first == 0) return null;
        String s = stack[--first];
        stack[first] = null;
        if (first <= 0.25*stack.length) resize(stack.length/2);
        return s;
    }

    private void resize(int length) {
        String[] copy  = new String[length];
        for (int i = 0; i < first; i++) copy[i] = stack[i];
        stack = copy;
        copy = null;
    }

    @Override
    public boolean isEmpty() {
        return first == 0;
    }

    @Override
    public int count() {
        return first;
    }

    public int getSize(){
        return stack.length;
    }
}
