
public class CheckingAccount extends Account implements Taxable{

	public CheckingAccount(int agency, int number) {
		super(agency, number);
	}

	// this @Override is not actually necessary, but it helps to identify when you
	// truly want to override

	@Override
	public boolean withdraw(double value) {
		double valueToWithdraw = value + 0.2;
		return super.withdraw(valueToWithdraw);

	}

	@Override
	public void deposit(double value) {

		super.balance += value;

	}

	@Override
	public double getValueTaxes() {
		return super.balance * 0.01;
	}

}
// using extends -> despite extending attributes and methods, you will not inherit the constructs
