
public class NoClientAccount {
	public static void main(String[] args) {
		Account bachiraAccount = new Account();
		System.out.println(bachiraAccount.getBalance());
		bachiraAccount.barrer = new Client();
		bachiraAccount.barrer.name = "Bachira";
		System.out.println(bachiraAccount.barrer.name);
	}
}
