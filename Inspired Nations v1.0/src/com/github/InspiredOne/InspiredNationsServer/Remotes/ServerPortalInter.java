package com.github.InspiredOne.InspiredNationsServer.Remotes;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.Exceptions.PlayerOfflineException;
import com.github.InspiredOne.InspiredNationsServer.PlayerData;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.ClientID;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.IndexedMap;

public interface ServerPortalInter extends Remote {
	public void registerClient(ClientID id) throws RemoteException;
	public void unregisterClient(ClientID id) throws RemoteException;
	public void registerPlayer(PlayerID id) throws RemoteException, PlayerOfflineException;
	public PlayerDataPortal getPlayer(PlayerID id) throws RemoteException;
	public TaxTimerPortal getTaxTimer() throws RemoteException;
	public MoneyExchangePortal getExchange() throws RemoteException;
	public IndexedMap<PlayerID, PlayerData> getPlayerData() throws RemoteException;
	
}
