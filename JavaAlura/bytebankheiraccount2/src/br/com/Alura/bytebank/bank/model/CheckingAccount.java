package br.com.Alura.bytebank.bank.model;

public class CheckingAccount extends Account implements Taxable{

	public CheckingAccount(int agency, int number) {
		super(agency, number);
	}

	// this @Override is not actually necessary, but it helps to identify when you
	// truly want to override

	@Override
	public void withdraw(double value) throws InsuficientBalanceException {
		double valueToWithdraw = value + 0.2;
		super.withdraw(valueToWithdraw);

	}

	@Override
	public void deposit(double value) {

		super.balance += value;

	}

	@Override
	public double getValueTaxes() {
		return super.balance * 0.01;
	}
	
	@Override
	public String toString() {
	    return "Checking Account, " + super.toString();
	}

}
// using extends -> despite extending attributes and methods, you will not inherit the constructs
