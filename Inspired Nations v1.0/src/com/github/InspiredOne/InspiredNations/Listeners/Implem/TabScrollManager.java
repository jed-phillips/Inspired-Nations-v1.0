package com.github.InspiredOne.InspiredNations.Listeners.Implem;

import com.github.InspiredOne.InspiredNations.Hud.ActionMenu;
import com.github.InspiredOne.InspiredNations.Listeners.TabManager;

public class TabScrollManager<T extends ActionMenu> extends TabManager<T> {
	
	public boolean scrollUp = false;
	public boolean neither = false; // true if tabbed with something other than + or -
	
	public TabScrollManager(T menu) {
		super(menu);
		listeners.add(new TabListener<TabScrollManager<T>>(this));
	}

	@Override
	public void Update() {
		if(this.preTabEntry.equals("+")) {
			neither = false;
			scrollUp = true;
		}
		else if(this.preTabEntry.equals("-")) {
			neither = false;
			scrollUp = false;
		}
		else{
			neither = true;
		}
		this.getActionMenu().Update();
	}
}
