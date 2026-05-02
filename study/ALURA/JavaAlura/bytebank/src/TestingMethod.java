
public class TestingMethod {
	public static void main(String[] args) {
		// deposit
		Account myAccount = new Account();
		myAccount.balance = 100;
		myAccount.deposit(50);
		System.out.println(myAccount.balance);
		// withdraw
		boolean sucessfulWithdraw = myAccount.withdraw(20);
		System.out.println(myAccount.balance);
		System.out.println(sucessfulWithdraw);
		//transfer
		Account myFriendAccount = new Account();
		myFriendAccount.deposit(1000);
		System.out.println(myFriendAccount.balance);
		if(myFriendAccount.transfer(300, myAccount)) {
			System.out.println("Transferência Feita com Sucesso");
		} else {
			System.out.println("Faltou Dinheiro");
		}
		System.out.println(myFriendAccount.balance);
	}
}
