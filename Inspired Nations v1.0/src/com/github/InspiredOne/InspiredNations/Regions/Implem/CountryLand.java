package com.github.InspiredOne.InspiredNations.Regions.Implem;

import java.util.ArrayList;
import java.util.List;

import com.github.InspiredOne.InspiredNations.Regions.InspiredRegion;
import com.github.InspiredOne.InspiredNations.Regions.SelectionMode;

public class CountryLand extends InspiredRegion {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5322085601141117839L;
	private static final String typeName = "Country";
	
	@Override
	public List<Class<? extends SelectionMode>> getAllowedForms() {
		List<Class<? extends SelectionMode>> output = new ArrayList<Class<? extends SelectionMode>>();
		output.add(Chunkoid.class);
		return output;
	}

	@Override
	public List<Class<? extends InspiredRegion>> getEncapsulatingRegions() {
		List<Class<? extends InspiredRegion>> output = new ArrayList<Class<? extends InspiredRegion>>();
		return output;
	}

	@Override
	public List<Class<? extends InspiredRegion>> getAllowedOverlap() {
		List<Class<? extends InspiredRegion>> output = new ArrayList<Class<? extends InspiredRegion>>();
		return output;
	}

	@Override
	public String getTypeName() {
		return typeName;
	}

}
