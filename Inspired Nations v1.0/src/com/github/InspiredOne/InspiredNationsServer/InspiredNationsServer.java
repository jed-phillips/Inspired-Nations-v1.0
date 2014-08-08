package com.github.InspiredOne.InspiredNationsServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNationsClient.InspiredNationsClient;
import com.github.InspiredOne.InspiredNationsServer.Remotes.ServerPortalInter;
import com.github.InspiredOne.InspiredNationsServer.Remotes.Implem.ServerPortal;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.ClientID;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.IndexedMap;

public class InspiredNationsServer {

	public static ArrayList<ClientID> clients = new ArrayList<ClientID>();
	public static String hostname = "localhost";
	public static int port = 1099;
	
	public static IndexedMap<PlayerID, PlayerData> playerdata = new IndexedMap<PlayerID, PlayerData>();
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// Attach a shutdown hook for saving data.
		Runtime.getRuntime().addShutdownHook(new Thread() {
				  @Override
				  public void run() {
						// Saves Data
						try {
							  File theDir = new File(System.getProperty("user.dir") + "/InspiredNations");
							  // if the directory does not exist, create it
							  if (!theDir.exists()) {
								  try{
									  theDir.mkdir();
							     } catch(SecurityException se){
							     }        
							  }
							File regionfile = new File(System.getProperty("user.dir") + "/InspiredNations", "data.yml");
					        FileOutputStream regionOut = new FileOutputStream(regionfile);
					        ObjectOutputStream rout = new ObjectOutputStream(regionOut);
					        //rout.writeObject(InspiredNations.regiondata);
					        rout.writeObject(InspiredNationsServer.playerdata);
					        //rout.writeObject(InspiredNations.Exchange);
					        //rout.writeObject(InspiredNations.taxTimer);
					        rout.close();
					        regionOut.close();
						}
						catch(Exception ex) {
							ex.printStackTrace();
						}
				  }
			  });
		
		try {
            Registry registry = null;
            try {
                registry = LocateRegistry.createRegistry(port);
            } catch (RemoteException e) {
                registry = LocateRegistry.getRegistry(hostname, port);
                e.printStackTrace();
            }
            
            ServerPortalInter portal = new ServerPortal();
            ServerPortalInter stub = (ServerPortalInter) UnicastRemoteObject.exportObject(portal, 0);
            registry.rebind("portal", stub);
            Log.info("Server Portal Bound");
        } catch (Exception e) {
        	e.printStackTrace();
        }
		
		// Loads Data
		try {
			File regionfile = new File(System.getProperty("user.dir")+ "/InspiredNations", "data.yml");
	        FileInputStream regionIn = new FileInputStream(regionfile);
	        ObjectInputStream rin = new ObjectInputStream(regionIn);
	        //InspiredNations.regiondata = (MultiGovMap) rin.readObject();
	        InspiredNationsServer.playerdata = (IndexedMap<PlayerID, PlayerData>) rin.readObject();
	        //InspiredNations.Exchange = (MoneyExchange) rin.readObject();
	        //InspiredNations.taxTimer = (TaxTimer) rin.readObject();
	        rin.close();
	        regionIn.close();
		}
		catch(Exception ex) {
			
		}
		
		
		
		  int delay = 1000; //milliseconds

		  TimerTask task = new TimerTask() {

			@Override
			public void run() {
				for(ClientID client:clients) {
					client.getClientPortal();
				}				
			}
			  
		  };
		  Timer timer = new Timer();
		  timer.scheduleAtFixedRate(task, 5000, delay);
		  
	}
	
	public class TempCommandListener implements CommandExecutor {

		InspiredNationsClient plugin;
		
		public TempCommandListener(InspiredNationsClient instance) {
			plugin = instance;
		}
		
		@Override
		public boolean onCommand(CommandSender sender, Command arg1, String CommandLable,
				String[] arg3) {
			if(!(sender instanceof Player)) {
				InspiredNationsClient.logger.info("HUD cannot be called from console.");
				return false;
			}
			
			ClientPlayerData PDI = InspiredNationsClient.playerdata.get(new PlayerID((Player) sender));
			if (CommandLable.equalsIgnoreCase("hud")) {
				if(((Player) sender).isConversing()) {
					return false;
				}
				ConversationBuilder convo = new ConversationBuilder(PDI);
				Conversation conversation = convo.HudConvo();
				PDI.setCon(conversation);
				conversation.begin();
			}
			else if(CommandLable.equalsIgnoreCase("map")) {
				if(((Player) sender).isConversing()) {
					return false;
				}
				ConversationBuilder convo = new ConversationBuilder(PDI);
				Conversation conversation = convo.MapConvo();
				PDI.setCon(conversation);
				conversation.begin();
			}
			else if(CommandLable.equalsIgnoreCase("npc")) {
				for(NPC npc:PDI.npcs) {
					npc.buyOut();
				}
			}
			else return false;
			return false;
		}
		
	}

}