package com.github.InspiredOne.InspiredNationsServer.Remotes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.rmi.Remote;
import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.ToolBox.Nameable;
import com.github.InspiredOne.InspiredNationsServer.Economy.Payable;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.BalanceOutOfBoundsException;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.NameAlreadyTakenException;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.NegativeMoneyTransferException;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;

public interface CurrencyAccountPortal extends Remote, Payable, Nameable, Serializable {

	public CurrencyPortal getCurrency() throws RemoteException;
	public void setCurrency(CurrencyPortal curren) throws RemoteException;
	public String getName() throws RemoteException;
	public void setName(String name) throws NameAlreadyTakenException, RemoteException;
	public String getDisplayName(PlayerID viewerID) throws RemoteException;
	public void transferMoney(BigDecimal amountTake, CurrencyPortal monType,
			Payable target) throws BalanceOutOfBoundsException,
			NegativeMoneyTransferException, RemoteException;
	public void addMoney(BigDecimal amountGive, CurrencyPortal monType)
			throws NegativeMoneyTransferException, RemoteException;
	public BigDecimal getTotalMoney(CurrencyPortal valueType, MathContext round) throws RemoteException;
	public void sendNotification(AlertPortal msg) throws RemoteException;
	public int getID() throws RemoteException;
	
}
