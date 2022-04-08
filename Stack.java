
public class Stack {
    private Object arrStack[];
    private int top;
    private int stackSize;
 
    // Stack constructor
    Stack(int size)
    {
        arrStack = new Object[size];
        stackSize = size;
        top = -1;
    }
 
    //
    public boolean push(Object value)
    {
        if (isFull())
        {
            return false;
        }
 
        top+=1;
        arrStack[top] = value;

        return true;
    }
 
    // Utility function to pop a top element from the stack
    public Object pop()
    {
        if (isEmpty())
        {
            return null;
        }
 
        top-=1;
        return arrStack[top];
    }

    public Object showTop()
    {
        return arrStack[top];
    }

    //Check if stack is empty
    public boolean isEmpty()
    {
        return top == -1;
    }

    //Chec if stack is full
    public boolean isFull()
    {
        return top == stackSize - 1;
    }

    public int length(){
        return stackSize+1;
    }
}
