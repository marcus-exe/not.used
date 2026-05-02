// Class
public class Account {
	// Parameters
	double balance;
	int agency;
	int number;
	String barrer;

	// Methods
	public void deposit(double value) {
		this.balance += value;
	}
	// void means: do not expect any output from me
	// basically, this. means that we are reference the object
	// that summoned the method up

	public boolean withdraw(double value) {
		if (this.balance >= value) {
			this.balance -= value;
			return true;
		} else {
			return false;
		}
	}
	// It's important to note that when we declare the kind
	// of variable we expect for the output, we must make it
	// so that this variable is present at the end of the method

	public boolean transfer(double value, Account destination) {
		if (this.balance >= value) {
			this.balance -= value;
			destination.deposit(value);
			return true;
		}
		return false;
	}
}
