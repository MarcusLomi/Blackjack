
public class Blackjack {
	
	public static Deck deck= new Deck();
	public static Player dealer=new Player();
	
	public static void main(String[] args) {
		boolean game=true;
		int counter=1;
		int playersOut=0;
		Player p1=new Player();			// Initializing all players regardless of the playercount
		Player p2=new Player();			// Round Counter set to 1
		Player p3=new Player();			// Booleans which control whether or not a player is still in the game
		Player p4=new Player();			// after starting are initialized. 
		Player p5=new Player();			
		Player p6=new Player();		
									
		Player[] playerArr={p1,p2,p3,p4,p5,p6};
		
		
	// Introduction and Player name entry;	
		System.out.println("\t\tWelcome to Blackjack. \nStarting Balance is: $500.00");
		System.out.println("\nHow many players are there? Max: 6");
		int playerCount=IO.readInt();
		while(playerCount<1||playerCount>6){
			System.out.println("Please enter a number form 1 to 6");
			playerCount=IO.readInt();
		}
		for(int i=0;i<playerCount;i++){
			System.out.println("Enter player "+(i+1)+"'s name:");
			String name=IO.readString();
			playerArr[i].setName(name);
		}
		
	
	while(game){// keeps the game running
		System.out.println("\n\t\t\tRound:"+counter);
		counter++;
		//shuffle loop
		for(int i=0;i<200;i++){
			deck.shuffle();
		}
		//This for loop Deals cards	
		//Card ace= new Card(0,8);
		for(int i =0;i<playerCount;i++){
			if(playerArr[i]!=null){
				playerArr[i].dealCards(deck.deal(),deck.deal() );
			}
		}
		//Card ace= new Card(0,1);
		dealer.dealCards(deck.deal(), deck.deal());
	
		//If statement block handles turns 	
		for(int i =0;i<playerCount;i++){
			if(playerArr[i]!=null){
				System.out.println("\n\tDealer's Face up Card:"+dealer.getCard().printCard());
				playerArr[i].turn();
			}
		}
	
		//checks to see if all players busted. If so, the dealer doesn't play. 
		if(p1.getValue()+p2.getValue()+p3.getValue()+p4.getValue()+p5.getValue()+p6.getValue()!=0){
			dealer.dealer(dealer);
			deck=new Deck();
		}
			
		//checks for losers and winners and gives winnings accordingly. 
		String winners="";
		String push="";
		for(int i =0;i<playerCount;i++){
			if(playerArr[i]!=null){
				if(playerArr[i].hasSplit==true){
					winners+=playerArr[i].splitWin(dealer)+" ";
					break;
				}
				if(dealer.getValue()<playerArr[i].getValue()){
					winners+=playerArr[i].getName()+" ";
					playerArr[i].win();
				}
			}
		}
			
		//Checks for ties and gives money back accordingly
		for(int i =0;i<playerCount;i++){
			if(playerArr[i]!=null){
				if(dealer.getValue()==playerArr[i].getValue()&&dealer.getValue()!=0&&playerArr[i].hasSplit!=true){
					push+=playerArr[i].getName()+" ";
					playerArr[i].tie();
				}
			}
		}
			
		if(!push.equals("")){
			System.out.println("Tie:"+push);
		}
		if(winners.equals("")&&push.equals("")){
			winners+="Dealer";
		}
		
		//Checks for players whose bankRoll is <5.00
		for(int i =0;i<playerCount;i++){
			if(playerArr[i]!=null){
				if(playerArr[i].getBankRoll()<5){
					System.out.println("\n Sorry "+playerArr[i].getName()+" you lose. Bankroll is <5.00");
					playerArr[i].pause();
					playerArr[i]=null;
					playersOut++;
					
				}
			}
		}
		
		System.out.println("Round Winner(s):"+winners);	
		//System.out.println("playercount:"+playerCount+" playersOut:"+playersOut);
		//This if statement checks if the number of players removed from the game=playercount. It ends the game. 
		if(playersOut==playerCount){
			game=false;
			break;
		}
		
		System.out.println("\t\tWould the player(s) like to continue playing? <y/n>" );
		String reply=IO.readString();
		if(reply.equalsIgnoreCase("y")){
			
			}
			else if(reply.equalsIgnoreCase("n")){
				game=false;
			}
			else{
				boolean ask=true;
				while(ask){// keeps asking player for correct input
					
					System.out.println("Please enter y or n");
					reply=IO.readString();
					if(reply.equalsIgnoreCase("y")){
						ask=false;
						
					}
					if(reply.equalsIgnoreCase("n")){
						ask=false;
						game=false;
					}
				}
			}// Done checking if player wants to hit
			
			deck=new Deck(); //refreshes the deck every round
		
		}// end While(game)
		
		System.out.println("\t\t\tGame over. Thank you for playing.");
		if(playerCount>=1){
			System.out.println(p1.getName()+"'s final Bankroll is:"+p1.getBankRoll());
		}
		if(playerCount>=2){
			System.out.println(p2.getName()+"'s final Bankroll is:"+p2.getBankRoll());
		}
		if(playerCount>=3){
			System.out.println(p3.getName()+"'s final Bankroll is:"+p3.getBankRoll());
		}
		if(playerCount>=4){
			System.out.println(p4.getName()+"'s final Bankroll is:"+p4.getBankRoll());
		}
		if(playerCount>=5){
			System.out.println(p5.getName()+"'s final Bankroll is:"+p5.getBankRoll());
		}
		if(playerCount>=6){
			System.out.println(p6.getName()+"'s final Bankroll is:"+p6.getBankRoll());
		}
	}// End Main
	
	
	
}// End Class Blackjack
/*
 * MILESTONE 2: 
 * Change Log:
 * 11/13/15 
 * removed String hand from player class
 * added isEmpty check to deal(). It now reconstructs the deck inside the method if isEmpty() returns true
 * Made sure player.turn() works in Main()
 * added dealCards(Card c1, Card c2) to handle first to cards dealt in the the round
 * Added print method for cards.
 * Added boolean isLoser
 * made Deck static on the Blackjack class (challenge)
 * Added dealer, modified player method. 
 * Added win method to add money to the winning player's bankRoll
 * 
 * 11/14/15
 * fixed bug where dealer kept on hitting
 * fixed bug where dealer would play despite the fact that all players busted
 * fixed bug that printed out p1 name for all winners. 
 * added round counter
 * moved bankRoll to display before placing bets. 
 * code now fully accounts for aces
 * removed boolean isLoser
 * added statements to remove when players when their bankRoll gets below $5.00.
 * added statements to remove players from the game. 
 * added pause() method for good pacing 
 * 
 * 11/20/15
 * added playerArr[] for holding all players
 * removed if statements. Optimized code with for loops
 * 
 * MILESTONE 3:
 * 
 * 11/25/15
 * Added doubling down option with .doubleDown() method in Player class
 * 
 * 11/27/15 
 * Made dealer public static so that I can check the value of his face up card in .turn() method for players.
 * Added canDouble boolean
 * Added new text options and input checks in .turn() to allow/disallow doubling down and splits
 * Added Insurance with .insure() method in player class. 
 * Added .split() and .splitWin() to account for splits
 * 
 * 11/30/15
 * Added hint system with correct printouts
 * 
 * Notes on Functionality
 * Initialize all players(regardless of how many are playing), dealer and deck. Put players in an array
 * 	1. Asks how many players
 * 		a. uses setName() accordingly
 * 	2. While Loop for game handles rounds
 * 		a. deck refreshed and shuffled 200 times
 * 		b. Cards dealt to all currently playing players and dealer first
 * 		c. For loops controlled by int playerCount decide who plays their turn. Turns executed by player.turn() method. 
 * 		   Dealer uses player.dealer() method
 * 	3. Checks for winners/ties and prints their names
 * 	4. repeat 
 * 
 */

