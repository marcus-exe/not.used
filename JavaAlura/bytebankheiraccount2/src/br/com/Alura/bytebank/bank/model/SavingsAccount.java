package br.com.Alura.bytebank.bank.model;

public class SavingsAccount extends Account {
	
	public SavingsAccount(int agency, int number) {
		super(agency, number);
	}
	
	@Override
	public void withdraw(double value) throws InsuficientBalanceException {
		double valueToWithdraw = value + 0.3;
		super.withdraw(valueToWithdraw);

	}

	@Override
	public void deposit(double value) {
		super.balance += value;
		
	}
	
	@Override
	public String toString() {
	    return "Savings Account, " + super.toString();
	}




}
