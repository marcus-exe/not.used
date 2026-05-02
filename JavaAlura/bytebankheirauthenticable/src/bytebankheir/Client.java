package bytebankheir;

public class Client implements Authenticable {

	private UsefulAuthentication usefulauthentication;

	public Client() {
		usefulauthentication = new UsefulAuthentication();
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
