package bytebankheir;

public class TestingEmployee {
	public static void main(String[] args) {
		Programmer marcus = new Programmer();
		marcus.setName("Marcus Sena");
		marcus.setCpf("000.000.000-00");
		marcus.setWage(25000.80);
		System.out.println(marcus.getName());
		System.out.println(marcus.getBonus());
	}
}
