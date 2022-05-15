// Hand.java - John K. Estell - 8 May 2003
// last modified: 23 Febraury 2004
// Implementation of a abstract hand of playing cards.
// Uses the Card class.  Requires subclass for specifying
// the specifics of what constitutes the evaluation of a hand
// for the game being implemented.

import java.util.*;

import javax.swing.DefaultListModel;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;


/**
 * Represents the basic functionality of a hand of cards.
 * Extensions of this class will provide the
 * definition of what constitutes a hand for that game and how hands are compared
 * to one another by overriding the <code>compareTo</code> method.
 * @author John K. Estell
 * @version 1.0
 */
public class Hand implements HandInterface {

   //protected java.util.List hand = new ArrayList();
   DefaultListModel <Card> hand = new DefaultListModel<Card>();

   int holdSize = 0;
   int maxHoldSize = 9;

  /**
   * Adds a card to this hand.
   * @param card card to be added to the current hand.
   */
   public void addCard( Card card ) {
      hand.addElement( card );
      this.sort(); //Organiza cada vez que se añade una nueva carta
   }
   
  /**
   * Searches for the first instance of a set (3 or 4 Cards of the same rank) in the hand.
   * @return  returns Card [] of Cards found in deck or <code>-null </code> if not found.
   */
   // public Card [] findSet( ){

	//     final int handSize = hand.size();
   //     if(handSize < 3){
   //        return null;
   //     }

   //     char rankType;
   //     Set set = new Set('0');
   //     Card [] card = new Card[handSize];

   //     for(int i = 0; i < handSize - 1; i++){

   //       rankType = hand.get(i).getRank();

   //       while(rankType == hand.get(i+1).getRank()){

   //          set.addCard(hand.get(i));
   //          card[i] = set.getCard(i);
   //       }
   //     }
   //     return card;
   // }

  /**
   * Obtains the card stored at the specified location in the hand.  Does not
   * remove the card from the hand.
   * @param index position of card to be accessed.
   * @return the card of interest, or the null reference if the index is out of
   * bounds.
   */
   public Card getCard( int index ) {
      return (Card) hand.get( index );
   }


  /**
   * Removes the specified card from the current hand.
   * @param card the card to be removed.
   * @return the card removed from the hand, or null if the card
   * was not present in the hand.
   */
   public Card removeCard( Card card ) {
      int index = hand.indexOf( card );
      if ( index < 0 )
         return null;
      else{
         hand.remove(index);
         this.sort();
         return card;
      }
   }


  /**
   * Removes the card at the specified index from the hand.
   * @param index poisition of the card to be removed.
   * @return the card removed from the hand, or the null reference if
   * the index is out of bounds.
   */
   public Card removeCard( int index ) {
      if(index < 0 ){
         return null;
      }
      else{
         hand.remove(index);
         this.sort();
         return (Card) hand.get(index);
      }
   }


  /**
   * Removes all the cards from the hand, leaving an empty hand.
   */
   public void discardHand() {
      hand.clear();
   }


  /**
   * The number of cards held in the hand.
   * @return number of cards currently held in the hand.
   */
   public int getNumberOfCards() {
      return hand.size();
   }


  /**
   * Sorts the card in the hand.
   * Sort is performed according to the order specified in the {@link Card} class.
   */
   public void sort() {

     sortSuit();

     final int size = hand.size();

     DefaultListModel<Card> tmp = new DefaultListModel<Card>();
     DefaultListModel<Card> set = new DefaultListModel<Card>();

     for(int i = 0; i < size; i++){
       
         int n = i;

        while(n < size && hand.get(n).getSuit() == hand.get(i).getSuit()){
           set.addElement(hand.get(n));
           n++;
        }

        i = n;
        i--;

        sortRank(set);

        for(int j = 0; j <set.size(); j++){
           tmp.addElement(set.get(j));
        }

        set.clear();
     }

     hand.clear();
     for (int i = 0; i < size; i++){
        hand.addElement( tmp.get(i) );
     }

   }

   //
   public void sortRank(DefaultListModel<Card> Ohand){

      final int maxHandSize = Ohand.getSize();
      
      for(int i = 0; i < maxHandSize; i++){

         int inx = i;

         for(int j = i + 1; j < maxHandSize; j++){
            if(Ohand.get(j).getRank() < Ohand.get(inx).getRank()){
               inx = j;
            }

            Card tmp = Ohand.get(inx);
            Ohand.setElementAt(Ohand.get(i), inx);
            Ohand.setElementAt(tmp, i);
         }
      }


   }

   public void sortSuit(){

      final int maxHandSize = hand.getSize();

      for(int i = 0; i < maxHandSize; i++){
         int inx = i;

         for(int j = i+1; j <maxHandSize; j++){
            if(hand.get(j).getSuit() < hand.get(inx).getSuit()){
               inx = j;
            }

            Card tmp = hand.get(inx);
            hand.setElementAt(hand.get(i), inx);
            hand.setElementAt(tmp, i);
         }
      }
   }


  /**
   * Checks to see if the hand is empty.
   * @return <code>true</code> is the hand is empty.
   */
   public boolean isEmpty() {
      return hand.isEmpty();
   }


  /**
   * Determines whether or not the hand contains the specified card.
   * @param card the card being searched for in the hand.
   * @return <code>true</code> if the card is present in the hand.
   */
   public boolean containsCard( Card card ) {
      if(hand.contains(card) == true){
         return true;
      } else {
         return false;
      }
   }


  /**
   * Searches for the first instance of the specified card in the hand.
   * @param card card being searched for.
   * @return position index of card if found, or <code>-1</code> if not found.
   */
   public int findCard( Card card ) {
      return hand.indexOf( card );
   }


  /**
   *  Compares two hands.
   *  @param otherHandObject the hand being compared.
   *  @return < 0 if this hand is less than the other hand, 0 if the two hands are
   *  the same, or > 0 if this hand is greater then the other hand.
   */
   public int compareTo( Object otherHandObject ) {
      Hand otherHand = (Hand) otherHandObject;
      return evaluateHand() - otherHand.evaluateHand();
   }


    /**
     * Evaluates a hand according to the rules of the dumb card game.
     * Each card is worth its displayed pip value (ace = 1, two = 2, etc.)
     * in points with face cards worth ten points.  The value of a hand
     * is equal to the summation of the points of all the cards held in
     * the hand.
     */
    public int evaluateHand() {
        int value = 0;
      
        for ( int i = 0; i < getNumberOfCards(); i++ ) {
            Card c = getCard( i );
            int cardValue = Card.getRankIndex(c.getRank()) - Card.getRankIndex('a') + 1;
            if ( cardValue > 10 )
               cardValue = 10;
            value += cardValue;
        }
		
        return value;
    }


   /**
    * Returns a description of the hand.
    * @return a list of cards held in the hand.
    */
    public String toString() {
       this.sort();
        return hand.toString();
    }


   /**
    * Replaces the specified card with another card.  Only the first
    * instance of the targeted card is replaced.  No action occurs if
    * the targeted card is not present in the hand.
    * @return <code>true</code> if the replacement occurs.
    */
    public boolean replaceCard( Card oldCard, Card replacementCard ) {
        int location = findCard( oldCard );
        if ( location < 0 )
           return false;
        hand.set( location, replacementCard );
        return true;
    }

    public DefaultListModel<Card> getHand(){

      // DefaultListModel <Card> tmp = new DefaultListModel<Card>();

      //  for(int i = 0; i < hand.size(); i++){
      //     tmp.add(i, getCard(i));
      //  }

      //  return tmp;
      return this.hand;
    }

}