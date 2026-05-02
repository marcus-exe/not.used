import java.util.ArrayList;

public class ArrayList2DStuff {
	public static void main(String[] args) {
		
		ArrayList<ArrayList<String>> groceryList = new ArrayList();
				
		ArrayList<String> bakeryList = new ArrayList();
		bakeryList.add("Pasta");		
		bakeryList.add("Garlic bread");
		bakeryList.add("Donuts");
		
		ArrayList<String> produceList = new ArrayList();
		produceList.add("Tomatoes");		
		produceList.add("Zucchini");
		produceList.add("Pepers");
		
		ArrayList<String> drinksList = new ArrayList();
		drinksList.add("Soda");		
		drinksList.add("Coffe");

		groceryList.add(bakeryList);
		groceryList.add(produceList);
		groceryList.add(drinksList);
		
		System.out.println(groceryList);
		System.out.println(groceryList.get(0).get(0));
		
	}
}
