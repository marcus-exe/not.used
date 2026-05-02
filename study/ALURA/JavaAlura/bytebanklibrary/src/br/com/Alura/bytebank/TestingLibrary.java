package br.com.Alura.bytebank;

import java.awt.AlphaComposite;

import br.com.Alura.bytebank.bank.model.Account;
import br.com.Alura.bytebank.bank.model.CheckingAccount;

public class TestingLibrary {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Account account = new CheckingAccount(123, 321);
		account.deposit(300.0);
		System.out.println(account.getBalance());
	}

}
