
public class Player {
	
	private String name;
	private double bet;	// How much the player bets every round. 
	private double bankRoll; //How much the player has in the bank. 
	private Card c1; // Saves the first card the player receives. 
	private Card c2; // Saves the second card the player receives. 
	private int value; 
	private int splitVal1;
	private int splitVal2;
	private boolean hasAce;
	boolean hasSplit;
	
	public Player(){
		this.name="";
		this.bankRoll=500.00;
		this.value=0;
		this.splitVal1=0;
		this.splitVal2=0;
		this.bet=0;
		this.hasAce=false;
		this.hasSplit=false;	
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public boolean bet(double bet){
		if(bet>bankRoll||bet<1.00){
			return false;
		}
		else{
			this.bankRoll-=(bet);
			return true;
		}
	}
	public double getBankRoll(){
		return this.bankRoll;
	}
	
	//used to print player's name and their bankRoll
	public void printInfo(){
		System.out.println(this.name+"'s BankRoll is:$"+this.bankRoll);
	}
	
	//uses deck.deal in the main method and takes those cards in as parameters to set c1 and c2
	public void dealCards(Card c1, Card c2){
		this.c1=c1;
		this.c2=c2;
	}
	
	//returns card value
	public int getValue(){
		return this.value;
	}
	
	public void insure(String a){
		if(a.equalsIgnoreCase("y")){
			double insurance=this.bet/2.0;
			this.bankRoll-=(insurance);
			if(Blackjack.dealer.c2.getFace()>10){
				this.bankRoll+=(insurance*2.0);
			}
			else{	
			}
		}
		else if(a.equalsIgnoreCase("n")){
			return;
		}
		else{
			boolean ask=true;
			while(ask){// keeps asking player for correct input
				System.out.println("Please enter y or n");
				a=IO.readString();
				if(a.equalsIgnoreCase("y")){
					ask=false;
					if(Blackjack.dealer.c2.getFace()>10){
						this.bankRoll+=((this.bet/2.0)*2.0);
					}
					else{
					}
				}
				if(a.equalsIgnoreCase("n")){
					return;
				}
			}
		}
	}// end 

	
	public void win(){
		this.bankRoll+=(this.bet*2);
	}
	public void tie(){
		this.bankRoll+=this.bet;
	}
	
	public void doubleDown(){
		Card current;
		this.bet(bet); 								//re-bets initial bet amount which was saved earlier
		this.bet+=bet;								//sets the betting amount to double of what it was	
		current=Blackjack.deck.deal();				//deals a new card, only one. 
		if(current.getFace()==1){					//then uses the same algorithm for calculating values that is used in .turn()
			this.hasAce=true;
			this.value+=10;
		}
		System.out.println(current.printCard());
		this.value+=current.getValue();
		if(this.value>21){
			if(hasAce){
				this.value-=10;
				hasAce=false;
			}
			else{
				System.out.println("You Bust!");
				this.value=0;
			}
		}
				
	}
		
	//Handles dealer tasks.
	public boolean dealer(Player a){
		Deck.cardCount=0;
		this.value=0;
		this.hasAce=false;
		boolean hit=true;// controls hit loop inside method.
		Card current;
		
		//Take in initial bets
		System.out.println("\nIt's The Dealer's turn.");
		System.out.println(c1.printCard());
		System.out.println(c2.printCard());
		this.value+=c1.getValue()+c2.getValue();
		if(c1.getFace()==1||c2.getFace()==1){
			this.hasAce=true;
			value+=10;
		}
		//Total card value checker before hitting
		if(this.value==21){
			System.out.println("21!");
			hit=false;
			return false;
		}
		else if(this.value>=17){
			System.out.println("The dealer stands \n");
			hit=false;
		}
		this.pause();
															
		//Hit loop repeats process required in hitting 
		while(hit){
			if(this.value>21){
				if(hasAce){
					this.value-=10;
					this.hasAce=false;// so that it doesn't keep subtracting every turn if the player has an ace
					if(this.value>=17){// put this if loop in here to make sure that if the ace would go over, but is turned into a value of 1, and value=17, it doesn't hit again.
						System.out.println("Dealer Stands");
						hit=false;
						return false;
					}
				}
				else{
					System.out.println("Dealer Bust!\n");
					this.value=0;
					hit=false;
					return false;
				}
			}
			else if(this.value==21){
				System.out.println("21!");
				return false;
			}
			else if(this.value>=17){
				System.out.println("Dealer Stands");
				hit=false;
				return false;
			}
			else if(this.value<17){
				System.out.println("Dealer hits");
			}
			
			current=Blackjack.deck.deal();
			this.value+=current.getValue();
			if(current.getFace()==1){
				this.hasAce=true;
				this.value+=10;// resets for new aces 				
			}			
			System.out.println(current.printCard());
			this.pause();
		}// end while(hit)
		
		return false;
	}// end turn
	
	// handles player turns
	public boolean turn(){
		this.value=0;
		boolean canDouble=true; //used for checking if the player can double down or not.
		boolean hit=true; 		//controls hit loop inside method.
		this.hasAce=false;		
		this.hasSplit=false;
		Card current;		
	//Take in initial bets
		System.out.println("\nIt's "+this.getName()+"'s turn. Place your bet. $1.00 minimum");
		System.out.println("Your balance is:"+this.bankRoll);
		this.bet=IO.readDouble();
		if(this.bet(bet)){	
			
		}
		else{
			while(this.bet(bet)==false){
				System.out.println("Invalid bet");
				this.bet=IO.readDouble();
			}
		}	
	// Checks value of first cards
		this.value+=c1.getValue()+c2.getValue();
		if(c1.getFace()==1||c2.getFace()==1){
			this.hasAce=true;
			this.value+=10;
		}
		System.out.println(c1.printCard());
		System.out.println(c2.printCard());
		//System.out.println("\t\t Card count is:"+Deck.cardCount);
	
		if(this.value==21){
			System.out.println("21!");
			return false;
		}
	
	//Check if they can Insure
		if(Blackjack.dealer.c1.getFace()==1&&this.bankRoll-((this.bet)/2)>0){
			System.out.println("Would you like Insuracnce:$"+(this.bet/2)+"?");
			String reply=IO.readString();
			this.insure(reply);
		}
	//Check if they can double or split	
		if(this.bankRoll-this.bet<0){
			canDouble=false;
		}
	
	//Asks user if they want to hit stand or double
		boolean repeat=false;
	do{
		if(canDouble){
			if(canDouble&&c1.getValue()==c2.getValue()){
				System.out.println("Hit, Stand, Double, Split, Hint? <y/n/d/s/h>");
			}
			else{
				System.out.println("Hit, Stand, Double, Hint? <y/n/d/h>");
			}
		}
		else{
			System.out.println("Hit, Stand or Hint? <y/n/h>");
		}
		
		String reply=IO.readString();
		if(reply.equalsIgnoreCase("d")&&canDouble){
			this.doubleDown();
			return false;
		}//end if they want to double down. 
		else if(reply.equalsIgnoreCase("h")){
			System.out.println(this.hint(this.value));
			repeat=true;
		}
		else if(reply.equalsIgnoreCase("s")&&c1.getValue()==c2.getValue()){
			this.split();
			this.hasSplit=true;
			return false;
		}
		else if(reply.equalsIgnoreCase("n")){
			return false;
		}
		else if(reply.equalsIgnoreCase("y")){
			repeat=false;
		}// done asking for initial inputs
		else{
			boolean ask=true;
			while(ask){// keeps asking player for correct input
				System.out.println("y to hit, n to stand, d to double, or s to split (if available)");
				reply=IO.readString();
				if(reply.equalsIgnoreCase("d")){
					ask=false;
					repeat=false;
					this.doubleDown();
					return false;
				}
				if(reply.equalsIgnoreCase("s")&&c1.getValue()==c2.getValue()){
					ask=false;
					repeat=false;
					this.split();
					this.hasSplit=true;
					return false;
				}
				if(reply.equalsIgnoreCase("n")){
					return false;
				}
				if(reply.equalsIgnoreCase("y")){
					ask=false;
					repeat=false;
				}
				if(reply.equalsIgnoreCase("h")){
					System.out.println(this.hint(this.value));
					repeat=true;
					ask=false;
				}
			}
		}// Done asking to correct bad inputs
		
	}while(repeat==true);//done asking for input
	
	//Hit loop repeats process required in standard hitting 
		while(hit){
		//Deals new card if player hits	
			current=Blackjack.deck.deal();
			if(current.getFace()==1){
				this.hasAce=true;
				this.value+=10;
			}
			System.out.println(current.printCard());
			
			//System.out.println("\t\t Card count is:"+Deck.cardCount);
			this.value+=current.getValue();
			
			if(this.value>21){
				if(hasAce){
					this.value-=10;
					hasAce=false;// so that it doesn't keep subtracting every turn if the player has an ace
				}
				else{
					System.out.println("You Bust!");
					this.value=0;
					hit=false;
					return false;
				}
			}
			else if(this.value==21){
				System.out.println("21!");
				this.pause();
				return false;
			}
	
		//Asks if the player wants to hit again
			repeat=false;
			do{	
				System.out.println("Hit or hint? <y/n/h>" );
			    String reply=IO.readString();
				if(reply.equalsIgnoreCase("y")){
					repeat=false;
				}
				else if(reply.equalsIgnoreCase("h")){
					System.out.println(this.hint(this.value));
					repeat=true;
				}
				else if(reply.equalsIgnoreCase("n")){
					return false;
				}
				else{
					boolean ask=true;
					while(ask){// keeps asking player for correct input
						
						System.out.println("Please enter y, n, or h");
						reply=IO.readString();
						if(reply.equalsIgnoreCase("y")){
							ask=false;
							repeat=false;
						}
						if(reply.equalsIgnoreCase("h")){
							System.out.println(this.hint(this.value));
							repeat=true;
						}
						if(reply.equalsIgnoreCase("n")){
							ask=false;
							hit=false;
							return false;
						}
					}
				}//done asking to hit or stand
			}while(repeat==true);
		}// end hit loop
		
		return false;
	}// end turn()
	
	public void split(){
		this.hasSplit=true;
		boolean hand1=true;
		boolean hand2=false;
		Card current;
		this.value=1;
		this.splitVal1=c1.getValue();
		System.out.println(this.name+"'s 1st hand:");
		System.out.println(c1.printCard());
		this.bet(bet);
		
		
		//Handles the first split hand
		while(hand1){
			//Deals new card if player hits	
				current=Blackjack.deck.deal();
				if(current.getFace()==1){
					this.hasAce=true;
					this.splitVal1+=10;
				}
				System.out.println(current.printCard());
				this.splitVal1+=current.getValue();
				if(this.splitVal1>21){
					if(hasAce){
						this.splitVal1-=10;
						hasAce=false;// so that it doesn't keep subtracting every turn if the player has an ace
					}
					else{
						System.out.println("You Bust!");
						this.splitVal1=0;
						hand1=false;
						hand2=true;
						break;
					}
				}
				else if(this.splitVal1==21){
					System.out.println("21!");
					this.pause();
					hand1=false;
					hand2=true;
					break;
				}
				// if the player has an ace they only get one more card
				if(c1.getFace()==1){
					hand1=false;
					hand2=true;
					break;
				}
			//Asks if the player wants to hit again
				boolean repeat=false;
				do{	
					System.out.println("Hit or hint? <y/n/h>" );
				    String reply=IO.readString();
					if(reply.equalsIgnoreCase("y")){
						repeat=false;
					}
					else if(reply.equalsIgnoreCase("h")){
						System.out.println(this.hint(splitVal1));
						repeat=true;
					}
					else if(reply.equalsIgnoreCase("n")){
						hand2=true;
						hand1=false;
						repeat=false;
						
					}
					else{
						boolean ask=true;
						while(ask){// keeps asking player for correct input
							
							System.out.println("Please enter y, n, or h");
							reply=IO.readString();
							if(reply.equalsIgnoreCase("y")){
								ask=false;
								repeat=false;
							}
							if(reply.equalsIgnoreCase("h")){
								System.out.println(this.hint(splitVal1));
								repeat=true;
							}
							if(reply.equalsIgnoreCase("n")){
								ask=false;
								hand1=false;
								hand2=true;
								return;
							}
						}
					}//done asking to hit or stand
				}while(repeat==true);
				
			}// end hand1
	
		this.splitVal2=c2.getValue();
		System.out.println("\n"+this.name+"'s 2nd hand:");
		System.out.println(c2.printCard());
		while(hand2){
			//Deals new card if player hits	
				current=Blackjack.deck.deal();
				if(current.getFace()==1){
					this.hasAce=true;
					this.splitVal2+=10;
				}
				System.out.println(current.printCard());
				this.splitVal2+=current.getValue();
				if(this.splitVal2>21){
					if(hasAce){
						this.splitVal2-=10;
						hasAce=false;// so that it doesn't keep subtracting every turn if the player has an ace
					}
					else{
						System.out.println("You Bust!");
						this.splitVal2=0;
						return;
					}
				}
				else if(this.splitVal2==21){
					System.out.println("21!");
					this.pause();
					return;
				}
				// if the player has an ace they only get one more card
				if(c1.getFace()==1){
					return;
				}
			//Asks if the player wants to hit again
				boolean repeat=false;
				do{	
					System.out.println("Hit or hint? <y/n/h>" );
				    String reply=IO.readString();
					if(reply.equalsIgnoreCase("y")){
						repeat=false;
					}
					else if(reply.equalsIgnoreCase("h")){
						System.out.println(this.hint(splitVal2));
						repeat=true;
					}
					else if(reply.equalsIgnoreCase("n")){
						return;
					}
					else{
						boolean ask=true;
						while(ask){// keeps asking player for correct input
							
							System.out.println("Please enter y, n, or h");
							reply=IO.readString();
							if(reply.equalsIgnoreCase("y")){
								ask=false;
								repeat=false;
							}
							if(reply.equalsIgnoreCase("h")){
								System.out.println(this.hint(splitVal2));
								repeat=true;
							}
							if(reply.equalsIgnoreCase("n")){
								ask=false;
								return;
							}
						}
					}//done asking to hit or stand
				}while(repeat==true);
				
			}// end hand2
		
	}//end split method
	
	//calculates all the winnings. If a player's particular split hand wins then it returns a string to add to the 
	//"winners" string printout in the blackjack main method. if a hand pushes it'll add that to push. 
	public String splitWin(Player a){
		String answer="";
		if(this.splitVal1==a.value){
			this.tie();
		}
		if(this.splitVal2==a.value){
			this.tie();
		}
		if(this.splitVal1>a.value){
			this.win();
			answer+=this.name+"'s Hand 1 ";
		}
		if(this.splitVal2>a.value){
			this.win();
			answer+=this.name+"'s Hand 2 ";
		}
		
		return answer;
	}
	
	// for good pacing during the game
	public void pause(){
		try {
		    Thread.sleep(2000);                 
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}
	
	// Used to act as a "face up" card the dealer has that all players can see. 
	public Card getCard(){
		return this.c1;
	}
	
	//hint system for giving the players advice. Works in conjunction with int cardCount in the Blackjack class
	public String hint(int value){
		String answer="";
		//Accurately recommending when to split based on popular Blackjack strategy. 
		//i.e. always split aces and eights, never split 10's or 5's
		
		if(this.hasSplit==true){
			if(this.c1.getValue()==this.c2.getValue()&&this.c2.getValue()<9&&
				        this.c2.getValue()>2&&this.c2.getValue()!=5
					   &&this.hasSplit==false||
					   this.c1.getValue()==this.c2.getValue()&&this.c2.getFace()==1
					   &&this.hasSplit==false){
						answer+="\tHint: Odds in favor of Splitting";
					}
					//recommending to double down when the player's first two have a value of 11.
					else if(this.c1.getValue()+this.c2.getValue()==11&&value==11){
						answer+="\tHint: Odds in favor of Doubling down";
					}
					else if(value>=17){
						answer+="\tHint: Odds in favor of standing";
					}
					else if(value<=16){
						if(value>=12){
							if(Deck.cardCount>2){							
								answer+="\tHint: Odds in favor of Standing";	 
							}											
							else if(Deck.cardCount<=2){//chagned from >=0
								answer+="\tHint: Odds in favor of Hitting";
							}
							else if(Deck.cardCount<=-3){
								answer+="\tHint: Odds in favor of hitting.";
							}
						}
						else{
							answer+="\tHint: Odds in favor of hitting";
						}
						
					}
				
				
				return answer;
		}
		else{
			if(this.c1.getValue()==this.c2.getValue()&&
			   this.c2.getValue()<9&&this.c2.getValue()>2&&this.c2.getValue()!=5
			   &&this.hasSplit==false||
			   this.c1.getValue()==this.c2.getValue()&&this.c2.getFace()==1
			   &&this.hasSplit==false){
				answer+="\tHint: Odds in favor of Splitting";
			}
			//recommending to double down when the player's first two have a value of 11.
			else if(this.c1.getValue()+this.c2.getValue()==11&&this.value==11){
				answer+="\tHint: Odds in favor of Doubling down";
			}
			else if(this.value>=17){
				answer+="\tHint: Odds in favor of standing";
			}
			else if(this.value<=16){
				if(this.value>=12){
					if(Deck.cardCount>2){							//Cardcount is used to help with hitting recmomendations. If the card count is <0, chances are the
						answer+="\tHint: Odds in favor of Standing";	//next card will be a low card i.e. <8, so then it would be beneficial to hit because the risk of busting is low. 
					}												// When the cardcount is high, it means many low cards have been played so the next cards values are likely to be >10
					else if(Deck.cardCount<=2){//chagned from >=0
						answer+="\tHint: Odds in favor of Hitting";
					}
					else if(Deck.cardCount<=-3){
						answer+="\tHint: Odds in favor of hitting.";
					}
				}
				else{
					answer+="\tHint: Odds in favor of hitting";
				}
				
			}
		
		
		return answer;
		}
	}	
}
