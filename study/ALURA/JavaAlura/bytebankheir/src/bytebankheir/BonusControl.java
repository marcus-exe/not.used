package bytebankheir;

public class BonusControl {
	
	private double sum;

	public void register(Employee employee) {
		this.sum += employee.getBonus();
	}
	//thing is, since Manager is a son of Employee, we can use Employee methods for Managers also
	//this is a perfect example of polimorphism
	
	
	public double getSum() {
		return sum;
	} 
	//shows the total of bonus, but you have to declare it
	//and count the amount of bonus you want to use
}
