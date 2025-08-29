package projects;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collector;
import java.util.stream.Collectors;
class SalesAnalytics {
	private List<SalesRecord> salesData;

	public List<SalesRecord> getSalesData() {
		return salesData;
	}

	public void setSalesData(List<SalesRecord> salesData) {
		this.salesData = salesData;
	}

	// Stream-based analytics methods to implement:
	// 1. Basic Aggregations
	public double getTotalSales() {
		// Sum all sales amounts using streams
		return salesData.stream().mapToDouble(SalesRecord::getAmount).sum();
	}

	public OptionalDouble getAverageSaleAmount() {
		// Calculate average sale amount
		return salesData.stream().mapToDouble(SalesRecord::getAmount).average();
	}

	public Optional<SalesRecord> getHighestSale() {
		// Find record with maximum amount
		return salesData.stream().max(Comparator.comparingDouble(SalesRecord::getAmount));
	}

	// 2. Filtering and Grouping
	public Map<String, Double> getSalesByCategory() {
		// Group by category, sum amounts
		Map<String, Double>res=salesData.stream()
				.collect(Collectors.groupingBy(SalesRecord::getCategory,
						Collectors.summingDouble(SalesRecord::getAmount)));
		return res;
	}

	public Map<String, Long> getCountByRegion() {
		// Count sales records by region
		Map<String, Long>res=salesData.stream()
				.collect(Collectors.groupingBy(SalesRecord::getRegion,
						Collectors.counting()));
		return res;
	}

	@SuppressWarnings("rawtypes")
	public Map<String, List<SalesRecord>> getSalesByMonth() {
		// Group by month-year
		Map<String, List<SalesRecord>>res=salesData.stream()
				.collect(Collectors.groupingBy(
						record -> record.getDate().getMonth() + "-" + record.getDate().getYear()
					));
		return res;
	}
	// 3. Complex Analytics
	public Map<String, Double> getTopPerformingProducts(int limit) {
		// Top N products by total sales
		// Return Map<ProductName, TotalSales>
		//Stream<SalesRecord>list=salesData.stream().sorted(Comparator.comparing(SalesRecord::getAmount).reversed()).limit(limit);
		Map<String, Double>res1=salesData.stream()
				.collect(Collectors.groupingBy(SalesRecord::getProduct,
						Collectors.summingDouble(SalesRecord::getAmount)));
		Map<String, Double>res2=res1.entrySet().stream()
		        .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
		        .limit(limit)
		        .collect(Collectors.toMap(
		            Map.Entry::getKey,
		            Map.Entry::getValue,
		            (e1, e2) -> e1,
		            LinkedHashMap::new
		        ));
		return res2;
	}

	public List<String> getUnderperformingRegions(double threshold) {
		// Regions with total sales below threshold
		// Return sorted list of region names
		Map<String, Double>m=salesData.stream()
				.collect(Collectors.groupingBy(SalesRecord::getRegion,
						Collectors.summingDouble(SalesRecord::getAmount)));
		List<String>list=m.entrySet().stream()
							.filter(n->n.getValue()<threshold)
							.map(Map.Entry::getKey)
							.sorted()
							.collect(Collectors.toList());
		return list;
	}

	public Map<String, Map<String, Double>> getCategorySalesByRegion() {
		// Nested grouping: Region -> Category -> Total Sales
		Map<String, Map<String, Double>>res=salesData.stream()
					.collect(Collectors.groupingBy(SalesRecord::getRegion,
							Collectors.groupingBy(
										SalesRecord::getCategory,
										Collectors.summingDouble(SalesRecord::getAmount)
							 )));
		return res;
	}

	// 4. Advanced Transformations
	public List<SalesReport> generateMonthlySummary() {
		// Transform raw data into monthly summary objects
		// Include: month, totalSales, avgSales, topProduct
		
		Map<String, List<SalesRecord>>m=salesData.stream()
					.collect(Collectors.groupingBy(
							record -> record.getDate().getMonth() + "-" + record.getDate().getYear()
						));
		List<SalesReport>res=m.entrySet().stream()
					.map(entry -> {
			            List<SalesRecord> records = entry.getValue();
			            double total = records.stream().mapToDouble(SalesRecord::getAmount).sum();
			            double avg = records.stream().mapToDouble(SalesRecord::getAmount).average().orElse(0);
			            String topProduct = records.stream()
				                .collect(Collectors.groupingBy(
				                    SalesRecord::getProduct,
				                    Collectors.summingDouble(SalesRecord::getAmount)
				                ))
				                .entrySet().stream()
				                .max(Map.Entry.comparingByValue())
				                .map(Map.Entry::getKey)
				                .orElse("N/A");
			            return new SalesReport(entry.getKey(), total, avg, topProduct, records.size());
			        })
			        .collect(Collectors.toList());
		return res;
	}

	public Map<String, Statistics> getSalespersonStats() {
		// For each salesperson: count, total, average, best sale
		// Use custom Statistics class
		 return salesData.stream()
			        .collect(Collectors.groupingBy(
			            SalesRecord::getSalesperson,
			            Collector.of(
			                Statistics::new,
			                (stats, record) -> stats.accept(record),
			                Statistics::combine
			            )
			        ));
	}

	// 5. Real-time Filtering
	public Stream<SalesRecord> findSalesBy(Predicate<SalesRecord> criteria) {
		// Flexible filtering system
		// Examples: by date range, amount range, region, etc.
		return salesData.stream().filter(criteria);
	}

	// 6. Custom Collectors
	public static Collector<SalesRecord, ?, Map<String, Double>>topProductsByCategory(int limit) {
	    // Custom collector for complex aggregations
		return Collectors.collectingAndThen(
		        Collectors.groupingBy(SalesRecord::getCategory),
		        categoryMap -> categoryMap.values().stream()
		            .flatMap(records -> records.stream()
		                .collect(Collectors.groupingBy(
		                    SalesRecord::getProduct,
		                    Collectors.summingDouble(SalesRecord::getAmount)
		                ))
		                .entrySet().stream()
		            )
		            .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
		            .limit(limit)
		            .collect(Collectors.toMap(
		                Map.Entry::getKey,
		                Map.Entry::getValue,
		                (a, b) -> a,
		                LinkedHashMap::new
		            ))
		    );
	}

	// 7. Parallel Processing
	public CompletableFuture<Map<String, Double>>getAsyncSalesByRegion() {
	    // Async processing with parallel streams
		//System.out.println("Sales data size: " + salesData.size());
			return CompletableFuture.supplyAsync(() ->
	        salesData.stream()
	            .collect(Collectors.groupingBy(
	                SalesRecord::getRegion,
	                Collectors.summingDouble(SalesRecord::getAmount)
	            ))
			);	
	}

	// 8. Stream Pipeline Optimization
	public List<ProductPerformance> getOptimizedProductAnalysis() {
	    // Single pass through data for multiple calculations
	    // Minimize intermediate collections
		return salesData.stream()
		        .collect(Collectors.groupingBy(SalesRecord::getProduct,
		            Collector.of(
		                () -> new double[3], // [total, count, max]
		                (a, r) -> {
		                    a[0] += r.getAmount(); // total
		                    a[1] += 1;             // count
		                    a[2] = Math.max(a[2], r.getAmount()); // max
		                },
		                (a, b) -> {
		                    a[0] += b[0];
		                    a[1] += b[1];
		                    a[2] = Math.max(a[2], b[2]);
		                    return a;
		                },
		                a -> new ProductPerformance(
		                    "", // product name will be added later
		                    a[0],
		                    (long) a[1],
		                    a[0] / a[1]
		                )
		            )
		        ))
		        .entrySet().stream()
		        .map(e -> {
		            ProductPerformance p = e.getValue();
		            p.setProduct(e.getKey());
		            return p;
		        })
		        .sorted(Comparator.comparing(ProductPerformance::getTotalSales).reversed())
		        .collect(Collectors.toList());
	}
}
