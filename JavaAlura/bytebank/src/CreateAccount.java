
public class CreateAccount {
	public static void main(String[] args) {

		// first account
		Account firstAccount = new Account();
		firstAccount.balance = 200;
		// System.out.println(firstAccount.balance);
		firstAccount.balance += 100;
		// System.out.println(firstAccount.balance);

		// second account
		Account secondAccount = new Account();
		secondAccount.balance = 50;

		System.out.println("first account has " + firstAccount.balance);
		System.out.println("second account has " + secondAccount.balance);
		
		System.out.println(firstAccount.agency);
		System.out.println(firstAccount.number);
		// basically, the default value is zero, but
		// you can declare it previously in the document where 
		// you've instanced your class
		// more: you can overwrite the values in your code anywhere you want
		
		if (firstAccount == secondAccount) {
			System.out.println("Same Account");
			}
		else {
			System.out.println("Diferent Account");
		}
				
	}
}
