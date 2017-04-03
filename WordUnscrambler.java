import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
	public HashSet<String>scrambledWordsAlpha = new HashSet<String>();
	public HashSet<String>scrambledWordsBeta = new HashSet<String>();
	public HashSet<String>scrambledWordsOmega = new HashSet<String>();
	public HashSet<String>scrambledWordsDelta = new HashSet<String>();
	public HashSet<String>scrambledWordsLamda = new HashSet<String>();
	public HashSet<String>scrambledWordsTheta = new HashSet<String>();
	public HashSet<String>scrambledWordsFinal = new HashSet<String>();

	public HashSet<String> likeWords = new HashSet<String>();
	public static void main(String[] args){
		new WordUnscrambler();
	}
	public WordUnscrambler(){
		Scanner scan = new Scanner(System.in);
		System.out.println("Input a word to descramble");
		seakScrambles(new StringBuilder(scan.nextLine()));
		scan.close();
	}

	public volatile double iterations;
	public void seakScrambles(StringBuilder word){
		scrambledWordsAlpha.clear();
		scrambledWordsBeta.clear();
		scrambledWordsOmega.clear();
		scrambledWordsLamda.clear();
		scrambledWordsTheta.clear();
		scrambledWordsFinal.clear();
		likeWords.clear();
		wordBank.clear();
		freshSet(word);
		iterations = getIterations(word.toString());
		System.out.println("Iterations of " + word + ": " + iterations);
		Thread trialA1 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramAlpha(word);
				}

			}

		});

		Thread trialA2 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramAlpha(word);
				}

			}

		});
		Thread trialA3 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramAlpha(word);
				}

			}

		});
		Thread trialA4 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramAlpha(word);
				}

			}

		});
		Thread trialA5 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramAlpha(word);
				}

			}

		});
		Thread trialA6 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramAlpha(word);
				}

			}

		});
		Thread trialA7 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramAlpha(word);
				}

			}

		});
		Thread trialA8= new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramAlpha(word);
				}

			}

		});
		Thread trialA9 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramAlpha(word);
				}

			}

		});
		Thread trialA10 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramAlpha(word);
				}

			}

		});
		Thread trialB1 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramBeta(word);
				}

			}

		});

		Thread trialB2 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramBeta(word);
				}

			}

		});

		Thread trialB3 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramBeta(word);
				}

			}

		});
		Thread trialB4 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramBeta(word);
				}

			}

		});
		Thread trialB5 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramBeta(word);
				}

			}

		});
		Thread trialB6 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramBeta(word);
				}

			}

		});
		Thread trialB7 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramBeta(word);
				}

			}

		});
		Thread trialB8 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramBeta(word);
				}

			}

		});
		Thread trialB9 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramBeta(word);
				}

			}

		});
		Thread trialB10 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramBeta(word);
				}

			}

		});
		Thread trialC1 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramOmega(word);
				}

			}

		});
		Thread trialC2 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramOmega(word);
				}

			}

		});
		Thread trialC3 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramOmega(word);
				}

			}

		});
		Thread trialC4 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramOmega(word);
				}

			}

		});
		Thread trialC5 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramOmega(word);
				}

			}

		});
		Thread trialC6 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramOmega(word);
				}

			}

		});
		Thread trialC7 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramOmega(word);
				}

			}

		});
		Thread trialC8 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramOmega(word);
				}

			}

		});
		Thread trialC9 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramOmega(word);
				}

			}

		});
		Thread trialC10 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramOmega(word);
				}

			}

		});
		Thread trialD1 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramDelta(word);
				}

			}

		});
		Thread trialD2 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramDelta(word);
				}

			}

		});
		Thread trialD3 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramDelta(word);
				}

			}

		});
		Thread trialD4 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramDelta(word);
				}

			}

		});
		Thread trialD5 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramDelta(word);
				}

			}

		});
		Thread trialD6 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramDelta(word);
				}

			}

		});
		Thread trialD7 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramDelta(word);
				}

			}

		});
		Thread trialD8 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramDelta(word);
				}

			}

		});
		Thread trialD9 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramDelta(word);
				}

			}

		});
		Thread trialD10 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramDelta(word);
				}

			}

		});
		Thread trialE1 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramLamda(word);
				}

			}

		});
		Thread trialE2 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramLamda(word);
				}

			}

		});
		Thread trialE3 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramLamda(word);
				}

			}

		});
		Thread trialE4 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramLamda(word);
				}

			}

		});
		Thread trialE5 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramLamda(word);
				}

			}

		});
		Thread trialE6 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramLamda(word);
				}

			}

		});
		Thread trialE7 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramLamda(word);
				}

			}

		});
		Thread trialE8 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramLamda(word);
				}

			}

		});
		Thread trialE9 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramLamda(word);
				}

			}

		});
		Thread trialE10 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramLamda(word);
					System.out.println(iterations);
				}

			}

		});
		Thread trialF1 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramLamda(word);
				}

			}

		});
		Thread trialF2 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramLamda(word);
				}

			}

		});
		Thread trialF3 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramLamda(word);
				}

			}

		});
		Thread trialF4 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramLamda(word);
				}

			}

		});
		Thread trialF5 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramLamda(word);
				}

			}

		});
		Thread trialF6 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramLamda(word);
				}

			}

		});
		Thread trialF7 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramLamda(word);
				}

			}

		});
		Thread trialF8 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramLamda(word);
				}

			}

		});
		Thread trialF9 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramLamda(word);
				}

			}

		});
		Thread trialF10 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramLamda(word);
					System.out.println(iterations);
				}

			}

		});
		Thread trialG1 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramTheta(word);
				}

			}

		});
		Thread trialG2 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramTheta(word);
				}

			}

		});
		Thread trialG3 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramTheta(word);
				}

			}

		});
		Thread trialG4 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramTheta(word);
				}

			}

		});
		Thread trialG5 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramTheta(word);
				}

			}

		});
		Thread trialG6 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramTheta(word);
				}

			}

		});
		Thread trialG7 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramTheta(word);
				}

			}

		});
		Thread trialG8 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramTheta(word);
				}

			}

		});
		Thread trialG9 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramTheta(word);
				}

			}

		});
		Thread trialG10 = new Thread(new Runnable(){

			@Override
			public void run() {
				while(iterations>0){
					scramTheta(word);
					System.out.println(iterations);
				}

			}

		});
		trialA1.start();
		trialA2.start();
		trialA3.start();
		trialA4.start();
		trialA5.start();
		trialA6.start();
		trialA7.start();
		trialA8.start();
		trialA9.start();
		trialA10.start();
		trialB1.start();
		trialB2.start();
		trialB3.start();
		trialB4.start();
		trialB5.start();
		trialB6.start();
		trialB7.start();
		trialB8.start();
		trialB9.start();
		trialB10.start();
		trialC1.start();
		trialC2.start();
		trialC3.start();
		trialC4.start();
		trialC5.start();
		trialC6.start();
		trialC7.start();
		trialC8.start();
		trialC9.start();
		trialC10.start();
		trialD1.start();
		trialD2.start();
		trialD3.start();
		trialD4.start();
		trialD5.start();
		trialD6.start();
		trialD7.start();
		trialD8.start();
		trialD9.start();
		trialD10.start();
		trialE1.start();
		trialE2.start();
		trialE3.start();
		trialE4.start();
		trialE5.start();
		trialE6.start();
		trialE7.start();
		trialE8.start();
		trialE9.start();
		trialE10.start();
		trialF1.start();
		trialF2.start();
		trialF3.start();
		trialF4.start();
		trialF5.start();
		trialF6.start();
		trialF7.start();
		trialF8.start();
		trialF9.start();
		trialF10.start();
		trialG1.start();
		trialG2.start();
		trialG3.start();
		trialG4.start();
		trialG5.start();
		trialG6.start();
		trialG7.start();
		trialG8.start();
		trialG9.start();
		trialG10.start();
		try {
			trialA1.join();
			trialA2.join();
			trialA3.join();
			trialA4.join();
			trialA5.join();
			trialA6.join();
			trialA7.join();
			trialA8.join();
			trialA9.join();
			trialA10.join();
			trialB1.join();
			trialB2.join();
			trialB3.join();
			trialB4.join();
			trialB5.join();
			trialB6.join();
			trialB7.join();
			trialB8.join();
			trialB9.join();
			trialB10.join();
			trialC1.join();
			trialC2.join();
			trialC3.join();
			trialC4.join();
			trialC5.join();
			trialC6.join();
			trialC7.join();
			trialC8.join();
			trialC9.join();
			trialC10.join();
			trialD1.join();
			trialD2.join();
			trialD3.join();
			trialD4.join();
			trialD5.join();
			trialD6.join();
			trialD7.join();
			trialD8.join();
			trialD9.join();
			trialD10.join();
			trialE1.join();
			trialE2.join();
			trialE3.join();
			trialE4.join();
			trialE5.join();
			trialE6.join();
			trialE7.join();
			trialE8.join();
			trialE9.join();
			trialE10.join();
			trialF1.join();
			trialF2.join();
			trialF3.join();
			trialF4.join();
			trialF5.join();
			trialF6.join();
			trialF7.join();
			trialF8.join();
			trialF9.join();
			trialF10.join();
			trialG1.join();
			trialG2.join();
			trialG3.join();
			trialG4.join();
			trialG5.join();
			trialG6.join();
			trialG7.join();
			trialG8.join();
			trialG9.join();
			trialG10.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(likeWords.toString());

		
	}
	public synchronized void scramAlpha(StringBuilder word){
		String s = scramble(word.toString());
		if(!scrambledWordsAlpha.contains(s)&&!scrambledWordsBeta.contains(s)&&!scrambledWordsOmega.contains(s)&&!scrambledWordsDelta.contains(s)){
			if(wordBank.contains(s)){
				likeWords.add(s);
			}
			scrambledWordsAlpha.add(s);
			iterations--;
		}
	}
	public synchronized void scramBeta(StringBuilder word){
		String s = scramble2(word.toString());
		if(!scrambledWordsAlpha.contains(s)&&!scrambledWordsBeta.contains(s)&&!scrambledWordsOmega.contains(s)&&!scrambledWordsDelta.contains(s)&&!scrambledWordsLamda.contains(s)&&!scrambledWordsFinal.contains(s)){
			if(wordBank.contains(s)){
				likeWords.add(s);
			}
			scrambledWordsBeta.add(s);
			iterations--;
		}
	}
	public synchronized void scramOmega(StringBuilder word){
		String s = scramble3(word.toString());
		if(!scrambledWordsAlpha.contains(s)&&!scrambledWordsBeta.contains(s)&&!scrambledWordsOmega.contains(s)&&!scrambledWordsDelta.contains(s)&&!scrambledWordsLamda.contains(s)&&!scrambledWordsFinal.contains(s)){
			if(wordBank.contains(s)){
				likeWords.add(s);
			}
			scrambledWordsOmega.add(s);
			iterations--;
		}
	}
	public synchronized void scramDelta(StringBuilder word){
		String s = scramble4(word.toString());
		if(!scrambledWordsAlpha.contains(s)&&!scrambledWordsBeta.contains(s)&&!scrambledWordsOmega.contains(s)&&!scrambledWordsDelta.contains(s)&&!scrambledWordsLamda.contains(s)&&!scrambledWordsFinal.contains(s)){
			if(wordBank.contains(s)){
				likeWords.add(s);
			}
			scrambledWordsDelta.add(s);
			iterations--;
		}
	}
	public synchronized void scramLamda(StringBuilder word){
		String s = scramble5(word.toString());
		if(!scrambledWordsAlpha.contains(s)&&!scrambledWordsBeta.contains(s)&&!scrambledWordsOmega.contains(s)&&!scrambledWordsDelta.contains(s)&&!scrambledWordsLamda.contains(s)&&!scrambledWordsFinal.contains(s)){
			if(wordBank.contains(s)){
				likeWords.add(s);
			}
			scrambledWordsLamda.add(s);
			iterations--;
		}
	}
	public synchronized void scramTheta(StringBuilder word){
		String s = scramble7(word.toString());
		if(!scrambledWordsAlpha.contains(s)&&!scrambledWordsBeta.contains(s)&&!scrambledWordsOmega.contains(s)&&!scrambledWordsDelta.contains(s)&&!scrambledWordsLamda.contains(s)&&!scrambledWordsFinal.contains(s)){
			if(wordBank.contains(s)){
				likeWords.add(s);
			}
			scrambledWordsTheta.add(s);
			iterations--;
		}
	}
	public synchronized void scramFinal(StringBuilder word){
		String s = scramble6(word.toString());
		if(!scrambledWordsAlpha.contains(s)&&!scrambledWordsBeta.contains(s)&&!scrambledWordsOmega.contains(s)&&!scrambledWordsDelta.contains(s)&&!scrambledWordsLamda.contains(s)&&!scrambledWordsFinal.contains(s)){
			if(wordBank.contains(s)){
				likeWords.add(s);
			}
			scrambledWordsFinal.add(s);
			iterations--;
		}
	}
	public double getIterations(String word){
		HashMap<String, Integer> tempStorage = new HashMap<String, Integer>();
		String[] temp = word.split("");
		int index = 1;
		for(String t : temp){
			if(!tempStorage.containsKey(t)){
				tempStorage.put(t, index);
			}else
				tempStorage.put(t, tempStorage.get(t)+1);
		}
		double divi = 1;
		for(int d : tempStorage.values()){
			divi*=factorial(d);
		}
		return (factorial(word.length()) / divi );

	}

	public synchronized String scramble(String str){
		List<String> scram2 = new ArrayList<String>(Arrays.asList(str.split("")));

		Collections.shuffle(scram2);
		StringBuilder scram3 = new StringBuilder();
		for(String s : scram2){
			scram3.append(s);
		}
		return scram3.toString();
	}
	public synchronized String scramble2(String str){
		List<String> scram2 = new ArrayList<String>(Arrays.asList(str.split("")));

		Collections.shuffle(scram2);
		StringBuilder scram3 = new StringBuilder();
		for(String s : scram2){
			scram3.append(s);
		}
		return scram3.toString();
	}
	public synchronized String scramble3(String str){
		List<String> scram2 = new ArrayList<String>(Arrays.asList(str.split("")));

		Collections.shuffle(scram2);
		StringBuilder scram3 = new StringBuilder();
		for(String s : scram2){
			scram3.append(s);
		}
		return scram3.toString();
	}
	public synchronized String scramble4(String str){
		List<String> scram2 = new ArrayList<String>(Arrays.asList(str.split("")));

		Collections.shuffle(scram2);
		StringBuilder scram3 = new StringBuilder();
		for(String s : scram2){
			scram3.append(s);
		}
		return scram3.toString();
	}
	public synchronized String scramble5(String str){
		List<String> scram2 = new ArrayList<String>(Arrays.asList(str.split("")));

		Collections.shuffle(scram2);
		StringBuilder scram3 = new StringBuilder();
		for(String s : scram2){
			scram3.append(s);
		}
		return scram3.toString();
	}
	public synchronized String scramble6(String str){
		List<String> scram2 = new ArrayList<String>(Arrays.asList(str.split("")));

		Collections.shuffle(scram2);
		StringBuilder scram3 = new StringBuilder();
		for(String s : scram2){
			scram3.append(s);
		}
		return scram3.toString();
	}
	public synchronized String scramble7(String str){
		List<String> scram2 = new ArrayList<String>(Arrays.asList(str.split("")));

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
	public void freshSet(StringBuilder word){
		wordBank = new HashSet<String>();
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(desktop));
			String line;
			while ((line = reader.readLine()) != null){
				if(containsAllChars(line,word))
					wordBank.add(line.toLowerCase());
			}
			reader.close();

		}
		catch (Exception e)
		{
			System.err.format("Done reading str/Words");
		}
	}
	public boolean containsAllChars(String line, StringBuilder word){
		for(int i = 0; i < word.length(); i++){
			if(!line.contains(word.substring(i,i+1))){
				return false;
			}
		}
		return true;
	}
}
