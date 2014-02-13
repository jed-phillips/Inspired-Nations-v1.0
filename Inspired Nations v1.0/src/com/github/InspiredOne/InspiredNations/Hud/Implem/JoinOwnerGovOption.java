package com.github.InspiredOne.InspiredNations.Hud.Implem;

import com.github.InspiredOne.InspiredNations.Governments.OwnerGov;
import com.github.InspiredOne.InspiredNations.Hud.Menu;
import com.github.InspiredOne.InspiredNations.Hud.Option;
import com.github.InspiredOne.InspiredNations.Hud.OptionMenu;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.OptionUnavail;

public class JoinOwnerGovOption extends Option {

	OwnerGov gov;
	
	public JoinOwnerGovOption(OptionMenu menu, String label,
			OptionUnavail reason, OwnerGov gov) {
		super(menu, label, reason);
		this.gov = gov;
	}

	public JoinOwnerGovOption(OptionMenu menu, String label, OwnerGov gov) {
		super(menu, label);
		this.gov = gov;
	}

	public JoinOwnerGovOption(OptionMenu menu, String label, String description, OwnerGov gov) {
		super(menu, label, description);
		this.gov = gov;
	}

	@Override
	public Menu response(String input) {
		gov.addOwner(menu.PDI.getPlayerID());
		return menu;
	}

}
