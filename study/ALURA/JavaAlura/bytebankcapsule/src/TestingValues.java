
public class TestingValues {
	public static void main(String[] args) {
		Account account = new Account(1337, 24226);
		//account.setAgency(-50);
		//account.setNumber(-330);
		Account account2 = new Account(1338, 22226);
		Account account3 = new Account(1355, 20026);
		System.out.println(Account.getTotal());
		// I can only use this by calling the class directly 
		// Because I've declared the method static
		
	}
}
