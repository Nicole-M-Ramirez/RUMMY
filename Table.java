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
	JList<Card> p1HandPile;
	JList<Card> p2HandPile;
	Deck cardDeck;

	//Modifique stack para que stackDeck usara mi algoritmo de stack hecho desde 0
	Stack stackDeck;

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

	//Botones para que los jugadores teminen su turno
	JButton p1FinishTurn;
	JButton p2FinishTurn;

	Hand p1Hand;
	Hand p2Hand;
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*
	VARIABLES AÑADIDAS POR MI
	*/

	//Copia de la carta añadida a la mano del jugador
	Object cardAddedToHand = null;

	/*
	Array 'DiscartedCards' para las cartas descartadas por el jugador.
	Variable 'DiscartedCounter' para saber la cantidad de cartas descartadas por el jugador
	*/
	Object [] DiscartedCards = new Object [10];
	int DiscardCounter = -1;

	//VirtualPlayers
	int VirtualPlayers = 0;

	//Para validar que solo se a cojido una carta del stack
	boolean pickedCard = false;
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	private void deal(Card [] cards)
	{
		for(int i = 0; i < cards.length; i ++)
			cards[i] = (Card)cardDeck.dealCard();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/*
	La funcion ComputerPlay() es la que permite que una partida completa pueda ser jugada por la computadora sin intervencion del usuario.
	Un while que correra la funcion hasta que las manos esten basias o el deck este vacio. Este while llamara a la funcion virtualPlayer()
	(explicada mas adelante) que llamara primero para la jugada del jugador 1 y luego del jugador 2 susecivamente hasta que la condicion 
	del while sea false. Luego se llama a la funcion endGame() (explicada mas adelante) para terminar el juego
	*/
	public void ComputerPlay (){

		while(p1Hand.getNumberOfCards() > 0 && p2Hand.getNumberOfCards() > 0 && cardDeck.isEmpty() == false){

		virtualPlayer(p1Hand, p1HandPile);
		System.out.println("Player 1"); //Imprime que jugador realizo la jugada
		FinishTurn(); //Imprime datos de la jugada (explicada mas adelante)
		System.out.println("\tHand now: "+ p1Hand.toString()); //Imprime datos de la jugada (explicada mas adelante)

		virtualPlayer(p2Hand, p2HandPile);
		System.out.println("Player 2"); //Imprime que jugador realizo la jugada
		FinishTurn();//Imprime datos de la jugada (explicada mas adelante)
		System.out.println("\tHand now: "+ p2Hand.toString());//Imprime datos de la jugada (explicada mas adelante)

		}
		
		endGame();
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Table()
	{
		
		super("The Card Game of the Century");

		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//Permite al usuario decidir cuantos jugadores virtuales quiere
		Scanner scan = new Scanner(System.in); //scan para obtener el input del teclado

		boolean valid = false; //Para el input validation

		//Validacion del input
		while(valid == false){
			System.out.print("Insert number of virtual players (0, 1 or 2): "); //elige cuantos jugadores virtuales nesecita
			VirtualPlayers =  scan.nextInt();//resive el input del teclado
			System.out.println("");

			//Si el input es mayor de 2 o menor de 0 el input es invalido y se le pide al usuario que reingrese el input.
			if(VirtualPlayers >= 0 && VirtualPlayers <= 2){
				valid = true;
			}
			else{
				System.out.println("Invalid input. Please insert a number from 0 to 2 ");
				VirtualPlayers = 0;
			}
		}

		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
		stackDeck = new Stack();

		JPanel top = new JPanel();

		for (int i = 0; i < Card.rank.length;i++){
			setPanels[i] = new SetPanel(Card.getRankIndex(Card.rank[i]));
		}


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

		//Boton para que el jugador 1 termine turno. Se añade al actionListener
		p1FinishTurn = new JButton("FinishTurn");
		p1FinishTurn.addActionListener(this);

		/*
		Aqui las cartas se añaden a la mano de 'player 1' por primera vez.
		 El arrayList con las cartas 'player 1' se llama 'cardsPlayer1'
		*/
		Card [] cardsPlayer1 = new Card[numDealtCards];
		deal(cardsPlayer1);
		p1Hand = new Hand();



		for(int i = 0; i < cardsPlayer1.length; i++){
			p1Hand.addCard(cardsPlayer1[i]);	
		}
		
		//Inprime la mano incical del jugador 1 al inicio del juego
		System.out.println("Initial Player 1: " + p1Hand.toString());

		p1HandPile = new JList<Card>(p1Hand.getHand());


		//añadi 'p1FinishTurn'
		middle.add(new HandPanel("Player 1", p1HandPile, p1Stack, p1Deck, p1Lay, p1LayOnStack, p1FinishTurn));

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

		//Boton para cuando p2 termine el turno
		p2FinishTurn = new JButton("FInishTurn");
		p2FinishTurn.addActionListener(this);




		/*
		Aqui las cartas se añaden a la mano de 'player 2' por primera vez.
		 El arrayList con las cartas 'player 2' se llama 'cardsPlayer2'.
		*/
		Card [] cardsPlayer2 = new Card[numDealtCards];
		deal(cardsPlayer2);
		p2Hand = new Hand();


		for(int i = 0; i < cardsPlayer2.length; i++){
			p2Hand.addCard(cardsPlayer2[i]);
		}
		
		//Imprime la mano incical del jugador 1 al inicio del juego
		System.out.println("Initial Player 2: " + p2Hand.toString());

		p2HandPile = new JList(p2Hand.getHand());


		middle.add(new HandPanel("Player 2", p2HandPile, p2Stack, p2Deck, p2Lay, p2LayOnStack, p2FinishTurn));

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

		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		//Luego de crear las manos, y el deck, si el jugador selecciono 2 jugadores virtuales, llamara a la funcion
		//ComputerPlay() para inicial la partida virtual
		if(VirtualPlayers == 2){
			ComputerPlay();
		}

	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/*
	FinishTurn() es la funcion que imprime los datos de la partida luege de que el jugador termino. Primer verifica si el jugador añadio
	una carta a la mano. En el caso de que no la alla añadido, el jugo termina, en el otro caso imprime que carta se añadio.

	Segundo verifica si el jugador descarto alguna carta. Si no la descarto, imprime null, de lo contrario imprime el array con todas las cartas
	que se descartaron.

	Por ultimo todas las variables se reinician para poder volverlas a usar.
	*/
	public void FinishTurn () {
		//Si p1 no añadio ninguna carta a su mano retorna NULL, de lo contrario imprime la carta que añadio a su mano
		if(cardAddedToHand == null){
			endGame();
		} else {
			System.out.println("    Added: " + cardAddedToHand);
		}

		//Si p1 no descarto ninguna carta retorna NULL, de lo contrario imprime todas las cartas que descarto
		if(DiscardCounter == -1){
			System.out.println("    Discarted: NULL ");
		} else{
			System.out.print("    Discarted: [ ");
			for(int i = 0; i <= DiscardCounter; i++){
				System.out.print(DiscartedCards[i] + ", ");
			}
			System.out.println(" ]");
		}

		//Reinicia el contador de cartas descartadas, el arreglo con las cartas descartadas y la variable de carta añadida
		DiscardCounter = -1;
		DiscartedCards = new Object[9];
		cardAddedToHand = null;
		pickedCard = false;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/*
	Añade una carta a la mano del jugador desde el 'deck' solo si quedan cartas en el 'deck' y
	en la mano del jugador hay menos de 9 cartas
	*/
	/*
	La variable inx contiene en indice de la carta que se acaba de añadir a la mano.
	ardAddedToHand contiene la carta que se acaba de añadir
	*/
	/*
	La variable pickedCard es falsa solo si no se an añadido ninguna carta ni del stack ni del deck. Si es sierta ni el stack ni el deck
	van a funcionar. Esto para garantizar que solo se pueden añadir una carta por jugada.	*/
	public void deckButton (Hand pHand, Card card) {
		if(pickedCard == false){
			pHand.addCard(card);

			/*
			La variable inx contiene en indice de la carta que se acaba de añadir a la mano.
			cardAddedToHand contiene la carta que se acaba de añadir
			*/
			int inx = pHand.findCard(card);
			cardAddedToHand = pHand.getCard(inx);
		}
		pickedCard = true;
    }

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/*
	La variable inx contiene en indice de la carta que se acaba de añadir a la mano.
	cardAddedToHand contiene la carta que se acaba de añadir
	*/
	public void stackButton (Hand pHand, Card card) {
		if(pickedCard == false){
			pHand.addCard(card);

			//para hacer una copia de la carta que se añadio a la mano. la variable inx posee una copia del indice donde se encuentra
			//la carta que se acaba de  añadir.
			int inx = pHand.findCard(card);
			cardAddedToHand = pHand.getCard(inx);
		}

		//Comprueba de que si se añadio una carta. Esto para que no se pueda descartar sin añadir primero
		pickedCard = true;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/*
	lay es el boton que permite hacer jugadas con los set. El jugador elige un set y primero se comprueba que este set solo sea de 
	3 minimo o 4 cartas maximo. Si esta condicion no es cierta, se despliega un mensaje con el error y el resto de la funcion no se
	ejecuta.

	Luego con el for se verifica que todas las cartas tengan el mismo rank. Si no tienen el mismo rank, se despliega un mensaje con el
	error y el resto de la funcion no se ejecuta.

	Luego de este punto el resto de la funcion se ejecuta como normalemte lo hacia
	*/
	public void layButton (JList<Card> pHandPile, Hand pHand ) {

		//Si pickedCard es false, significa que el jugador no a tomado una carta
		//y siempre deve comenzar tomando una carta
		if(pickedCard == false){
			System.out.println("   Please peak a card from either the stack or the deck before discarting");
			return;
		}

		Object [] cards = pHandPile.getSelectedValues();

		//Verifica que para ley se nesecitan aver selecionado minimo 3 y maximo 4 cartas
		if(cards.length < 3 || cards.length > 4){
			System.out.println("You need to select 3 or 4 cards for this action");
			return;
		}


		//Verifica que todas las cartas seleccionadas tengan el mismo rank
		for (int i = 0; i < cards.length; i++) {
			Card card_1 = (Card)cards[i];

			for (int k = 0; k < cards.length; k++) {
				Card card_2 = (Card)cards[k];

				if (card_1.getRank() != card_2.getRank()) {
					//valid = false;
					System.out.println("All selected cards need to have the same rank for this action");
			      	return;
				}
			}
		}

		if (cards != null)
			for(int i = 0; i < cards.length; i++)
			{
				Card card = (Card)cards[i];
				layCard(card);

				//Para llevar copia de todas las cartas descartadas en la jugada
				DiscartedCards[++DiscardCounter]=card;

				pHand.removeCard(card);			
				}
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		/*
		En esta funcion se coloca las cartas de la mano hacia el stack. Cuando se coloca una carta
		que estaba en la mano en el stack, el contador disminuye. el contador DEBE de estar
		dentro del if porque si no cada vez que precionemos el boton aunque no allamos selecionado
	    ninguna carta, el contador bajaria sin razon.

		Discarted card guarda una copia de las cartas que el jugador descarto
		*/
	public void layOnStackButton (JList<Card> pHandPile, Hand pHand) {

		//Si pickedCard es false, significa que el jugador no a tomado una carta
		//y siempre deve comenzar tomando una carta
		if(pickedCard == false){
			System.out.println("   Please peak a card from either the stack or the deck before discarting");
			return;
		}

		int [] num  = pHandPile.getSelectedIndices();
		if (num.length == 1)
		{
			Object obj = pHandPile.getSelectedValue();

			//DiscartedCards card guarda una copia de las cartas que el jugador descarto
			DiscartedCards[++DiscardCounter]=obj;
			
			if (obj != null)
			{
				pHand.removeCard((Card)obj);
				Card card = (Card)obj;
				stackDeck.addCard(card);
				topOfStack.setIcon(card.getCardImage());
			}
		}
	}

	
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
		/*Esta funcion es la que despliega la puntuacion y el ganador cuando el juego se termina, ya sea por que alguna de las manos este vacia
		o si el deck esta vacio. Primero suma los puntos usando una funcion que ya estaba en el file de Hand, esta funcion cuenta los puntos
		de cada carta.

		Luego decide quien gana. Si el jugador 1 tiene menos puntos que jugador 2, jugador 1 gana; jugador 2 gana si es este el que tiene la
		menor cantidad de puntos. Si es un empate, imprimira que es un empate.
		*/
		public void endGame () {
			int p1Points = 0;
			int p2Points = 0;
	
			//p1Points = countPoint(p1Hand, p1CardHold);
			//p2Points = countPoint(p2Hand, p2CardHold);

			p1Points = p1Hand.evaluateHand();
			p2Points = p2Hand.evaluateHand();
	
			//Si player1 tiene menos puntos que player2, player1 gana
			if(p1Points < p2Points){
				System.out.println("Points: " + p1Points + " to " + p2Points);
				System.out.println("Player 1 Wins!");
			}

			//Si player2 tiene menos puntos que player1, player2 gana
			if(p2Points < p1Points){
				System.out.println("Points: " + p2Points + " to " + p1Points);
				System.out.println("Player 2 Wins!");
			}

			//Si player1 y player2 tienen los mismos puntos, es un empate
			if(p1Points == p2Points){
				System.out.println("Points: " + p1Points + " to " + p2Points);
				System.out.println("Its a tie!");
			}
	
		}

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/*
		virtualPlayer() es la funcion que posee la logica para la computadora controle a los jugadores virtuales. Los jugadores virtuales
		no son muy inteligentes, simplemente hacen las acciones basicas de tomar una carta del stack y descartar una carta en el stack.
		Esto hasta que el deck sea acabado.

		El algoritmo para Deck es similar al de la funcion deckButton(), solo que aqui se automatisa el escojido de la carta.

		El algoritmo para layOnStack agarra siempre la primera carta en la mano en lugar de descartar una carta escojida por el usuario
		*/
		public void virtualPlayer (Hand pHand, JList<Card> pHandPile) {

			//Primer paso: Deck
			Card card = cardDeck.dealCard(); //Crea una variable card donde se guardara una carta del deck
			pHand.addCard(card); //Se incluye esta carta a la mano del jugador

			int inx = pHand.findCard(card);
			cardAddedToHand = pHand.getCard(inx); //Se incluye una copia de la carta incluida a la mano para luego imprimir la jugada

			//Segundo paso: LayOnStack
			Card tmp = pHand.getCard(0); //Variable tmp guarda una copia de la carta en la primera posision de la mano
			pHand.removeCard(0); //Remueve la carta de la mano
			stackDeck.addCard(tmp); //Incluye la copia de la mano que se descarto al stack
			topOfStack.setIcon(tmp.getCardImage()); //Muestra la foto que representa la carta en el GUI
		
			DiscartedCards[++DiscardCounter] = tmp; //Guarda la carta que se descarto para imprimir la jugada

		}

	public void actionPerformed(ActionEvent e)
	{

		Object src = e.getSource();

		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//Logica para accionar el boton 'Deck'
		if(p1Deck == src|| p2Deck == src){

			Card card = cardDeck.dealCard();

			if (card != null){
				if(src == p1Deck){
					if(p1Hand.getNumberOfCards() < 10){ //se asegura de que no allan mas de 10 cartas en la mano
						deckButton(p1Hand, card);//Llama a la funcion para el boton Deck
					}
				}
			}

			if (card != null){
				if(src == p2Deck){
					if(p2Hand.getNumberOfCards() < 10){//se asegura de que no allan mas de 10 cartas en la mano
						deckButton(p2Hand, card);//Llama a la funcion para el boton Deck
					}
				}
			}

			if(cardDeck.getSizeOfDeck() == 0)
				deckPile.setIcon(new ImageIcon(Card.directory + "blank.gif"));

		}

		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
					if(p1Hand.getNumberOfCards() < 10){//se asegura de que no allan mas de 10 cartas en la mano
						stackButton(p1Hand, card); //Llama a la funsion stackButton
					}
				}

				/*
				Toma una carta del stack y la pone en la mano de 'player2' solo si
				 en su mano hay menos de 9 cartas
				*/
				else{
					if(p2Hand.getNumberOfCards() < 10){//se asegura de que no allan mas de 10 cartas en la mano
						stackButton(p2Hand, card); //Llama a la funsion stackButton
					}
				}


			}

		}

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/*
		Estos if permiten al jugador poner sus cartas en sets.
		*/
		if(p1Lay == src){
			layButton(p1HandPile, p1Hand); //Llama a la funcion layButton
		}


		if(p2Lay == src){
			layButton(p2HandPile, p2Hand); //Llama a la funcion layButton
		}

		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		//Estos if permiten al jugador descartar sus cartas y ponerlas en el stack
		if(p1LayOnStack == src){
			layOnStackButton(p1HandPile, p1Hand); //Llama a funcion layOnStack
		}

		if(p2LayOnStack == src){
			layOnStackButton(p2HandPile, p2Hand);//Llama a funcion layOnStack
		}

		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		/*
		Logica para botones 'FinishTurn' que al usarlos imprimen la jugada del jugador
		*/
		if(p1FinishTurn == src){
			System.out.println("Player 1"); //Imprime que jugador realizo la jugada

			FinishTurn(); //Llama a funcion FinishTurn()

			//Luego de la partida imprime como de ve la mano de p1
			System.out.println("    Hand now: "+ p1Hand.toString());
			
			//Verifica si alguno de los jugadores ya no tiene cartas o si el deck ya se acabo
			if(p1Hand.getNumberOfCards() <= 0 || p2Hand.getNumberOfCards() <= 0 || cardDeck.isEmpty() == true){
				endGame(); //Llama a funcion endGme() si alguna de las condiciones son ciertas
			}

			//Si el usuario solo quiere un jugador virtual, esto es principalemte como se realizara. Cuando el usuario use el boton de FinishTurn
	        //Primer se imprimer su jugada y luego actua el jugador virtual.
			if(VirtualPlayers >= 1){
				virtualPlayer(p2Hand, p2HandPile); //Llama a la funcion virtualPlayer
				System.out.println("Player 2"); //Imprime que jugador realizo la jugada
				FinishTurn(); //Llama a la funcion FinishTurn()
				System.out.println("    Hand now: "+ p2Hand.toString());//Imprime como se ve la mano luego de la jugada

			}
		}

		if(p2FinishTurn == src){
			System.out.println("Player 2"); //Imprime que jugador realizo la jugada

			FinishTurn();

			//Luego de la partida imprime como de ve la mano de p2
			System.out.println("    Hand now: "+ p2Hand.toString());

			//Verifica si alguno de los jugadores ya no tiene cartas o si el deck ya se acabo
			if(p1Hand.getNumberOfCards() <= 0 || p2Hand.getNumberOfCards() <= 0 || cardDeck.isEmpty() == true){
				endGame(); //Llama a la funcion endGame() si alguna condiciopn s ehace cierta
			}
		}

	}

	void layCard(Card card)
	{
		char rank = card.getRank();
		char suit = card.getSuit();
		int suitIndex =  Card.getSuitIndex(suit);
		int rankIndex =  Card.getRankIndex(rank);
		setPanels[rankIndex].array[suitIndex].setText(card.toString());
		setPanels[rankIndex].array[suitIndex].setIcon(card.getCardImage());
	}

}

class HandPanel extends JPanel
{

	//añadi 'JButton FinishTurn'
	public HandPanel(String name,JList hand, JButton stack, JButton deck, JButton lay, JButton layOnStack, JButton FinishTurn)
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

		//añadi 'FinishTurn'
		FinishTurn.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(FinishTurn);

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
