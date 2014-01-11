package com.github.InspiredOne.InspiredNations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import org.bukkit.conversations.Conversation;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.Economy.AccountCollection;
import com.github.InspiredOne.InspiredNations.Economy.Currency;
import com.github.InspiredOne.InspiredNations.Exceptions.NotASuperGovException;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.Governments.OwnerGov;
import com.github.InspiredOne.InspiredNations.Governments.OwnerSubjectGov;
import com.github.InspiredOne.InspiredNations.ToolBox.Nameable;
import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;


public class PlayerData implements Serializable, Nameable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8628182579123244877L;
	
	private transient Conversation con;
	private String name;
	private AccountCollection accounts;
	private Currency currency;
	
	public PlayerData(PlayerID id) {
		this.setName(id.getName());
		con = null;
		currency = Currency.DEFAULT;
		accounts = new AccountCollection();
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
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	public Player getPlayer() {
		InspiredNations plugin = InspiredNations.plugin;
		System.out.println("Inside Get Player " + (plugin == null) );
		System.out.println(name);
		System.out.println(plugin.getServer());
		return plugin.getServer().getPlayer(name);
	}
	
	public boolean isSubjectOf(Class<? extends InspiredGov> govtype) {
		for(InspiredGov gov:InspiredNations.regiondata.get(govtype)) {
			if(gov.getSubjects().contains(this.getName())) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Gets all governments in which a player is a citizen. Uses the HashSet input to check.
	 * @param govType	type of government we're looking for
	 * @return	a list off all the governments in which the player is a citizen
	 * @param govType
	 * @return
	 */
	public List<OwnerSubjectGov> getCitizenship(Class<? extends InspiredGov> govType) {
		return getCitizenship(govType, InspiredNations.regiondata.get(govType));
	}
	/**
	 * Gets all governments in which a player is a citizen. Uses the HashSet input to check.
	 * @param govType	type of government we're looking for
	 * @param govDir	all the governments to check
	 * @return	a list off all the governments in which the player is a citizen
	 */
	public List<OwnerSubjectGov> getCitizenship(Class<? extends InspiredGov> govType, HashSet<? extends InspiredGov> govDir) {
		List<OwnerSubjectGov> output = new ArrayList<OwnerSubjectGov>();

		for(InspiredGov gov:govDir) {
			if(gov.getSubjects().contains(this.getName()) && gov.getClass().equals(govType)) {
				output.add((OwnerSubjectGov) gov);
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
				if(((OwnerGov) gov).getOwners().contains(this.getName())) {
					output.add((OwnerGov) gov);
				}
			}
		}
		System.out.println("In get Ownership. Size = " + output.size());
		System.out.println(govType.getName());
		
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
		System.out.println("In get AllSuperGovsBelow. Size = " + output.size());
		return output;
	}

	public AccountCollection getAccounts() {
		return accounts;
	}

	public void setAccounts(AccountCollection accounts) {
		this.accounts = accounts;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
}
