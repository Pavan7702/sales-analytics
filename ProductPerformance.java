class ProductPerformance {
    private String product;
    private double totalSales;
    private long count;
    private double average;

    // Constructor, getters, setters
	public ProductPerformance(String product, double totalSales, long count, double average) {
		super();
		this.product = product;
		this.totalSales = totalSales;
		this.count = count;
		this.average = average;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public double getTotalSales() {
		return totalSales;
	}
	public void setTotalSales(double totalSales) {
		this.totalSales = totalSales;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public double getAverage() {
		return average;
	}
	public void setAverage(double average) {
		this.average = average;
	}
	@Override
	public String toString() {
		return "ProductPerformance [product=" + product + ", totalSales=" + totalSales + ", count=" + count
				+ ", average=" + average + "]";
	}
}
