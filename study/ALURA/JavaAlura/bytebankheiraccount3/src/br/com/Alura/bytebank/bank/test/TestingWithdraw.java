package br.com.Alura.bytebank.bank.test;
//above we have out FQN 

import br.com.Alura.bytebank.bank.model.Account;
import br.com.Alura.bytebank.bank.model.CheckingAccount;
import br.com.Alura.bytebank.bank.model.InsuficientBalanceException;


public class TestingWithdraw {
	public static void main(String[] args) throws InsuficientBalanceException {
		//this variable that includes the package name is called Full Qualified Name(FQN)
		Account newaccount = new CheckingAccount(123, 444);
		newaccount.deposit(200.0);
		newaccount.withdraw(201.0);
		System.out.println(newaccount.getBalance());
	}
}
