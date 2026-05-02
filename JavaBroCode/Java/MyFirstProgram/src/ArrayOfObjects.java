//we're referencing Food
public class ArrayOfObjects {
	public static void main(String[] args) {
		
		//Food[] refrigerator = new Food[3];
		Food food1 = new Food("Pizza");
		Food food2 = new Food("Pineapple");
		Food food3 = new Food("Rice"); 
		
		Food[] refrigerator = {food1, food2, food3};

		System.out.println(refrigerator[0].name);
		System.out.println(refrigerator[1].name);
		System.out.println(refrigerator[2].name);
		
	}
}
