
public class TaxesCalculator {
	
	private double totalTaxes;
	
	public void register(Taxable taxable) {
		double value = taxable.getValueTaxes();
		this.totalTaxes += value;
	}
	public double getTotalTaxes() {
		return totalTaxes;
	}
}
