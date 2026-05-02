package br.com.Alura.bytebank.bank.model;

public class InsuficientBalanceException extends Exception{
	
	public InsuficientBalanceException(String message) {
		super(message);
	}

}
