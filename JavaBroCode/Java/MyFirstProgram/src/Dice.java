import java.util.Random;

public class Dice {
	
	public static void main(String[] args) {
		Dice dice = new Dice();
	}
	
	Random random = new Random();
	int number = 0;

	Dice() {
		roll();
	}

	void roll() {
		number = random.nextInt(6) + 1;
		System.out.println(number);
	}
}
