package com.github.InspiredOne.InspiredNations.Listeners.Implem;

import org.bukkit.block.Block;

import com.github.InspiredOne.InspiredNations.Exceptions.BlockNotChestException;
import com.github.InspiredOne.InspiredNations.Hud.Implem.ClaimAndUnclaimLand.ClaimChestShop;
import com.github.InspiredOne.InspiredNations.Listeners.ActionManager;
import com.github.InspiredOne.InspiredNations.Regions.Implem.ShopRegion;

public class ClaimChestShopManager extends ActionManager<ClaimChestShop> {

	Block block;
	public ShopRegion region = new ShopRegion();
	
	public ClaimChestShopManager(ClaimChestShop menu) {
		super(menu);
		this.listeners.add(new ClaimChestShopListener(this));
	}
	
	public void addBlock(Block block) throws BlockNotChestException {
		region.addChest(block);
	}

}
