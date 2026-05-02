package br.com.Alura.bytebank.bank.test;

import br.com.Alura.bytebank.bank.model.CheckingAccount;
import br.com.Alura.bytebank.bank.model.SavingsAccount;

public class TestingObject {
	public static void main(String[] args) {
		Object checkingaccount = new CheckingAccount(222, 333);
		Object savingsaccount = new SavingsAccount(222, 333);
		System.out.println(checkingaccount);
		System.out.println(savingsaccount);
	}
}
