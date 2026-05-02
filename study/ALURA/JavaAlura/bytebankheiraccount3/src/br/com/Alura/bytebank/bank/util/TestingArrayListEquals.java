package br.com.Alura.bytebank.bank.util;

import java.util.ArrayList;
import br.com.Alura.bytebank.bank.model.Account;
import br.com.Alura.bytebank.bank.model.CheckingAccount;
import br.com.Alura.bytebank.bank.model.SavingsAccount;

public class TestingArrayListEquals {

	public static void main(String[] args) {
		
		ArrayList<Account> list = new ArrayList<Account>();

		Account checkingaccount = new CheckingAccount(22, 11);
		list.add(checkingaccount);

		Account savingsaccount = new SavingsAccount(22, 11);
		list.add(savingsaccount);

		System.out.println("Does it already exists? " + list.contains(savingsaccount));
		//contains is based in the equals method
		System.out.println("Is equal? " + checkingaccount.equals(savingsaccount));
		
		for(Account account : list) {
			System.out.println(account);
		}
		
		

	}

}
