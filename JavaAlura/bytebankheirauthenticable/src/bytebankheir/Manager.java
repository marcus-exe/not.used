package bytebankheir;

public class Manager extends Employee implements Authenticable {

	private UsefulAuthentication usefulauthentication;
	
	public Manager() {
		this.usefulauthentication = new UsefulAuthentication();
	}
	

	@Override
	public void setPassword(int password) {
		this.usefulauthentication.setPassword(password);;
		
	}

	@Override
	public boolean autentication(int password) {
		return this.usefulauthentication.autentication(password);
	}
	
	public double getBonus() {

		return super.getWage();
	}

	}


