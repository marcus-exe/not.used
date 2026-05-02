package bytebankheir;

// manager is a heir from employee
public class Manager extends EmployeeAuthenticable {


	public double getBonus() {
		// return this.wage; -> I could have written this way, but it's not so clear as
		// shown bellow
		return super.getWage();
		// if use this.getBonus, congratulations, you've got yourself a loop without
		// results!
		//
	}
}
