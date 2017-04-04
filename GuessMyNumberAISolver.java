import java.util.Scanner;

public class GuessMyNumberAISolver {
	public static void main(String[] args){
		new GuessMyNumberAISolver();
	}
	public GuessMyNumberAISolver(){
		showComputerBanter();
		faceOff();
	}
	public void showComputerBanter(){
		System.out.println("Guess a Number between 0 and 1000 inclusive and Remember it!");
		System.out.println("I am quite the number guessing master");
	}
	public void faceOff(){
		int tries = 0;
		int lowestPossible = 0;
		int highestPossible = 1000;
		int number = 500;
		Scanner player = new Scanner(System.in);
		while(true){
		System.out.println("Is your number " + number);
		System.out.println("yes or no");
		boolean correct = (player.nextLine().equals("yes"))?true:false;
		if(correct){
			System.out.println("Got emmmm! " + tries + " as well! ");
			break;
		}else{
			tries++;
			System.out.println("Was I low or high");
			boolean tooLow = (player.nextLine().equals("low"))?true:false;
			if(tooLow){
				System.out.println("hmmmmmm so higher huh....");
				lowestPossible = number;
				number = lowestPossible + (int)(highestPossible-lowestPossible)/2;
				
			}else{
				System.out.println("hmmmmm so lower huh....");
				highestPossible = number;
				number = lowestPossible + (int)(highestPossible-lowestPossible)/2;
			}
		}
		}
		player.close();
	}
}
