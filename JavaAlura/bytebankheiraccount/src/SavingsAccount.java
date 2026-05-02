
public class SavingsAccount extends Account {
	
	public SavingsAccount(int agency, int number) {
		super(agency, number);
	}
	
	@Override
	public boolean withdraw(double value) {
		double valueToWithdraw = value + 0.3;
		return super.withdraw(valueToWithdraw);

	}

	@Override
	public void deposit(double value) {
		super.balance += value;
		
	}




}
