package bytebankheir;

public class TestingSystem {
	public static void main(String[] args) {
		Manager manager = new Manager();
		manager.setPassword(222);
		
		Admin administrator = new Admin();
		administrator.setPassword(333);
		
		InternalSystem internalsystem = new InternalSystem();
		internalsystem.autentication(manager);
		internalsystem.autentication(administrator);
	}
}
