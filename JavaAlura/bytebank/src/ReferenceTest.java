public class ReferenceTest {
	public static void main(String[] args) {
		Account firstAccount = new Account();
		firstAccount.balance = 300;
		System.out.println("Balance First Account: " + firstAccount.balance);
		Account secondAccount = firstAccount;
		// both those variables indicte to the same exact object
		// there are no clones
		System.out.println("Balance Second Account: " + secondAccount.balance);
		secondAccount.balance += 100;
		System.out.println("Balance Second Account: " + secondAccount.balance);
		System.out.println("Balance First Account: " + firstAccount.balance);
		// everything that is altered relates to the same object
		
		if (firstAccount == secondAccount) {
			System.out.println("Same Account");
			}
		else {
			System.out.println("Diferent Account");
		}
	}
}