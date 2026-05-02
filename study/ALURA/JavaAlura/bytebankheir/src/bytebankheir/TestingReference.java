package bytebankheir;

public class TestingReference {
	public static void main(String[] args) {
		
		Manager manager = new Manager();
		manager.setName("Marcos");
		System.out.println(manager.getName());
		manager.setWage(5000.0);
		
		Programmer programmer = new Programmer();
		programmer.setName("Jon");
		System.out.println(programmer.getName());
		programmer.setWage(3000.0);
		
		BonusControl control = new BonusControl();
		control.register(manager);
		control.register(programmer);
		System.out.println(control.getSum());
		
	}
}
