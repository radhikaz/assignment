package com.jpm.report.data;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class ReportDataGeneratorTest {
	
	@Parameter(value=0)
	public List<Instruction> instructions;

	@Parameter(value=1)
	public Object[][] expectedResult;
	
	
	@Test
	public void getAmountInUSDSettledIncomingEverydayTest() {
		ReportData dataGenerator = new ReportDataGenerator(instructions);
		assertThat(dataGenerator.getAmountInUSDSettledIncomingEveryday(), 
				is(((BigDecimal)expectedResult[0][0]).setScale(2, RoundingMode.HALF_UP)));
		
	}
	
	@Test
	public void getAmountInUSDSettledOutgoingEverydayTest() {
		ReportData dataGenerator = new ReportDataGenerator(instructions);

		assertThat(dataGenerator.getAmountInUSDSettledOutgoingEveryday(), 
				is(((BigDecimal)expectedResult[1][0]).setScale(2, RoundingMode.HALF_UP)));
	}
	
	@Test
	public void getRankingOfEntitiesOnIncomingAmountTest() {
		ReportData dataGenerator = new ReportDataGenerator(instructions);
		TreeSet<Instruction> ranking = dataGenerator.getRankingOfEntitiesOnIncomingAmount();
		List<String> entityList = ranking.stream()
			.map(inst -> inst.getEntity())
			.collect(Collectors.toList());
		assertThat(entityList , is(Arrays.asList(expectedResult[3])));
	}
	
	@Test
	public void getRankingOfEntitiesOnOutgoingAmountTest() {
		ReportData dataGenerator = new ReportDataGenerator(instructions);
		TreeSet<Instruction> ranking = dataGenerator.getRankingOfEntitiesOnOutgoingAmount();
		List<String> entityList = ranking.stream()
			.map(inst -> inst.getEntity())
			.collect(Collectors.toList());
		assertThat(entityList , is(Arrays.asList(expectedResult[2])));
	}
	
	@Test(expected = Test.None.class)
	public void nullDataTest() {
		ReportData dataGenerator = new ReportDataGenerator(null);
		dataGenerator.getAmountInUSDSettledIncomingEveryday();
	}
	
	@Parameters
	public static Object[][] data() {
		return new Object[][] { getDataset0(), getDataset1() };
	}
	
	static Object[] getDataset0() {
		try {
			
			var inst1 = new Instruction("foo", InstructionType.BUY, new BigDecimal(0.50), 
					Currency.SAR, LocalDate.parse("01 Jan 2016", Instruction.DATE_FORMATTER),
					200, new BigDecimal(100.25));
			
			var inst2 = new Instruction("bar", InstructionType.SELL, new BigDecimal(0.22), 
					Currency.INR, LocalDate.parse("03 Jan 2016", Instruction.DATE_FORMATTER),
					450, new BigDecimal(150.55));
						
			List<Instruction> list1 = new ArrayList<Instruction>();
			list1.add(inst1);
			list1.add(inst2);

			Object[][] expectedfResults = new Object[][] { 
				{ new BigDecimal(14904.45) }, 
				{ new BigDecimal(10025.00) }, 
				{ "foo" }, { "bar" } };
			return new Object[] { list1, expectedfResults };
		} catch (DateTimeParseException pe) {
			pe.printStackTrace();
		}
		return null;
	}
	
	static Object[] getDataset1() {
		try {
			
			var inst1 = new Instruction("foo1", InstructionType.BUY, new BigDecimal(0.50), 
					Currency.SAR, LocalDate.parse("01 Jan 2016", Instruction.DATE_FORMATTER),
					200, new BigDecimal(100.25)); //10025
			var inst2 = new Instruction("foo2", InstructionType.BUY, new BigDecimal(0.60), 
					Currency.GBP, LocalDate.parse("01 Jan 2016", Instruction.DATE_FORMATTER),
					100, new BigDecimal(50.25)); //3015
			var inst3 = new Instruction("foo3", InstructionType.BUY, new BigDecimal(0.10), 
					Currency.INR, LocalDate.parse("05 Jan 2016", Instruction.DATE_FORMATTER),
					300, new BigDecimal(48)); //1440
			var inst4 = new Instruction("bar1", InstructionType.SELL, new BigDecimal(0.10), 
					Currency.INR, LocalDate.parse("04 Jan 2016", Instruction.DATE_FORMATTER),
					450, new BigDecimal(200)); //9000
			var inst5 = new Instruction("bar2", InstructionType.SELL, new BigDecimal(0.50), 
					Currency.SAR, LocalDate.parse("08 Jan 2016", Instruction.DATE_FORMATTER),
					650, new BigDecimal(100.45));//32646.25
			var inst6 = new Instruction("bar3", InstructionType.SELL, new BigDecimal(0.22), 
					Currency.AED, LocalDate.parse("10 Jan 2016", Instruction.DATE_FORMATTER),
					50, new BigDecimal(150.55));//1656.05
			var inst7 = new Instruction("bar4", InstructionType.SELL, new BigDecimal(0.50), 
					Currency.SAR, LocalDate.parse("02 Jan 2016", Instruction.DATE_FORMATTER),
					450, new BigDecimal(159.55));//35898.75
			List<Instruction> list1 = new ArrayList<Instruction>();
			list1.add(inst1);
			list1.add(inst2);
			list1.add(inst3);
			list1.add(inst4);
			list1.add(inst5);
			list1.add(inst6);
			list1.add(inst7);
						
			Object[][] expectedfResults = new Object[][] 
					{ { new BigDecimal(79201.05) }, { new BigDecimal(14480.00) }, 
				{ "foo1","foo2","foo3" }, 
				{ "bar4","bar2","bar1","bar3" } };
			return new Object[] { list1, expectedfResults };
		} catch (DateTimeParseException pe) {
			pe.printStackTrace();
		}
		return null;
	}
}
