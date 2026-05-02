
public class TestingWithdraw {
	public static void main(String[] args) {
		Account newaccount = new CheckingAccount(123, 444);
		newaccount.deposit(200.0);
		try {
		newaccount.withdraw(201.0);
		} catch(InsuficientBalanceException ex) {
			System.out.println("Ex: " + ex.getMessage());
		}
		System.out.println(newaccount.getBalance());
	}
}
