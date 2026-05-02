package br.com.Alura.bytebank.bank.util;

import java.util.ArrayList;
import br.com.Alura.bytebank.bank.model.Account;
import br.com.Alura.bytebank.bank.model.CheckingAccount;
import br.com.Alura.bytebank.bank.model.SavingsAccount;

public class TestingArrayList {

	public static void main(String[] args) {
		
		//Gererics
		ArrayList<Account> list = new ArrayList<Account>();
		//here i just limited the method used by the library to use only Account
		//it is needed because this class is as broad as objects

		Account checkingaccount = new CheckingAccount(22, 11);
		list.add(checkingaccount);

		Account savingsaccount = new SavingsAccount(22, 22);
		list.add(savingsaccount);

		System.out.println(list.size());

		Account ref = (Account) list.get(0);
		System.out.println(ref);
		// System.out.println(list.get(0)); why it works without type cast?

		list.remove(0);
		System.out.println("Removed the first object, now I will print the rest of them");

		System.out.println(list.size());

		Account newcheckingaccount = new CheckingAccount(33, 44);
		list.add(newcheckingaccount);

		Account newsavingsaccount = new SavingsAccount(55, 66);
		list.add(newsavingsaccount);
		
		
		// I just exchanged the "Object" for "Account" Class
		// I did it in order to fit in with the <Generics> statement
		
		for(int i=0; i<list.size();i++) {
			Account oRef = list.get(i);
			System.out.println(oRef);
			//System.out.println(list.get(i));
		}
		System.out.println("-----Enhanced Form------");
		
		for(Account oRef : list) {
			System.out.println(oRef);
			//this is a compilers trick
			//it means: to each object of the list {do}
		}
		
		

	}

}
