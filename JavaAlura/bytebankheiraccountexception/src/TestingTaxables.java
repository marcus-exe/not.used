
public class TestingTaxables {

	public static void main(String[] args) {
		CheckingAccount checkingaccount = new CheckingAccount(222, 333);
		checkingaccount.deposit(100);
		LifeInsurance lifeinsurance = new LifeInsurance();
		
		TaxesCalculator taxescalculator = new TaxesCalculator();
		taxescalculator.register(lifeinsurance);
		taxescalculator.register(checkingaccount);
		
		System.out.println(taxescalculator.getTotalTaxes());

	}

}
