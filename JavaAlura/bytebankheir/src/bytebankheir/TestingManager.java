package bytebankheir;

public class TestingManager {
	public static void main(String[] args) {
		Manager manager = new Manager();
		manager.setName("João");
		manager.setCpf("222.222.222-02");
		manager.setWage(5000.00);
		
		System.out.println(manager.getName());
		System.out.println(manager.getWage());
		System.out.println(manager.getCpf());
		
		System.out.println(manager.getBonus());
	}
}
