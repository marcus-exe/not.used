package br.com.Alura.bytebank.bank.test;

import br.com.Alura.bytebank.bank.model.Account;

public class AccountReceiver {

	private Object[] references;
	private int freeposition;
	
	public AccountReceiver() {
		this.references = new Account[10];
		this.freeposition = 0;
	}
	
	public void addAccount(Account ref) {
		this.references[this.freeposition] = ref;
		this.freeposition++;
	}
	
	public int getAmountElements() {
		return this.freeposition;
	}
	
	public Object getReference(int pos) {
		return this.references[pos];
	}
}
