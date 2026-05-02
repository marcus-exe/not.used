
public class TestGetndSet {
	public static void main(String[] args) {
		Account account = new Account();
		account.setNumber(1337);//setting a number
		System.out.println(account.getNumber());
		Client marcus = new Client();
		// account.barrer = marcus; -> does not work: the barrer is private
		marcus.setName("Marcus Sena");
		
		account.setBarrer(marcus);// reference 
		
		System.out.println(account.getBarrer().getName());
		//as a result of reference of private attributes, we need to set 2 methods
		account.getBarrer().setProfession("programador");
		System.out.println(account.getBarrer().getProfession());
	}
}
