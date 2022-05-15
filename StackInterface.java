public interface StackInterface {
    //Constructor
    public Stack Stack();

    //Push 'value' inside the stack
    public boolean addCard(Object value);

    //Pops the 'top' value out of the stack
    public Card removeCard();

    //Shows the 'top' value
    public Card peek();

    //Tells if stack is empty
    public boolean isEmpty();

    //Tells if the stack is full
    public boolean isFull();

    public int length();
}
