package com.github.InspiredOne.InspiredNations.Hud.Implem.Money;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Economy.Account;
import com.github.InspiredOne.InspiredNations.Economy.AccountCollection;
import com.github.InspiredOne.InspiredNations.Economy.CurrencyAccount;
import com.github.InspiredOne.InspiredNations.Hud.Menu;
import com.github.InspiredOne.InspiredNations.Hud.PromptOption;
import com.github.InspiredOne.InspiredNations.Hud.TabSelectOptionMenu;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.OptionUnavail;

public class ManageCurrencies extends TabSelectOptionMenu<CurrencyAccount> {

	Menu previous;
	AccountCollection accounts;
	Account account;

	public ManageCurrencies(PlayerData PDI, Menu previous, Account account, AccountCollection accounts) {
		super(PDI);
		this.previous = previous;
		this.account = account;
		this.accounts = accounts;
	}

	@Override
	public Menu getPreviousPrompt() {
		return previous;
	}

	@Override
	public String postTabListPreOptionsText() {
		
		return "";
	}

	@Override
	public String getHeader() {
		return "Manage Currency";
	}

	@Override
	public void addTabOptions() {
		for(CurrencyAccount curren:account.getMoney()) {
			this.taboptions.add(curren);
		}
		
	}

	@Override
	public void addOptions() {
		if(this.taboptions.size() > 0) {
			this.options.add(new PromptOption(this, "Pay with " + this.getData().getName(), new PayNav(PDI, this, this.getData())));
			this.options.add(new ChangeTabOrderOption<>(this, "Change Currency Order <+/->", account.getMoney(), this.getData()));
			this.options.add(new PromptOption(this, "Transfer " + this.getData().getCurrency(), new PickAccount(PDI, this, accounts, this.getData())));
			if(this.taboptions.size() > 1) {
				this.options.add(new RemoveCurrencyOption(this, "Remove " + this.getData().getCurrency(), account, this.getData()));
			}
		}
		PickCurrencyToAdd next = new PickCurrencyToAdd(PDI, this , account);
		next.addTabOptions();
		if(next.getTabOptions().size() > 0) {
			this.options.add(new PromptOption(this, "Add Currency", next));
		}
		else {
			this.options.add(new PromptOption(this, "Add Currency", next, OptionUnavail.NO_CURRENCIES_TO_ADD));
		}
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}

}
