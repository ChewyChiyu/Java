import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
/*
 * 
 * 
 * 
 * 
 * PLEASE NOTE THAT NOT ALL WORDS WORK AS DICTONARY IS A DERP
 * 
 * 
 * 
 * 
 */
public class WordUnscrambler{
	File desktop = new File( System.getProperty("user.home") + "/Desktop/Words");
	public HashSet<String> wordBank = new HashSet<String>();
	public HashSet<String> scrambledWords = new HashSet<String>();
	public HashSet<String> likeWords = new HashSet<String>();
	public static void main(String[] args){
		new WordUnscrambler();
	}
	public WordUnscrambler(){
		freshSet();
		Scanner scan = new Scanner(System.in);

		while(true){
			System.out.println("Input a word to descramble");
			seakScrambles(new StringBuilder(scan.nextLine()));
			System.out.println("Continue? y/n");
			if(scan.nextLine().equals("n"))
				break;
		}
		scan.close();
	}


	public void seakScrambles(StringBuilder word){
		HashMap<String, Integer> tempStorage = new HashMap<String, Integer>();
		scrambledWords.clear();
		likeWords.clear();
		String[] temp = word.toString().split("");
		int repeat = 1;
		for(String t : temp){
			if(!tempStorage.containsKey(t)){
				tempStorage.put(t, repeat);
			}else
				tempStorage.put(t, tempStorage.get(t)+1);
		}
		double divi = 1;
		for(int d : tempStorage.values()){
			divi*=factorial(d);
		}
		for(int index = 2; index <= word.length(); index++){
			HashSet<String> trial = new HashSet<String>();
			HashSet<String> trialReal = new HashSet<String>();
			
			int combinations = (int) ((factorial(word.length()) / factorial(word.length()-index))/divi);
			System.out.println("Combinations " + combinations);
			while(combinations > 0 ){
				StringBuilder temp2 = new StringBuilder(word);
				StringBuilder b2 = new StringBuilder();
				for(int i = 0; i < index; i++){
					int i2 = (int)(Math.random()*temp2.length());
					b2.append(temp2.charAt(i2));
					temp2.deleteCharAt(i2);	
				}
				if(!trial.contains(b2.toString())){
					if(wordBank.contains(b2.toString()))
						trialReal.add(b2.toString());
					trial.add(b2.toString());
					combinations--;
				}


			}
			System.out.println(trialReal.toString());
			System.out.println("Trial Length " + trialReal.size());
		}


	}
	public String scramble(String str){
		String[] scram = str.toLowerCase().trim().split("");
		ArrayList<String> scram2 = new ArrayList<String>();
		for(String s : scram)
			scram2.add(s);
		Collections.shuffle(scram2);
		StringBuilder scram3 = new StringBuilder();
		for(String s : scram2){
			scram3.append(s);
		}
		return scram3.toString();
	}
	
	public double factorial(int f){
		double d = 1;
		while(f > 1){
			d*=f--;
		}
		return d;
	}
	public void freshSet(){
		wordBank = new HashSet<String>();
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(desktop));
			String line;
			while ((line = reader.readLine()) != null){
				wordBank.add(line.toLowerCase());
			}
			reader.close();

		}
		catch (Exception e)
		{
			System.err.format("Done reading str/Words");
		}
	}

}
