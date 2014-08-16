package com.github.InspiredOne.InspiredNationsServer.Remotes.Implem;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.Exceptions.PlayerOfflineException;
import com.github.InspiredOne.InspiredNationsServer.InspiredNationsServer;
import com.github.InspiredOne.InspiredNationsServer.Log;
import com.github.InspiredOne.InspiredNationsServer.PlayerData;
import com.github.InspiredOne.InspiredNationsServer.Remotes.MoneyExchangePortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.ServerPortalInter;
import com.github.InspiredOne.InspiredNationsServer.Remotes.TaxTimerPortal;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.ClientID;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.IndexedMap;

public class ServerPortal implements ServerPortalInter {

	@Override
	public void registerClient(ClientID id) throws RemoteException {
		InspiredNationsServer.clients.add(id);
		Log.info("Client has been registered: " + id.getName());
	}

	@Override
	public void unregisterClient(ClientID id) throws RemoteException {
		boolean contains = InspiredNationsServer.clients.remove(id);
		if(contains) {
			Log.info("Client Removed Successfully: ");
		}
		else {
			Log.info("Client Does Not Exist: " + id.getName());
		}
	}

	@Override
	public void registerPlayer(PlayerID id) throws RemoteException, PlayerOfflineException {
		if(InspiredNationsServer.playerdata.containsKey(id)) {
			return;
		}
		else {
			PlayerData player = new PlayerData(id);
			Log.info(player.getName() + " has not been added to playerdata yet.");
			InspiredNationsServer.playerdata.put(id, player);
		}
	}

	@Override
	public PlayerDataPortal getPlayer(PlayerID id) throws RemoteException {
		return InspiredNationsServer.playerdata.get(id);
	}
	
	@Override
	public IndexedMap<PlayerID, PlayerData> getPlayerData() throws RemoteException{
		return InspiredNationsServer.playerdata;
	}

	@Override
	public TaxTimerPortal getTaxTimer() throws RemoteException {
		return InspiredNationsServer.taxTimer;
	}

	@Override
	public MoneyExchangePortal getExchange() throws RemoteException {
		return InspiredNationsServer.Exchange;
	}

}
