package chiyugames.Samples;
import java.util.Scanner;
public class RockPaperScissors {
	private static Scanner scan = new Scanner(System.in);
	private static int playerWins = 0;
	private static int computerWins = 0;
	public static void main(String[] args){
		RockPaperScissors Game = new RockPaperScissors();
		int gamesPlayed = 10;
		while(true){
			String a = Game.player();
			System.out.println(Game.compare(a, Game.computer()));
			System.out.println("ScoreBoard: Player " + playerWins + " Computer " + computerWins + ", " + --gamesPlayed + " Games Left");
			if(gamesPlayed==0){
				if(playerWins==computerWins){
					System.out.println("Overall Tie");
					break;
				}
				else if(playerWins > computerWins){
					System.out.println("Overall the Player has Won");
					break;
				}
				else{
					System.out.println("Overall the Computer has Won");
					break;
				}
			}
		}
	}
	public String player(){
		System.out.println("Rock Paper or Scissors");
		String choice = scan.next();

		if(choice.equals("Rock")||choice.equals("Paper")||choice.equals("Scissors")){
			return choice;
		}
		System.out.println("Sorry invalid Choice");

		return player();
	}
	public String computer(){
		int temp = (int)(Math.random()*3);
		switch(temp){
		case(0): return "Rock"; 
		case(1): return "Paper";
		case(2): return "Scissors";
		}
		return null;


	}

	public String compare(String a, String b){
		if(a.equals(b)){ return "It is a Tie";}
		else if(a.equals("Rock")&&b.equals("Scissors")){
			playerWins++;
			return "Player wins " + a + " beats " + b; 
		}else if(a.equals("Paper")&&b.equals("Rock")){
			playerWins++;
			return "Player wins " + a + " beats " + b; 
		}else if(a.equals("Scissors")&&b.equals("Paper")){
			playerWins++;
			return "Player wins " + a + " beats " + b; 
		}else{
			computerWins++;
			return "Computer wins " + b + " beats " + a; 
		}
	}

}
