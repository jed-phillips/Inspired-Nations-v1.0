package com.github.InspiredOne.InspiredNations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.conversations.Conversation;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.Economy.AccountCollection;
import com.github.InspiredOne.InspiredNations.Economy.Currency;
import com.github.InspiredOne.InspiredNations.Exceptions.NotASuperGovException;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.Governments.OwnerGov;
import com.github.InspiredOne.InspiredNations.Governments.OwnerSubjectGov;
import com.github.InspiredOne.InspiredNations.ToolBox.Alert;
import com.github.InspiredOne.InspiredNations.ToolBox.Nameable;
import com.github.InspiredOne.InspiredNations.ToolBox.Notifyable;
import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;


public class PlayerData implements Serializable, Nameable, Notifyable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8628182579123244877L;
	
	private transient Conversation con;
	private String name;
	private AccountCollection accounts;
	private Currency currency;
	private MessageManager msg;
	protected PlayerData PDI;
	
	public PlayerData(PlayerID id) {
		this.name = id.getName();
		con = null;
		currency = Currency.DEFAULT;
		accounts = new AccountCollection(this.name);
		msg = new MessageManager(this);
		PDI = this;
	}

	public Conversation getCon() {
		return con;
	}
	
	public void setCon(Conversation con) {
		this.con = con;
	}
	@Override
	public String getName() {
		return name;
	}
	
	public Player getPlayer() {
		InspiredNations plugin = InspiredNations.plugin;
		return plugin.getServer().getPlayer(name);
	}
	
	public PlayerID getPlayerID() {
		return new PlayerID(this.getPlayer());
	}
	
	public boolean isSubjectOf(Class<? extends InspiredGov> govtype) {
		for(InspiredGov gov:InspiredNations.regiondata.get(govtype)) {
			if(gov.isSubject(this.getPlayerID())) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Gets all the governemnts that this player has applied ownership.
	 * @return
	 */
	public List<OwnerGov> getAllOwnerApplications() {
		List<OwnerGov> output = new ArrayList<OwnerGov>();
		for(InspiredGov gov:InspiredNations.regiondata) {
			if(gov instanceof OwnerGov) {
				if(((OwnerGov) gov).getOwnerRequests().contains(this.getPlayerID())) {
					output.add((OwnerGov) gov);
				}
			}
		}
		return output;
	}
	/**
	 * Gets all the governemnts that this player has been offered ownership.
	 * @return
	 */
	public List<OwnerGov> getAllOwnerOffers() {
		List<OwnerGov> output = new ArrayList<OwnerGov>();
		for(InspiredGov gov:InspiredNations.regiondata) {
			if(gov instanceof OwnerGov) {
				if(((OwnerGov) gov).getOwnerOffers().contains(this.getPlayerID())) {
					output.add((OwnerGov) gov);
				}
			}
		}
		return output;
	}
	/**
	 * Gets all the government that this player has applied subject
	 * @return
	 */
	public List<OwnerSubjectGov> getAllResidenceApplications() {
		List<OwnerSubjectGov> output = new ArrayList<OwnerSubjectGov>();
		for(InspiredGov gov:InspiredNations.regiondata) {
			if(gov instanceof OwnerSubjectGov) {
				if(((OwnerSubjectGov) gov).getSubjectRequests().contains(this.getPlayerID())) {
					output.add((OwnerSubjectGov) gov);
				}
			}
		}
		return output;
	}
	/**
	 * Gets all the governments that this player has been offered citizenship
	 * @return
	 */
	public List<OwnerSubjectGov> getAllResidenceOffers() {
		List<OwnerSubjectGov> output = new ArrayList<OwnerSubjectGov>();
		for(InspiredGov gov:InspiredNations.regiondata) {
			if(gov instanceof OwnerSubjectGov) {
				if(((OwnerSubjectGov) gov).getSubjectOffers().contains(this.getPlayerID())) {
					output.add((OwnerSubjectGov) gov);
				}
			}
		}
		return output;
	}
	/**
	 * A function that stops all the listeners of the player
	 * @param player
	 */
	public static void unRegister(PlayerID player) {
		if(InspiredNations.playerdata.get(player).getCon() != null) {
			InspiredNations.playerdata.get(player).getCon().acceptInput("exit");
			InspiredNations.playerdata.get(player).getCon().abandon();
			InspiredNations.playerdata.get(player).setCon(null);
		}
	}

	
	/**
	 * Gets all governments in which a player is a citizen. Uses the HashSet input to check.
	 * @param class1	type of government we're looking for
	 * @return	a list off all the governments in which the player is a citizen
	 * @param class1
	 * @return
	 */
	public List<InspiredGov> getCitizenship(Class<? extends InspiredGov> class1) {
		return getCitizenship(class1, InspiredNations.regiondata.get(class1));
	}
	/**
	 * Gets all governments in which a player is a citizen. Uses the HashSet input to check.
	 * @param govType	type of government we're looking for
	 * @param govDir	all the governments to check
	 * @return	a list off all the governments in which the player is a citizen
	 */
	public List<InspiredGov> getCitizenship(Class<? extends InspiredGov> govType, HashSet<? extends InspiredGov> govDir) {
		List<InspiredGov> output = new ArrayList<InspiredGov>();

		for(InspiredGov gov:govDir) {
			if(gov.isSubject(this.getPlayerID()) && gov.getClass().equals(govType)) {
				output.add(gov);
			}
		}
		return output;
	}
	/**
	 * Returns a list of all the governments of type <code>govType</code> that
	 * this player owns.
	 * @param govType	The type of the government to check
	 * @return	a list of all the government of the type owned
	 */
	public List<OwnerGov> getOwnership(Class<? extends InspiredGov> govType) {
		List<OwnerGov> output = new ArrayList<OwnerGov>();
		for(InspiredGov gov:InspiredNations.regiondata.get(govType)) {
			if(gov instanceof OwnerGov) {
				gov = (OwnerGov) gov;
				if(((OwnerGov) gov).isOwner(this.getPlayerID())) {

					output.add((OwnerGov) gov);
				}
			}
		}
		
		return output;
	}
	/**
	 * 
	 * @param govbot
	 * @param govtop
	 * @return
	 */
	public final LinkedHashSet<OwnerGov> getAllSuperGovsBelow(Class<? extends InspiredGov> govbot, InspiredGov govtop) {
		LinkedHashSet<OwnerGov> output = new LinkedHashSet<OwnerGov>();
		for(OwnerGov govbottom:this.getOwnership(govbot)) {
			try {
				output.add(govbottom.getSuperGovBelow(govtop));
			} catch (NotASuperGovException e) {
				e.printStackTrace();
			}
		}
		return output;
	}

	public AccountCollection getAccounts() {
		return accounts;
	}

	public void setAccounts(AccountCollection accounts) {
		if(this.accounts.isLinked()) {
			// TODO I have to figure out how to deal with linked account collections.
		}
		this.accounts = accounts;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public MessageManager getMsg() {
		return msg;
	}

	@Override
	public void setName(String name) {
		
	}
	
	@Override
	public String getDisplayName(PlayerData PDI) {
		//TODO make this name the one used for messages and everything.
		return this.getName();
	}

	@Override
	public void sendNotification(Alert msg) {
		this.getMsg().receiveAlert(msg);
	}
	
	// Methods used to check if the player has sufficient privilages
	public boolean breakPlace(Location block) {
		boolean allowed = false;
		ArrayList<InspiredGov> isin = new ArrayList<InspiredGov>();
		for(InspiredGov gov:InspiredNations.regiondata) {
			if(gov.contains(block)) {
				isin.add(gov);
			}
		}
		for(InspiredGov gov:isin) {
			if(!gov.isSubject(this.getPlayerID())) {
				
			}
		}
		return allowed;
	}

}
