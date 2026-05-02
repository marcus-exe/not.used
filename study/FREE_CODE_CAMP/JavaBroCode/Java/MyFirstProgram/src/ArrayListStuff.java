import java.util.ArrayList;

public class ArrayListStuff {
	public static void main(String[] args) {
		ArrayList<String> food = new ArrayList<String>();
		food.add("Pizza");
		food.add("Hamburger");
		food.add("hotdog");
		
		food.set(0, "Sushi");
		food.set(2, "Pizza");
		food.remove(2);
		for(int i = 0; i < food.size(); i++) {
			System.out.println(food.get(i));			
		}
		food.clear();
		for(int i = 0; i < food.size(); i++) {
			System.out.println(food.get(i));			
		}
		System.out.println("End");
		
	}
}
