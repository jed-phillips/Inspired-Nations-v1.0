package com.github.InspiredOne.InspiredNations.Hud.Implem.ManageGov;

import java.util.HashSet;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.Facility;
import com.github.InspiredOne.InspiredNations.Governments.GovFactory;
import com.github.InspiredOne.InspiredNations.Governments.OwnerGov;
import com.github.InspiredOne.InspiredNations.Hud.Menu;
import com.github.InspiredOne.InspiredNations.Hud.PassByOptionMenu;
import com.github.InspiredOne.InspiredNations.Hud.PromptOption;
import com.github.InspiredOne.InspiredNations.Hud.Implem.NewFacility.PickFacilityType;

public class GovernmentRegions extends PassByOptionMenu {

	private OwnerGov gov;
	public GovernmentRegions(PlayerData PDI, OwnerGov gov) {
		super(PDI);
		this.gov = gov;
	}

	@Override
	public String getPreOptionText() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String getHeader() {
		// TODO Auto-generated method stub
		return "Government Regions";
	}

	@Override
	public Menu getPreviousMenu() {
		return new ManageGov(PDI, gov);
	}

	@Override
	public void init() {


		for(Class<? extends Facility> fac: gov.getGovFacilities()) {
			Facility facil = GovFactory.getGovInstance(fac);

			boolean allowed = true;
			for(Facility facility:gov.getFacilities()) {
				if(facility.getClass().equals(fac)) {
					allowed = false;
				}
			}
			if(!facil.isUnique() || allowed) {
				this.options.add(new PromptOption(this, "New "+facil.getTypeName(), new PickFacilityType<>(PDI, gov, fac)));
			}
		}
		for(Facility fac: gov.getFacilities()) {
			HashSet<Class<? extends Facility>> set = new HashSet<Class<? extends Facility>>();
			if(!set.contains(fac.getClass())) {
				this.options.add(new PromptOption(this, "Manage " + fac.getTypeName(), new PickFacilityToManage<>(PDI, this, gov, fac.getClass())));
			}
			set.add(fac.getClass());
		}
			
		
	}


}
