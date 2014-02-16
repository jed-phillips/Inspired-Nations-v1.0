package com.github.InspiredOne.InspiredNations.Hud.Implem;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Economy.MarketPlace;
import com.github.InspiredOne.InspiredNations.Economy.Sellable;
import com.github.InspiredOne.InspiredNations.Hud.Menu;
import com.github.InspiredOne.InspiredNations.Hud.TabSelectOptionMenu;

public class BuyMenu extends TabSelectOptionMenu<Sellable> {

	MarketPlace<?> market;
	
	public BuyMenu(PlayerData PDI, MarketPlace<?> market) {
		super(PDI);
		this.market = market;
	}

	@Override
	public Menu getPreviousPrompt() {
		return new PickMarketplace(PDI);
	}

	@Override
	public String postTabListPreOptionsText() {
		return "";
	}

	@Override
	public void Init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TabSelectOptionMenu<?> GetSelf() {
		return new BuyMenu(PDI, market);
	}

	@Override
	public String getHeader() {
		return market.getName();
	}

}
