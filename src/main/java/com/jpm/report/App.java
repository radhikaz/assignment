package com.jpm.report;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.jpm.report.data.Currency;
import com.jpm.report.data.Instruction;
import com.jpm.report.data.InstructionType;
import com.jpm.report.data.ReportDataGenerator;
import com.jpm.report.reports.ConsoleReport;
import com.jpm.report.reports.Report;

/**
 * This is the sample class which can be run to test the report functionality
 * @author radhika
 *
 */
public class App 
{
	public static void main(String[] args) throws ParseException {
		ReportDataGenerator dataGenerator = new ReportDataGenerator(App.getInstructions());

		Report report = new ConsoleReport(dataGenerator);
		report.generateReport();
	}

	static List<Instruction> getInstructions() throws ParseException {
		List<Instruction> instList = new ArrayList<>();
		
		var inst1 = new Instruction("foo", InstructionType.BUY, 
				new BigDecimal(0.50), Currency.SGP, 
				LocalDate.parse("01 Jan 2016", Instruction.DATE_FORMATTER),
				200, new BigDecimal(100.25));
		
		var inst2 = new Instruction("bar", InstructionType.SELL, 
				new BigDecimal(0.22), Currency.INR, 
				LocalDate.parse("03 Jan 2016", Instruction.DATE_FORMATTER),
				450, new BigDecimal(150.55));
		
		instList.add(inst1);
		instList.add(inst2);
		return instList;
	}
}
