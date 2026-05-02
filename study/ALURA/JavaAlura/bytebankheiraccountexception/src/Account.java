public abstract class Account {

	protected double balance;
	private int agency;
	private int number;
	private Client barrer;
	private static int total;

	public Account() {

	}

	public Account(int agency, int number) {
		this.agency = agency;
		this.number = number;
		total++;
	}

	public abstract void deposit(double value);

	public void withdraw(double value) throws InsuficientBalanceException{
		if (this.balance < value) {
			throw new InsuficientBalanceException("Balance: " + this.balance + ", Valor: " + value);
		}
		this.balance -= value;
		
	}

	public void transfer(double value, Account destination) throws InsuficientBalanceException{
		this.withdraw(value);
		//it will only pass throw the line above if withdraw works in the first place
		destination.deposit(value);
	}

	public double getBalance() {
		return this.balance;
	}

	public int getNumber() {
		return this.number;
	}

	public void setNumber(int number) {
		if (number > 0) {
			System.out.println("Cannot use Negative Values");
		}

		this.number = number;
	}

	public int getAgency() {
		return this.agency;
	}

	public void setAgency(int agency) {
		if (agency < 0) {
			System.out.println("Cannot use Negative Values");
			return;
		}
		this.agency = agency;

	}

	public void setBarrer(Client barrer) {
		this.barrer = barrer;
	}

	// link
	public Client getBarrer() {
		return barrer;
	}

	public static int getTotal() {
		return Account.total;
	}

}
