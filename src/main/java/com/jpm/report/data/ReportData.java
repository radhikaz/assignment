package com.jpm.report.data;

import java.math.BigDecimal;
import java.util.TreeSet;
/**
 * This interface represents data to be shown in report
 * @author Radhika
 *
 */
public interface ReportData {
	BigDecimal getAmountInUSDSettledIncomingEveryday();
	BigDecimal getAmountInUSDSettledOutgoingEveryday();
	TreeSet<Instruction> getRankingOfEntitiesOnIncomingAmount();
	TreeSet<Instruction> getRankingOfEntitiesOnOutgoingAmount();

}
