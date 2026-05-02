
public class TestingBank {
	public static void main(String[] args) {
		// Create Client Object and Populate
		Client marcus = new Client();
		marcus.name = "Marcus Sena";
		marcus.cpf = "000.000.000-00";
		marcus.profession = "Programmer";
		// Create Client Account
		Account marcusAccount = new Account();
		marcusAccount.deposit(1000);
		// Make the reference from barrer to client - We call it Composition
		marcusAccount.barrer = marcus;
		System.out.println(marcusAccount.barrer.name);
	}

}
