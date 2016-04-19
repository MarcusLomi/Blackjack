
// This class represents the deck of deckArray from which deckArray are dealt to players.
public class Deck
{
	public static int cardCount=0;
	 //Deck is a card array	
	public Card[] deckArray= new Card[52];

	// This constructor builds a deck of 52 cards.
	public Deck()
	{	int i=0;
		while(i<52){
			for(int s=0;s<4;s++){
				for(int f=1;f<14;f++){
					if(i==52){
						break;
					}
					else{
					deckArray[i]=new Card(s,f);
					i++;
					}
				}
			}
		}
	}

	
	// This method takes the top card off the deck and returns it.
	public Card deal()
	{
		if(this.isEmpty()){
			int i=0;
			while(i<52){
				for(int s=0;s<4;s++){
					for(int f=1;f<14;f++){
						if(i==52){
							break;
						}
						else{
						deckArray[i]=new Card(s,f);
						i++;
						}
					}
				}
			}// Refreshes cards
		}
		if(deckArray[0].getFace()>9){
			cardCount--;
		}
		else if(deckArray[0].getFace()>6){
			
		}
		else{
			cardCount++;
		}
		Card draw=deckArray[0];
		deckArray[0]=null;
		for(int i=0;i<deckArray.length-1;i++){
			deckArray[i]=deckArray[i+1];
		}
		deckArray[deckArray.length-1]=null;
		return draw;
		
	}// end deal()
	
	

	
	// this method returns true if there are no more deckArray to deal, false otherwise
	public boolean isEmpty()
	{   
		boolean isEmpty=true;
		
		for(int i=0;i<deckArray.length;i++){
			if(deckArray[i]!=null){
				return false;
			}
		}
		
		return isEmpty;
	}// end isEmpty()
	
	//this method puts the deck in some random order
	public void shuffle(){
		
		for(int i=0;i<101;i++){
			int a=(int)Math.round(Math.random()*51);
			int b=(int)Math.round(Math.random()*51);
			Card temp=deckArray[a];
			deckArray[a]=deckArray[b];
			deckArray[b]=temp;
			
		}
	}// end shuffle()
	
	public void printDeck(){
		for(int i=0;i<deckArray.length;i++){
			if(deckArray[i]==null){
				break;
			}
			System.out.println(i+" "+deckArray[i].getSuit()+ " " + deckArray[i].getFace());
		}
		
	}// end print deck.

}
