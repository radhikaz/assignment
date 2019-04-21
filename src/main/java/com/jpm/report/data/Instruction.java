package com.jpm.report.data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * This class represents each instruction sent by the client
 * 
 * @author Radhika
 *
 */
public class Instruction {

	/*
	 * A financial entity whose shares are to be bought or sold
	 * 
	 */
	private String entity;
	/*
	 * represents Buy/Sell instruction
	 */
	private InstructionType instType;
	/*
	 * foreign exchange rate with respect to USD that was agreed
	 */
	private BigDecimal agreedFx;
	private Currency currency;
	/*
	 * Date on which the instruction was sent to JP Morgan by various clients
	 */
	private LocalDate instructionStartDate;
	/*
	 * The date on which the client wished for the instruction to be settled with
	 * respect to Instruction Date
	 */
	private LocalDate settlementDate;
	/*
	 * Number of shares to be bought or sold
	 */
	private int units;
	private BigDecimal pricePerUnit;

	public static final DateTimeFormatter DATE_FORMATTER = 
			DateTimeFormatter.ofPattern("dd MMM yyyy");
	
	public Instruction(String entity, InstructionType instType, 
			BigDecimal agreedFx, Currency currency,
			LocalDate instructionStartDate, int units, 
			BigDecimal pricePerUnit) {
		this.entity = entity;
		this.instType = instType;
		this.agreedFx = agreedFx;
		this.currency = currency;
		this.instructionStartDate = instructionStartDate;
		this.units = units;
		this.pricePerUnit = pricePerUnit;
		adjustSettlementDate();
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public InstructionType getInstType() {
		return instType;
	}

	public void setInstType(InstructionType instType) {
		this.instType = instType;
	}

	public BigDecimal getAgreedFx() {
		return agreedFx;
	}

	public void setAgreedFx(BigDecimal agreedFx) {
		this.agreedFx = agreedFx;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public LocalDate getInstructionStartDate() {
		return instructionStartDate;
	}

	public void setInstructionStartDate(LocalDate instructionStartDate) {
		this.instructionStartDate = instructionStartDate;
		adjustSettlementDate();
	}

	public LocalDate getSettlementDate() {
		return settlementDate;
	}

	/*
	 * This method calculates the settlement date from the given instruction start
	 * date. 
	 * A work week starts Monday and ends Friday, unless the currency of the
	 * trade is AED or SAR, where the work week starts Sunday and ends Thursday.
	 * No other holidays are taken into account. 
	 * If an instructed settlement date falls on a weekend, then the settlement date 
	 * will be changed to the next working day.
	 */
	private void adjustSettlementDate() {
		int startDay = currency.getWeekStartDay();
		int[] weekends = currency.getWeekends();

		int instDay = getInstructionStartDate().getDayOfWeek().getValue();
		settlementDate = this.getInstructionStartDate();
		boolean isOnWeekend = Arrays.stream(weekends).anyMatch(day -> day == instDay);
		if (isOnWeekend) // check if instruction date is on weekend
		{
			//calculate the settlement date
			int diff = 0;
			if (instDay <= startDay)
				diff = startDay - instDay;
			else {
				diff = (7 - instDay) + startDay;
			}
			// adjust the settlement date
			settlementDate = getInstructionStartDate().plusDays(diff);
		}
				
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

	public BigDecimal getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(BigDecimal pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	/**
	 * USD amount of a trade = Price per unit * Units * Agreed Fx
	 * @return USD amount of trade
	 */
	public BigDecimal getUSDAmountOfTrade() {
		BigDecimal returnValue = (pricePerUnit.multiply(agreedFx)
				.multiply(new BigDecimal(this.getUnits())))
				.setScale(2, RoundingMode.HALF_UP);
		return returnValue;
	}

	@Override
	public String toString() {
		return "Instruction [entity=" + entity + ", agreedFx=" + agreedFx + ", currency=" + currency
				+ ", instructionStartDate=" + instructionStartDate + ", settlementDate=" + settlementDate + ", units="
				+ units + ", pricePerUnit=" + pricePerUnit + "]";
	}

}
