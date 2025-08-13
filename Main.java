package projects;


import java.time.LocalDate;
import java.util.List;
public class Main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Sample sales data
        List<SalesRecord> salesData = Arrays.asList(
            new SalesRecord("Laptop", "Electronics", "North", 75000, LocalDate.of(2025, 8, 1), "Alice"),
            new SalesRecord("Phone", "Electronics", "South", 50000, LocalDate.of(2025, 10, 2), "Bob"),
            new SalesRecord("Tablet", "Electronics", "West", 30000, LocalDate.of(2025, 8, 3), "Alice"),
            new SalesRecord("Shoes", "Fashion", "East", 8000, LocalDate.of(2025, 8, 4), "Charlie"),
            new SalesRecord("Watch", "Fashion", "North", 12000, LocalDate.of(2025, 10, 5), "Bob"),
            new SalesRecord("Laptop", "Electronics", "South", 70000, LocalDate.of(2025, 1, 6), "Alice")
        );

        // Initialize analytics system
        SalesAnalytics analytics = new SalesAnalytics();
        analytics.setSalesData(salesData);
        //analytics.salesData = salesData; // Assuming salesData is public or has a setter

        // 1. Basic Aggregations
        System.out.println("Total Sales: " + analytics.getTotalSales());
        System.out.println("Average Sale Amount: " + analytics.getAverageSaleAmount().orElse(0));
        System.out.println("Highest Sale: " + analytics.getHighestSale().map(SalesRecord::getAmount).orElse(0.0));

        // 2. Filtering and Grouping
        System.out.println("\nSales by Category:");
        analytics.getSalesByCategory().forEach((category, total) ->
            System.out.println("  " + category + ": " + total)
        );

        System.out.println("\nCount by Region:");
        analytics.getCountByRegion().forEach((region, count) ->
            System.out.println("  " + region + ": " + count)
        );
        
        System.out.println("\nSales by Month:");
        analytics.getSalesByMonth().forEach((s, list)->{
        	System.out.println(" "+s);
        	list.forEach(sr->System.out.println("   "+sr));
        });
        
        System.out.println("\nTop Performing Products:");
        analytics.getTopPerformingProducts(2).forEach((s, d)->System.out.println(" "+ s +" "+d));
        
        System.out.println("\nUnder Performing Regions:");
        analytics.getUnderperformingRegions(45000).forEach(s->System.out.println(" "+s));
        
        System.out.println("\nCategory Sales by Regions:");
        analytics.getCategorySalesByRegion().forEach((region,m)->{
        	System.out.println(" "+region);
        	m.forEach((category, amount)->{
        		System.out.println("   "+category+" "+amount);
        	});
        });
        
        System.out.println("\nMonthly Summery:");
        analytics.generateMonthlySummary().forEach(sr->System.out.println(sr));
        
        System.out.println("\nSales Person Statistics:");
        analytics.getSalespersonStats().forEach((sales_person, stats)->{
        	System.out.println(" "+sales_person);
        	System.out.println("    "+stats);
        });
        
        //SalesAnalytics analytics1 = new SalesAnalytics();
        //analytics1.setSalesData(salesData); // assuming setter exists

        System.out.println("\n🔍 Sales over ₹50,000:");
        analytics.findSalesBy(s -> s.getAmount() > 50000)
            .forEach(s -> System.out.println(s.getProduct() + " → ₹" + s.getAmount()));

        System.out.println("\n📅 Sales in August 2025:");
        analytics.findSalesBy(s -> s.getDate().getMonthValue() == 8 && s.getDate().getYear() == 2025)
            .forEach(s -> System.out.println(s.getProduct() + " → " + s.getDate()));

        System.out.println("\n🌍 Sales from South region:");
        analytics.findSalesBy(s -> s.getRegion().equalsIgnoreCase("South"))
            .forEach(s -> System.out.println(s.getProduct() + " → " + s.getRegion()));

	}

}
