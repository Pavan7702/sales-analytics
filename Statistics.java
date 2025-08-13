package projects;


class Statistics {
	private long count;
	private double total;
	private double average;
	private double max;
	private String bestMonth;

	// Constructor, getters, setters
	public Statistics(long count, double total, double average, double max, String bestMonth) {
		this.count = count;
		this.total = total;
		this.average = average;
		this.max = max;
		this.bestMonth = bestMonth;
	}
	public Statistics() {}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
//	public double getAverage() {
//		return average;
//	}
	public void setAverage(double average) {
		this.average = average;
	}
	public double getMax() {
		return max;
	}
	public void setMax(double max) {
		this.max = max;
	}
	public String getBestMonth() {
		return bestMonth;
	}
	public void setBestMonth(String bestMonth) {
		this.bestMonth = bestMonth;
	}
	public void accept(SalesRecord record) {
        count++;
        total += record.getAmount();
        average = total / count;
        if (record.getAmount() > max) {
            max = record.getAmount();
            bestMonth = record.getDate().getMonth().toString();
        }
    }

    public Statistics combine(Statistics other) {
        Statistics combined = new Statistics();
        combined.count = this.count + other.count;
        combined.total = this.total + other.total;
        combined.max = Math.max(this.max, other.max);
        combined.bestMonth = this.max >= other.max ? this.bestMonth : other.bestMonth;
        return combined;
    }

    public double getAverage() {
        return count == 0 ? 0 : total / count;
    }
	@Override
	public String toString() {
		return "count=" + count +
				", total=" + total +
				", average=" + average +
				", max=" + max+
				", bestMonth=" + bestMonth;
	}
    
}
