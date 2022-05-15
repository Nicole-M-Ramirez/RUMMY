
public class Stack {
    private Card arrStack[];
    private static final int DEFAULT_SIZE = 54;
    private int top;
    private int stackSize;
 
    // Stack constructor
    Stack(int size)
    {
        arrStack = new Card[size];
        stackSize = size;
        top = 0;
    }

    Stack(){
        this(DEFAULT_SIZE);
    }
 

    //
    public boolean addCard(Object value)
    {
        if (top >= stackSize)
        {
            return false;
        }
 
       // top+=1;
        arrStack[top++] = (Card)value;
        //top+=1;

        return true;
    }
 
    // Utility function to pop a top element from the stack
    public Card removeCard()
    {
        if (top == 0)
        {
            return null;
        }
 
        //top-=1;
        return arrStack[--top];
    }

    public Card peek(){

        if (top == 0) return null;
        return arrStack[top-1];
    }

    //Check if stack is empty
    public boolean isEmpty()
    {
        return top == 0;
    }

    //Chec if stack is full
    public boolean isFull()
    {
        return top == stackSize;
    }

    public int length(){
        return stackSize;
    }
}
