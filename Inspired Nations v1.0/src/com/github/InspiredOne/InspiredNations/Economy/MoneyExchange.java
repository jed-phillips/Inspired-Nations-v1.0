package com.github.InspiredOne.InspiredNations.Economy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;

import com.github.InspiredOne.InspiredNations.InspiredNations;

public class MoneyExchange implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3674609233229292913L;
	public MathContext mcup = new MathContext(50, RoundingMode.UP);
	public MathContext mcdown = new MathContext(50, RoundingMode.DOWN);

	private HashMap<Currency, BigDecimal> Exchange = new HashMap<Currency, BigDecimal>();
	
	public void registerCurrency(Currency currency, BigDecimal diamondValue) {
		
		BigDecimal amount = new BigDecimal(InspiredNations.plugin.getConfig().getString("exchange_multiplyer"));
		if(!this.Exchange.containsKey(currency)) {
			Exchange.put(currency, amount.multiply(diamondValue));
		}
	}
	
	public void unregisterCurrency(Currency currency) {
		this.Exchange.remove(currency);
	}
	
	/**
	 * Finds the value of money required in valueType that would yeild mon in montype if exchanged.
	 * The reason getExchangeValue does not work in a transfer context is that getExchangeValue
	 * assumes that monType depreciates when exchanged. Because in a transfer there is no exchange
	 * happening, the monType does not depreciate and therefore more valueType is required than
	 * what is found using getExchangeValue.
	 * @param mon		the amount of mon you're trying to get
	 * @param monType	the type of mon
	 * @param valueType	the type of currency you're using to get mon
	 * @return
	 */
	public final BigDecimal getTransferValue(BigDecimal mon, Currency monType, Currency valueType, MathContext round) {
		BigDecimal output = BigDecimal.ZERO;
		BigDecimal B = Exchange.get(valueType);
		BigDecimal A = Exchange.get(monType);
		if(monType.equals(valueType)) {
			return mon;
		}
		if(mon.compareTo(BigDecimal.ZERO) <= 0) {
			return BigDecimal.ZERO;
		}
		//Figure out the value of things after the exchange
/*		BigDecimal c = mon.multiply(B.pow(2));
		BigDecimal b = ((new BigDecimal(2)).multiply(B)).add(
				mon.multiply(B).subtract(A.multiply(B)));
		BigDecimal a = (new BigDecimal(2)).multiply(mon);
		
		Debug.print("Transfer Value a: " + Tools.cut(a));
		Debug.print("Transfer Value b: " + Tools.cut(b));
		Debug.print("Transfer Value c: " + Tools.cut(c));
		
		return BigDecimalUtils.quad(a, b, c, round);*/
		
		

		BigDecimal difference = A.subtract(mon);
		output = mon.multiply(B).divide(difference, round);
		return output;
	}
	
	public void tempfunction() {
		Currency test = InspiredNations.getTestingPlayer().getCurrency();
		for(int i = 0;i<400; i +=2) {
			BigDecimal Transvalue = this.getTransferValue(new BigDecimal(i), Currency.DEFAULT, test, mcup);
			//BigDecimal ExchangeValue = this.getExchangeValue(Transvalue, test, );
		}
	}

	
	/**
	 * Gets the total amount of valueType you would recieve if you exchanged mon amount of monType
	 * @param mon
	 * @param monType
	 * @param valueType
	 * @return
	 */
	public final BigDecimal getExchangeValue(BigDecimal mon, Currency monType, Currency valueType) {
		//TODO I'm still not sure about having mcup here. Maybe it should be mcdown.
		BigDecimal output = this.getExchangeValue(mon, monType, valueType, mcup);
		return output;
	}
	
	public final BigDecimal getExchangeValue(BigDecimal mon, Currency monType, Currency valueType, MathContext round) {
		
		BigDecimal output;
		//TODO put these lines back into the else statement.
		BigDecimal valueAmount = Exchange.get(valueType);
		BigDecimal monAmount = Exchange.get(monType);
		//TODO end of the lines I need to put back in the else statement.
		
		//Remove if(monType.equals(valueType) when you figure it out
		
		if(monType.equals(valueType)) {
			output = mon;
		}
		else {
			BigDecimal monSum = monAmount.add(mon);
			BigDecimal division = valueAmount.divide(monSum, round);
			output = mon.multiply(division);
		}
		return output;
	}
	
	public final BigDecimal exchange(BigDecimal mon, Currency monType, Currency valueType) {
		BigDecimal outputup = this.getExchangeValue(mon, monType, valueType, mcup);
		BigDecimal outputdown = this.getExchangeValue(mon, monType, valueType, mcdown); //added outputdown
		Exchange.put(monType, Exchange.get(monType).add(mon));
		Exchange.put(valueType, Exchange.get(valueType).subtract(outputdown));
		
		return outputup;
	}
	
	@SuppressWarnings("unchecked")
	public final HashMap<Currency, BigDecimal> getExchangeMap() {
		return (HashMap<Currency, BigDecimal>) this.Exchange.clone();
	}
	
}
