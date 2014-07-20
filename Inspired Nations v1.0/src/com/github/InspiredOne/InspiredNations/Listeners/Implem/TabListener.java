package com.github.InspiredOne.InspiredNations.Listeners.Implem;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;

import com.github.InspiredOne.InspiredNations.Debug;
import com.github.InspiredOne.InspiredNations.Economy.Currency;
import com.github.InspiredOne.InspiredNations.Hud.TabSelectOptionMenu;
import com.github.InspiredOne.InspiredNations.Hud.Implem.Player.PlayerID;
import com.github.InspiredOne.InspiredNations.Listeners.InspiredListener;
import com.github.InspiredOne.InspiredNations.Listeners.TabManager;

public class TabListener<T extends TabManager<?>> extends InspiredListener<T> {

	public TabListener(T manager) {
		super(manager);
		
	}
	
	@EventHandler
	public void onChatTabPress(PlayerChatTabCompleteEvent event) {
		if(!this.getPlayerData().getPlayerID().equals(new PlayerID(event.getPlayer()))) {
			return;
		}
		event.getTabCompletions().clear();
		this.getManager().preTabEntry = event.getLastToken();
		Debug.print("Filtered options size is: " +((TabSelectOptionMenu<Currency>) this.getManager().getActionMenu()).filteredoptions.size());
		Debug.print("The PreTabEntry is: " + this.getManager().preTabEntry);
		this.getManager().Update();

		
	}
	
}
