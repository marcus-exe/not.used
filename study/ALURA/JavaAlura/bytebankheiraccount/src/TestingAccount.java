
public class TestingAccount {
	public static void main(String[] args) {
		CheckingAccount checkingaccount = new CheckingAccount(111, 111);
		checkingaccount.deposit(100.0);
		
		SavingsAccount savingsaccount = new SavingsAccount(222, 222);
		savingsaccount.deposit(200.0);
		
		checkingaccount.transfer(10.0, savingsaccount);
		
		System.out.println("CheckingAccount: "+ checkingaccount.getBalance());
		System.out.println("SavingAccount "+ savingsaccount.getBalance());
		
		
	}
}
