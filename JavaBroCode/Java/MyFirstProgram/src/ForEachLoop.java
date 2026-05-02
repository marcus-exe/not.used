import java.util.ArrayList;

public class ForEachLoop {
	public static void main(String[] args) {
		ArrayList<String> animals = new ArrayList<String>();
		animals.add("cat");
		animals.add("rat");
		animals.add("bird");
		//String[] animals = {"cat", "dog", "bird"};
		for(String i : animals) {
			System.out.println(i);
		}
	}
}
