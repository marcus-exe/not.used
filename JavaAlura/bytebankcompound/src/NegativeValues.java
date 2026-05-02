
public class NegativeValues {
	public static void main(String[] args) {
		Account newAccount = new Account();
		newAccount.deposit(100);
		newAccount.withdraw(200);
		System.out.println(newAccount.getBalance());
		
		// prohibited stuff - so I've encapsulated my attribute
		//newAccount.balance -= 101;
		//System.out.println(newAccount.balance);
	}
}
