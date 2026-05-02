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

	public boolean withdraw(double value) {
		if (this.balance >= value) {
			this.balance -= value;
			return true;
		}
		return false;
	}

	public boolean transfer(double value, Account destination) {
		if (this.balance >= value) {
			this.withdraw(value);
			destination.deposit(value);
			return true;
		}
		return false;
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
