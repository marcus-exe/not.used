package bytebankheir;

public class InternalSystem {
	
	private int password = 222;
	
	public void autentication(Authenticable employeeauthenticable) {
		
		if(employeeauthenticable.autentication(this.password)) {
			System.out.println("You may enter");
		}else {
			System.out.println("You shall not Pass");
		}		
	}
	
	
	
	
}
