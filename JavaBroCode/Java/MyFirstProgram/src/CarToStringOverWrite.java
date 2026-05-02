
public class CarToStringOverWrite {
	
	public static void main(String[] args) {
		CarToStringOverWrite car = new CarToStringOverWrite();
		System.out.println(car.toString());
		System.out.println(car);
	}
	
	
	String make = "Ford";
	String model = "Mustang";
	String color = "red";
	int year = 2021;
	
	public String toString() {
		return make + "\n" + model + "\n" + color + "\n" + year;
	}
	
	
	
}
