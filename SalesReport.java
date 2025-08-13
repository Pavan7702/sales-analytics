package projects;

class SalesReport {
	private String month;
	private double totalSales;
	private double avgSales;
	private String topProduct;
	private long recordCount;
	
	// Constructor, getters, setters
	public SalesReport(String month, double totalSales, double avgSales, String topProduct, long recordCount) {
		super();
		this.month = month;
		this.totalSales = totalSales;
		this.avgSales = avgSales;
		this.topProduct = topProduct;
		this.recordCount = recordCount;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public double getTotalSales() {
		return totalSales;
	}
	public void setTotalSales(double totalSales) {
		this.totalSales = totalSales;
	}
	public double getAvgSales() {
		return avgSales;
	}
	public void setAvgSales(double avgSales) {
		this.avgSales = avgSales;
	}
	public String getTopProduct() {
		return topProduct;
	}
	public void setTopProduct(String topProduct) {
		this.topProduct = topProduct;
	}
	public long getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}
	@Override
	public String toString() {
		return "month=" + month + 
				", totalSales=" + totalSales + 
				", avgSales=" + avgSales +
				", topProduct="+ topProduct + 
				", recordCount=" + recordCount ;
	}
}
