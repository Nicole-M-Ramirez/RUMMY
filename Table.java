import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
/**
*	This GUI assumes that you are using a 52 card deck and that you have 13 sets in the deck.
*	The GUI is simulating a playing table
	@author Patti Ordonez
*/
public class Table extends JFrame implements ActionListener
{
	final static int numDealtCards = 9;
	JPanel player1;
	JPanel player2;
	JPanel deckPiles;
	JLabel deck;
	JLabel stack;
	JList p1HandPile;
	JList p2HandPile;
	Deck cardDeck;
	Deck stackDeck;

	SetPanel [] setPanels = new SetPanel[13];
	JLabel topOfStack;
	JLabel deckPile;
	JButton p1Stack;
	JButton p2Stack;

	JButton p1Deck;
	JButton p2Deck;

	JButton p1Lay;
	JButton p2Lay;

	JButton p1LayOnStack;
	JButton p2LayOnStack;

	DefaultListModel p1Hand;
	DefaultListModel p2Hand;

	//Track of cards in hand
	int p1CardHold = 9;
	int p2CardHold = 9;

	private void deal(Card [] cards)
	{
		for(int i = 0; i < cards.length; i ++)
			cards[i] = (Card)cardDeck.dealCard();
	}

	public Table()
	{
		super("The Card Game of the Century");

		setLayout(new BorderLayout());
		setSize(1200,700);


		cardDeck = new Deck();

		for(int i = 0; i < Card.suit.length; i++){
			for(int j = 0; j < Card.rank.length; j++){
				Card card = new Card(Card.suit[i],Card.rank[j]);
				cardDeck.addCard(card);
			}
		}
		cardDeck.shuffle();
		stackDeck = new Deck();

		JPanel top = new JPanel();

		for (int i = 0; i < Card.rank.length;i++)
			setPanels[i] = new SetPanel(Card.getRankIndex(Card.rank[i]));


		top.add(setPanels[0]);
		top.add(setPanels[1]);
		top.add(setPanels[2]);
		top.add(setPanels[3]);

		player1 = new JPanel();

		player1.add(top);




		add(player1, BorderLayout.NORTH);
		JPanel bottom = new JPanel();


		bottom.add(setPanels[4]);
		bottom.add(setPanels[5]);
		bottom.add(setPanels[6]);
		bottom.add(setPanels[7]);
		bottom.add(setPanels[8]);

		player2 = new JPanel();




		player2.add(bottom);
		add(player2, BorderLayout.SOUTH);


		JPanel middle = new JPanel(new GridLayout(1,3));

		p1Stack = new JButton("Stack");
		p1Stack.addActionListener(this);
		p1Deck = new JButton("Deck ");
		p1Deck.addActionListener(this);
		p1Lay = new JButton("Lay  ");
		p1Lay.addActionListener(this);
		p1LayOnStack = new JButton("LayOnStack");
		p1LayOnStack.addActionListener(this);


		/*
		Aqui las cartas se añaden a la mano de 'player 1' por primera vez.
		 El arrayList con las cartas 'player 1' se llama 'cardsPlayer1'
		*/
		Card [] cardsPlayer1 = new Card[numDealtCards];
		deal(cardsPlayer1);
		p1Hand = new DefaultListModel();

		System.out.print("Initial Player 1: ");//Para desplegar la mano inicial

		for(int i = 0; i < cardsPlayer1.length; i++){
			p1Hand.addElement(cardsPlayer1[i]);
			System.out.print(cardsPlayer1[i] + ", ");//conforme se añade las cartas en la mano, tambien se desplegan en el terminal
		}
		System.out.println("");

		p1HandPile = new JList(p1Hand);



		middle.add(new HandPanel("Player 1", p1HandPile, p1Stack, p1Deck, p1Lay, p1LayOnStack));

		deckPiles = new JPanel();
		deckPiles.setLayout(new BoxLayout(deckPiles, BoxLayout.Y_AXIS));
		deckPiles.add(Box.createGlue());
		JPanel left = new JPanel();
		left.setAlignmentY(Component.CENTER_ALIGNMENT);


		stack = new JLabel("Stack");
		stack.setAlignmentY(Component.CENTER_ALIGNMENT);

		left.add(stack);
		topOfStack = new JLabel();
		topOfStack.setIcon(new ImageIcon(Card.directory + "blank.gif"));
		topOfStack.setAlignmentY(Component.CENTER_ALIGNMENT);
		left.add(topOfStack);
		deckPiles.add(left);
		deckPiles.add(Box.createGlue());

		JPanel right = new JPanel();
		right.setAlignmentY(Component.CENTER_ALIGNMENT);

		deck = new JLabel("Deck");

		deck.setAlignmentY(Component.CENTER_ALIGNMENT);
		right.add(deck);
		deckPile = new JLabel();
		deckPile.setIcon(new ImageIcon(Card.directory + "b.gif"));
		deckPile.setAlignmentY(Component.CENTER_ALIGNMENT);
		right.add(deckPile);
		deckPiles.add(right);
		deckPiles.add(Box.createGlue());
		middle.add(deckPiles);


		p2Stack = new JButton("Stack");
		p2Stack.addActionListener(this);
		p2Deck = new JButton("Deck ");
		p2Deck.addActionListener(this);
		p2Lay = new JButton("Lay  ");
		p2Lay.addActionListener(this);
		p2LayOnStack = new JButton("LayOnStack");
		p2LayOnStack.addActionListener(this);

		/*
		Aqui las cartas se añaden a la mano de 'player 2' por primera vez.
		 El arrayList con las cartas 'player 2' se llama 'cardsPlayer2'.
		*/
		Card [] cardsPlayer2 = new Card[numDealtCards];
		deal(cardsPlayer2);
		p2Hand = new DefaultListModel();

		System.out.print("Initial Player 2: ");//Para desplegar la mano inicial

		for(int i = 0; i < cardsPlayer2.length; i++){
			p2Hand.addElement(cardsPlayer2[i]);
			System.out.print(cardsPlayer2[i] + ", ");//conforme se añade las cartas en la mano, tambien se desplegan en el terminal
		}
		System.out.println("");

		p2HandPile = new JList(p2Hand);

		middle.add(new HandPanel("Player 2", p2HandPile, p2Stack, p2Deck, p2Lay, p2LayOnStack));

		add(middle, BorderLayout.CENTER);

		JPanel leftBorder = new JPanel(new GridLayout(2,1));


		setPanels[9].setLayout(new BoxLayout(setPanels[9], BoxLayout.Y_AXIS));
		setPanels[10].setLayout(new BoxLayout(setPanels[10], BoxLayout.Y_AXIS));
		leftBorder.add(setPanels[9]);
		leftBorder.add(setPanels[10]);
		add(leftBorder, BorderLayout.WEST);

		JPanel rightBorder = new JPanel(new GridLayout(2,1));

		setPanels[11].setLayout(new BoxLayout(setPanels[11], BoxLayout.Y_AXIS));
		setPanels[12].setLayout(new BoxLayout(setPanels[12], BoxLayout.Y_AXIS));
		rightBorder.add(setPanels[11]);
		rightBorder.add(setPanels[12]);
		add(rightBorder, BorderLayout.EAST);

	}

	/*
	p1CardHold muestra la cantidad de cartas que hay al momento en la mano de jugador 1.
	p2CardHold muestra la cantidad de cartas que hay al momento en la mano de jugador 2.
	Si en la mano ya se encuentra 9 cartas, no se pude añadir otra. Si hay menos de 9 cartas
	se añade otra a la mano y la cantidad de p1CardHold o p2CardHold aumenta por 1.
	*/
	public void actionPerformed(ActionEvent e)
	{
		Object src = e.getSource();
		if(p1Deck == src|| p2Deck == src){

			Card card = cardDeck.dealCard();

			/*
			Añade una carta a la mano de 'player1' desde el 'deck' solo si quedan cartas en el 'deck' y
			en la mano del jugador hay menos de 9 cartas
			*/
			if (card != null){
				if(src == p1Deck){
					if(p1CardHold < 9 ){
						p1Hand.addElement(card);
						p1CardHold+=1;
						System.out.println("p1CardHold: "+p1CardHold);
					}
				}
			} // :)

			/*
			Añade una carta a la mano de 'player2' desde el 'deck' solo si quedan cartas en el 'deck' y
			en la mano del jugador hay menos de 9 cartas
			*/
			if (card != null){
				if(src == p2Deck){
					if(p2CardHold < 9 ){
						p2Hand.addElement(card);
						p2CardHold+=1;
						System.out.println("p2CardHold: "+p2CardHold);
					}
				}
			}

			if(cardDeck.getSizeOfDeck() == 0)
				deckPile.setIcon(new ImageIcon(Card.directory + "blank.gif"));

		}
		if(p1Stack == src || p2Stack == src){

			Card card = stackDeck.removeCard();

			if(card != null){
				Card topCard = stackDeck.peek();
				if (topCard != null){
					topOfStack.setIcon(topCard.getCardImage());
				}
				else{
					topOfStack.setIcon(new ImageIcon(Card.directory + "blank.gif"));
				}

				/*
				Toma una carta del stack y la pone en la mano de 'player1' solo si
				 en su mano hay menos de 9 cartas
				*/
				if(p1Stack == src){
					if(p1CardHold < 9){
						p1Hand.addElement(card);
						p1CardHold+=1;//modicicado de -= a +=
						System.out.println("p1CardHold: "+p1CardHold);
					}
				}

				/*
				Toma una carta del stack y la pone en la mano de 'player2' solo si
				 en su mano hay menos de 9 cartas
				*/
				else{
					if(p2CardHold < 9){
						p2Hand.addElement(card);
						p2CardHold+=1;//modicicado de -= a +=
						System.out.println("p2CardHold: "+p2CardHold);
					}
				}


			}

		}

		if(p1Lay == src){
			Object [] cards = p1HandPile.getSelectedValues();
			if (cards != null)
				for(int i = 0; i < cards.length; i++)
				{
					Card card = (Card)cards[i];
					layCard(card);
					p1Hand.removeElement(card);

					p1CardHold-=1;
					System.out.println("p1CardHold: "+p1CardHold);
				}
				// p1CardHold-=1;
				// System.out.println("p1CardHold: "+p1CardHold);
		}


		if(p2Lay == src){
			Object [] cards = p2HandPile.getSelectedValues();
			if (cards != null)
				for(int i = 0; i < cards.length; i++)
				{
					Card card = (Card)cards[i];
					layCard(card);
					p2Hand.removeElement(card);

					p2CardHold-=1;
				    System.out.println("p2CardHold: "+p2CardHold);
				}
				// p2CardHold-=1;
				// System.out.println("p2CardHold: "+p2CardHold);
		}

		/*
		En este if se coloca las cartas de la mano hacia el stack. Cuando se coloca una carta
		que estaba en la mano en el stack, el contador disminuye. el contador DEBE de estar
		dentro del if porque si no cada vez que precionemos el boton aunque no allamos selecionado
	    ninguna carta, el contador bajaria sin razon.
		*/
		if(p1LayOnStack == src){
			int [] num  = p1HandPile.getSelectedIndices();
			if (num.length == 1)
			{
				Object obj = p1HandPile.getSelectedValue();
				if (obj != null)
				{
					p1Hand.removeElement(obj);
					Card card = (Card)obj;
					stackDeck.addCard(card);
					topOfStack.setIcon(card.getCardImage());

					p1CardHold -=1;
					System.out.println("p1CardHold: "+p1CardHold);
				}
			}
		}

		//Lo mismo que arriba
		if(p2LayOnStack == src){
			int [] num  = p2HandPile.getSelectedIndices();
			if (num.length == 1)
			{
				Object obj = p2HandPile.getSelectedValue();
				if (obj != null)
				{
					p2Hand.removeElement(obj);
					Card card = (Card)obj;
					stackDeck.addCard(card);
					topOfStack.setIcon(card.getCardImage());

					p2CardHold-=1;
					System.out.println("p2CardHold: "+p2CardHold);
				}
			}
		}

	}

	// public static void main(String args[])
	// {
	// 	Table t = new Table();
	// 	t.setVisible(true);
	// }
	void layCard(Card card)
	{
		char rank = card.getRank();
		char suit = card.getSuit();
		int suitIndex =  Card.getSuitIndex(suit);
		int rankIndex =  Card.getRankIndex(rank);
		//setPanels[rankIndex].array[suitIndex].setText(card.toString());
		System.out.println("laying " + card);
		setPanels[rankIndex].array[suitIndex].setIcon(card.getCardImage());
	}

}

class HandPanel extends JPanel
{

	public HandPanel(String name,JList hand, JButton stack, JButton deck, JButton lay, JButton layOnStack)
	{
		//model = hand.createSelectionModel();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//		add(Box.createGlue());
		JLabel label = new JLabel(name);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(label);
		stack.setAlignmentX(Component.CENTER_ALIGNMENT);
//		add(Box.createGlue());
		add(stack);
		deck.setAlignmentX(Component.CENTER_ALIGNMENT);
//		add(Box.createGlue());
		add(deck);
		lay.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lay);
		layOnStack.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(layOnStack);
		add(Box.createGlue());
		add(hand);
		add(Box.createGlue());
	}

}
class SetPanel extends JPanel
{
	private Set data;
	JButton [] array = new JButton[4];

	public SetPanel(int index)
	{
		super();
		data = new Set(Card.rank[index]);

		for(int i = 0; i < array.length; i++){
			array[i] = new JButton("   ");
			add(array[i]);
		}
	}

}
