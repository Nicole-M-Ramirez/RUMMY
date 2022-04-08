public interface StackInterface {
    //Constructor
    public Stack Stack();

    //Push 'value' inside the stack
    public boolean push(Object value);

    //Pops the 'top' value out of the stack
    public Object pop();

    //Shows the 'top' value
    public Object showTop();

    //Tells if stack is empty
    public boolean isEmpty();

    //Tells if the stack is full
    public boolean isFull();

    public int length();
}
