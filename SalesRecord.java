package projects;

import java.time.LocalDate;
class SalesRecord {
	private String product;
	private String category;
	private String region;
	private double amount;
	private LocalDate date;
	private String salesperson;
	
	// Constructor, getters, setters
	SalesRecord(String product, String category, String region, double amount, LocalDate date, String salesperson){
		this.product=product;
		this.category=category;
		this.region=region;
		this.amount=amount;
		this.date=date;
		this.salesperson=salesperson;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getSalesperson() {
		return salesperson;
	}
	public void setSalesperson(String salesperson) {
		this.salesperson = salesperson;
	}
	@Override
	public String toString() {
		return "product=" + product +
				", category=" + category +
				", region=" + region +
				", amount="+ amount +
				", date=" + date +
				", salesperson=" + salesperson ;
	}
	
}
