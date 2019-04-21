package com.jpm.report.data;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class ReportDataGenerator implements ReportData {
	private List<Instruction> data;
	
	public ReportDataGenerator(List<Instruction> data) {
		if(data == null) {
			this.data = new ArrayList<Instruction>();
		}else {
			this.data = data;
		}		
	}

	@Override
	public BigDecimal getAmountInUSDSettledIncomingEveryday() {

		Optional<BigDecimal> amount = data.stream().
				filter(inst -> inst.getInstType().equals(InstructionType.SELL))
				.map(Instruction::getUSDAmountOfTrade)
				.collect(Collectors.reducing(BigDecimal::add));
		
		if(amount.isPresent()) {
			return amount.get().setScale(2, RoundingMode.HALF_UP);
		}
		return new BigDecimal(0);
	}

	@Override
	public BigDecimal getAmountInUSDSettledOutgoingEveryday() {

		Optional<BigDecimal> amount = data.stream()
				.filter(inst -> inst.getInstType().equals(InstructionType.BUY))
				.map(Instruction::getUSDAmountOfTrade)
				.collect(Collectors.reducing(BigDecimal::add));

		if(amount.isPresent()) {
			return amount.get().setScale(2, RoundingMode.HALF_UP);
		}
		return new BigDecimal(0);
	}

	@Override
	public TreeSet<Instruction> getRankingOfEntitiesOnIncomingAmount() {
		TreeSet<Instruction> rankingOnAmount = data.stream()
				.filter(inst -> inst.getInstType().equals(InstructionType.SELL))
				.collect(Collectors.toCollection(
						() -> new TreeSet<>(Comparator.comparing(
								Instruction::getUSDAmountOfTrade).reversed())));

		return rankingOnAmount;
	}
	
	@Override
	public TreeSet<Instruction> getRankingOfEntitiesOnOutgoingAmount() {
		TreeSet<Instruction> rankingOnAmount = data.stream()
				.filter(inst -> inst.getInstType().equals(InstructionType.BUY))
				.collect(Collectors.toCollection(
						() -> new TreeSet<>(Comparator.comparing(
								Instruction::getUSDAmountOfTrade).reversed())));

		return rankingOnAmount;
	}

}
