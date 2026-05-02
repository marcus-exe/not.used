import java.util.Scanner;

public class LogicalOperators {
	public static void main(String[] args) {
		
		//temperature
		int temp = 10;
		if(temp>30) {
			System.out.println("It is hot outside!");
		}
		else if(temp>20 && temp<30) {
			System.out.println("It is warm outside");
		}
		else {
			System.out.println("It is cold outside");
		}
		//game
		Scanner scanner = new Scanner(System.in);
		System.out.println("You are playing a game! Press q or Q to quit");
		String response = scanner.next();
		if (!response.equals("q")&&!response.equals("Q")) {
			System.out.println("You are still playing the game *pew pew*");
		}
		else {
			System.out.println("You quit the game");
			
		}
		//
		
		
		
		
	}
}
