package br.com.Alura.bytebank.bank.test;

import br.com.Alura.bytebank.bank.model.Account;
import br.com.Alura.bytebank.bank.model.CheckingAccount;
import br.com.Alura.bytebank.bank.model.SavingsAccount;

public class TestingReceiver {
	public static void main(String[] args) {
		AccountReceiver receiver = new AccountReceiver();
		
		Account checkingaccount = new CheckingAccount(22, 11);
		receiver.addAccount(checkingaccount);
		
		Account savingsaccount = new SavingsAccount(22, 22);
		receiver.addAccount(savingsaccount);
		
		System.out.println(receiver.getAmountElements());
		System.out.println(receiver.getReference(0));
		System.out.println(receiver.getReference(1));
		// I expected to use type cast since this Object is too generic
		
		
	}
	
	
	
	
	
}
