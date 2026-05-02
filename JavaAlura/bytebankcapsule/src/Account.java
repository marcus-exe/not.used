public class Account {

	private double balance;
	private int agency;
	private int number;
	private Client barrer;
	private static int total;
	// static makes that the total attribute is from the class, not instances
	// almost like a global variable, but global variables are only in static typed
	// languages

	// Constructors
	public Account() {
		// this constructor needs no information
		// this is default, but as soon as we've
		// declared our first constructor with conditions
		// this one stops existing, so you have to declare it
		// if you want, duuh
		total++;
		// Account.total++; ->variable of the class
	}

	public Account(int agency, int number) {
		this.agency = agency;
		this.number = number;
		// System.out.println("Your account is being created: "+ this.agency);
		total++;
		// System.out.println("The total number of accounts is "+ total);
	}

	public void deposit(double value) {
		this.balance += value;
	}

	public boolean withdraw(double value) {
		if (this.balance >= value) {
			this.balance -= value;
			return true;
		}
		return false;
	}

	public boolean transfer(double value, Account destination) {
		if (this.balance >= value) {
			this.balance -= value;
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
		// despite having the same name, those variables are different
		// I know it sounds weird, but this is common practice
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
