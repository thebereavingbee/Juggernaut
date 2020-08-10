package me.thebereavingbee.Juggernaut;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
ArrayList<Player> joined = new ArrayList<Player>();
Player theJugg;

		@Override
		public void onEnable() {
			//getServer().getPluginManager().registerEvents(new onDeath(), this);
			
		}
		
		@Override
		public void onDisable() {
			//removeJuggernaut(theJugg);
			
		}

		
		public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			
			if (label.equalsIgnoreCase("juggernaut") || label.equalsIgnoreCase("jug")) {
				if (sender instanceof Player) {
														// player
					Player player = (Player) sender;
					if (args.length >= 1) {								//checks if command has arguments
						switch(args[0]) {
						default:
							player.sendMessage(ChatColor.RED + "Usage: /juggernaut <start|list>");
							break;
						case "start":
							if (player.hasPermission("juggernaut.start")) {
								if (joined.size() >= 1) {											//checks if the arraylist joined has at least one person
									//Player theJugg = randomPlayer((Player[])joined.toArray());
									Random r = new Random();
									int n = r.nextInt(joined.size());								//gets random int between 0 and total number of joined.
									Player theJugg = joined.get(n);									//turns that int into a player
									//player.sendMessage("Random number: " + n);					//debug code
									//player.sendMessage("joined.size() = " + joined.size());		//debug code
									player.sendMessage(ChatColor.LIGHT_PURPLE + "Starting Game!");
									createJuggernaut(theJugg);										//Makes them thicc
									break;
								}
								else {
									player.sendMessage("Nobody is on the list to play! They can join with /juggernaut");
									break;
								}
							} else {
								player.sendMessage(ChatColor.RED + "You do not have permission to start the game!");
								break;
							}
						case "list":
							if (player.hasPermission("juggernaut.list")) {									// /juggernaut list		
								player.sendMessage(ChatColor.LIGHT_PURPLE + "Current Players in game:");	// prints list of players in game
								for (int i = 0; i < joined.size(); i++) {
									player.sendMessage(joined.get(i).getName());
								}
								break;
							}
							else {
								player.sendMessage(ChatColor.RED + "You dont have permission to list players in game!");
								break;
							}
						case "clear":
							if (player.hasPermission("juggernaut.clear")) {
								joined.clear();
								break;
							}
							else {
								player.sendMessage(ChatColor.RED + "You do not have permission to clear the list!");
								break;
							}
						}
					}else {												//If command doesn't have arguments, assumes player wants to join the game
						if (player.hasPermission("juggernaut.join")) {	// add to game queue
							joined.add(player);
							player.sendMessage(ChatColor.LIGHT_PURPLE + "You have been added to the game!");
							player.sendMessage(ChatColor.LIGHT_PURPLE + "Players ready: " + String.valueOf(joined.size()));
						}
					}
				}
				else {
														// console
					
				}
			}
			return false;
		}

		public static void createJuggernaut(Player player) {		// Transform player into the Jugg
			player.sendMessage("You are thicc af");
			
			
		}
		public static void removeJuggernaut(Player player) {		// Transform player back into a normie
			player.sendMessage("You lose your chiseled jawline, normie");
			
		}
}