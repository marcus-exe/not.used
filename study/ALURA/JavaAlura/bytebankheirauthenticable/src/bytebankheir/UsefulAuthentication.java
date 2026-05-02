package bytebankheir;

public class UsefulAuthentication {
	
	private int password;
	
	public void setPassword(int password) {
		this.password = password;
	}
	
	public boolean autentication(int password) {
		if (this.password == password) {
			return true;
		}
		return false;
	}
	
}
