package br.com.Alura.bytebank.bank.test;

import br.com.Alura.bytebank.bank.model.CheckingAccount;
import br.com.Alura.bytebank.bank.model.SavingsAccount;
import br.com.Alura.bytebank.bank.model.Account;

public class TestingArrayReference {

	public static void main(String[] args) {

		Account[] accounts = new Account[5];
		// You can be more generic when creating account
		
		
		CheckingAccount[] checkingaccount = new CheckingAccount[5];
		SavingsAccount[] savingsaccount = new SavingsAccount[5];

		for (int i = 0; i < checkingaccount.length; i++) {
			int f;
			f = i + 2;
			checkingaccount[i] = new CheckingAccount(f * f, f * f * f);
//			System.out.println("CheckingAccount " + (i + 1) + " Agency: " + checkingaccount[i].getAgency()
//					+ ", Number: " + checkingaccount[i].getNumber());
		}

		for (int i = 0; i < savingsaccount.length; i++) {
			int f;
			f = i + 2;
			savingsaccount[i] = new SavingsAccount(f * f, f * f * f);
//			System.out.println("SavingsAccounts " + (i + 1) + " Agency: " + savingsaccount[i].getAgency() + ", Number: "
//					+ savingsaccount[i].getNumber());
			
		CheckingAccount ca1	= new CheckingAccount(22, 11);
		accounts[0] = ca1;
		
		SavingsAccount sa1 = new SavingsAccount(22, 11);
		accounts[1] = sa1;
		
		SavingsAccount ref = (SavingsAccount) accounts[1];//type cast
		
		
		
		
		
			

		}

	}
}