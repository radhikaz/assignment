package com.jpm.report.reports;

import java.math.RoundingMode;

import com.jpm.report.data.ReportDataGenerator;

/**
 * This class generates the report and print it on console
 * @author Radhika
 *
 */
public class ConsoleReport implements Report {
	ReportDataGenerator reportDataGenerator;
	
	public ConsoleReport(ReportDataGenerator reportDataGenerator) {
		this.reportDataGenerator = reportDataGenerator;
	}
	
	@Override
	public void generateReport() {
		if(reportDataGenerator == null) {
			System.out.println("No data to generate report");
			return;
		}
		System.out.println("---------------- Report ----------------\n\n");
		System.out.println("---------------- Amount in USD settled incoming everyday ----------------");
		System.out.println(this.reportDataGenerator.getAmountInUSDSettledIncomingEveryday());
		System.out.println("---------------- Amount in USD settled outgoing everyday ----------------");		
		System.out.println(this.reportDataGenerator.getAmountInUSDSettledOutgoingEveryday());
		
		System.out.println("---------------- Ranking of entities based on incoming amount ----------------");
		
		this.reportDataGenerator.getRankingOfEntitiesOnIncomingAmount().stream()
			.map(inst -> inst.getEntity() + "(" + inst.getUSDAmountOfTrade().setScale(2, RoundingMode.HALF_UP)+ ")")
			.forEach(System.out::println);
		
		System.out.println("---------------- Ranking of entities based on outgoing amount ----------------");
		this.reportDataGenerator.getRankingOfEntitiesOnOutgoingAmount().stream()
		.map(inst -> inst.getEntity() + "(" + inst.getUSDAmountOfTrade().setScale(2, RoundingMode.HALF_UP)+ ")")
		.forEach(System.out::println);
			
	}

}
