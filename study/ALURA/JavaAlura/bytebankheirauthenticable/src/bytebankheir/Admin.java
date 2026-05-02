	package bytebankheir;

public class Admin extends Employee implements Authenticable{
	
	private UsefulAuthentication usefulauthentication;
	
	public Admin() {
		this.usefulauthentication = new UsefulAuthentication();
	}
	
	@Override
	public double getBonus() {
		return 500.00;
	}

	@Override
	public void setPassword(int password) {
		this.usefulauthentication.setPassword(password);
		
	}

	@Override
	public boolean autentication(int password) {
		return this.usefulauthentication.autentication(password);
	}

}
