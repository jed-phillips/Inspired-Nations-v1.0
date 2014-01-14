package com.github.InspiredOne.InspiredNations.Governments;

import java.math.BigDecimal;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Economy.Currency;
import com.github.InspiredOne.InspiredNations.ToolBox.Tools;

public class GovFactory {

	InspiredGov gov;
	BigDecimal diamondvalue = BigDecimal.ONE;
	public GovFactory(Class<? extends InspiredGov> gov) {
		this.gov = GovFactory.getGovInstance(gov);
		//this.gov.setRegion(Tools.getInstance(this.gov.getInspiredRegion()));
	}
	
	public GovFactory withName(String name) {
		this.gov.setName(name);
		return this;
	}
	
	public GovFactory withSuperGov(InspiredGov gov) {
		this.gov.setSuperGovObj(gov);
		if(!this.gov.getCommonEcon().equals(this.gov.getClass())) {
			this.gov.setCurrency(gov.getCurrency());
		}
		return this;
	}
	
	public GovFactory withMoneyname(String name) {
		this.gov.setCurrency(new Currency(name));
		return this;
	}
	
	public GovFactory withDiamondValue(BigDecimal multiplyer) {
		this.diamondvalue = multiplyer;
		return this;
	}
	
	public GovFactory withCurrency(Currency currency) {
		this.gov.setCurrency(currency);
		return this;
	}
	
	public InspiredGov getGov() {
		return this.gov;
	}

	public void registerGov() {
		InspiredNations.regiondata.put(gov.getClass(), gov);
		InspiredNations.Exchange.registerCurrency(this.getGov().getCurrency(), diamondvalue);
		//TODO change BigDecimal.ONE to whatever you're going to use for default values for exchanger
	}
	
	public static <T extends InspiredGov> T getGovInstance(Class<T> gov) {
		return Tools.getInstance(gov);
	}
	
	
	
}