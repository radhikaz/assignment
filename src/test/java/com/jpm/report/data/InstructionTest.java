
//export PATH=/opt/apache-maven-3.6.0/bin:$PATH
package com.jpm.report.data;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class InstructionTest {
	
	@Parameter(value=0)
	public Instruction instruction;
	
	@Parameter(value=1)
	public Object settlementDateExpected;
	
	@Parameter(value=2)
	public Object usdAmountExpected;

	@Test
	public void settlementDateForCurrencyTest(){
			try {
				assertThat(instruction.getSettlementDate(), 
						is(LocalDate.parse(
								(String)settlementDateExpected, 
								Instruction.DATE_FORMATTER)));
			} catch (DateTimeParseException e) {				
				e.printStackTrace();
			}
	}
	
	@Test
	public void getUSDAmountOfTradeTest() {		
		assertThat(instruction.getUSDAmountOfTrade(), 
				is(((BigDecimal)usdAmountExpected).setScale(2, RoundingMode.HALF_UP)));
	}
	
	@Parameters
	public static Object[][] data() {
		

		try {
			var inst1 = new Instruction("foo", InstructionType.BUY, new BigDecimal(0.50), 
					Currency.SAR, 
					LocalDate.parse("01 Jan 2016", Instruction.DATE_FORMATTER),
					100, new BigDecimal(100.25)); //friday
			var inst2 = new Instruction("foo", InstructionType.BUY, new BigDecimal(0.50), 
					Currency.SAR, 
					LocalDate.parse("02 Jan 2016", Instruction.DATE_FORMATTER),
					200, new BigDecimal(100.25)); // saturday
			var inst3 = new Instruction("foo", InstructionType.BUY, new BigDecimal(0.50), 
					Currency.SAR, 
					LocalDate.parse("03 Jan 2016", Instruction.DATE_FORMATTER),
					300, new BigDecimal(100.25)); // sunday
			
			var inst4 = new Instruction("bar", InstructionType.SELL, new BigDecimal(0.22), 
					Currency.INR, 
					LocalDate.parse("01 Jan 2016", Instruction.DATE_FORMATTER),
					450, new BigDecimal(150.55));
			var inst5 = new Instruction("bar", InstructionType.SELL, new BigDecimal(0.22), 
					Currency.INR, 
					LocalDate.parse("02 Jan 2016", Instruction.DATE_FORMATTER),
					550, new BigDecimal(150.55));
			var inst6 = new Instruction("bar", InstructionType.SELL, new BigDecimal(0.22), 
					Currency.INR, 
					LocalDate.parse("03 Jan 2016", Instruction.DATE_FORMATTER),
					650, new BigDecimal(150.55));
			return new Object[][] {
					{inst1, "03 Jan 2016", new BigDecimal(5012.50)},
					{inst2, "03 Jan 2016", new BigDecimal(10025.00)},
					{inst3, "03 Jan 2016", new BigDecimal(15037.50)},
					{inst4, "01 Jan 2016", new BigDecimal(14904.45)},
					{inst5, "04 Jan 2016", new BigDecimal(18216.55)},
					{inst6, "04 Jan 2016", new BigDecimal(21528.65)}
			};
		} catch (DateTimeParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
