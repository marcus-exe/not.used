package bytebankheir;

public abstract class Employee {
	//abstract means that it isn't a real class
	//it is like an idea, a group
	//tomatoes, oranges, berries are in the abstract class fruits
	
	
	private String name;
	private String cpf;
	private double wage;
	
	// method without a body
	public abstract double getBonus();
	// I just want this method in order to organize the other classes
	// Moreover, I deleted it because I won't use that bonus system anymore
	// Always when this type of Method is used, the sons are obliged to use it.
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public double getWage() {
		return wage;
	}

	public void setWage(double wage) {
		this.wage = wage;
	}

}
