
public class StaticMethods {
	
	
	public static void main(String[] args) {
		System.out.println(StaticMethods.numberOfFriends);
	}
	
	String name;
	static int numberOfFriends;
	
	StaticMethods(String name){
		this.name = name;
		numberOfFriends++;
	}
	
	StaticMethods friend1 = new StaticMethods("SpongeBob");
	StaticMethods friend2 = new StaticMethods("Patrick");
}

