import java.util.Scanner;
public class RomanToDecimal {
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		System.out.println("Give a Roman Numeral Please");
		String roman = scan.next();
		RomanToDecimal activity = new RomanToDecimal();
		System.out.println(activity.toDecimal(roman));
		scan.close();
	}
	public int toDecimal(String s){
		int number = 0;
		String[] array = s.toUpperCase().trim().split("");
		for(String a : array){
			if(!a.equals("I")&&!a.equals("V")&&!a.equals("X")&&!a.equals("L")&&!a.equals("C")&&!a.equals("D")&&!a.equals("M")){
				System.out.println("Invalid Roman");
				return 0;
			}
		}
		for(int index = 0; index < array.length-1; index++){
			if(array[index].equals("I")){
				if(array[index+1].equals("X")){
					number+=9;
					array[index] = "-";
					array[index + 1] = "-";
				}
				if(array[index+1].equals("V")){
					number+=4;
					array[index] = "-";
					array[index + 1] = "-";
				}
			}
			if(array[index].equals("X")){
				if(array[index+1].equals("L")){
					number+=40;
					array[index] = "-";
					array[index + 1] = "-";
				}
				if(array[index+1].equals("C")){
					number+=90;
					array[index] = "-";
					array[index + 1] = "-";
				}
			}
			if(array[index].equals("C")){
				if(array[index+1].equals("D")){
					number+=400;
					array[index] = "-";
					array[index + 1] = "-";
				}
				if(array[index+1].equals("M")){
					number+=900;
					array[index] = "-";
					array[index + 1] = "-";
				}
			}

		}
		for(String num : array){
			switch(num){
			case "I" : number++; break;
			case "V" : number+=5; break;
			case "X" : number+=10; break; 
			case "L" : number+=50; break;
			case "C" : number+=100; break;
			case "D" : number+=500; break;
			case "M" : number+=1000; break;
			default : number+=0; break;
			}
		}
		return number;
	}
}
