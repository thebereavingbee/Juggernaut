package me.thebereavingbee.Juggernaut;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
ArrayList<Player> joined = new ArrayList<Player>();
	
		@Override
		public void onEnable() {
			
			
		}
		
		@Override
		public void onDisable() {
			
			
		}
		
		
		public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			
			if (label.equalsIgnoreCase("juggernaut")) {
				if (sender instanceof Player) {
					// player
					Player player = (Player) sender;
					if (args.length >= 1) {	
						if (args[0].equalsIgnoreCase("start")) {
							if (player.hasPermission("juggernaut.start")) {
								//  start the game
								if (joined.size() >= 1) {
									//starts game if there are people in list
									Player theJugg = randomPlayer((Player[])joined.toArray());
									createJuggernaut(theJugg);
								}
								else {
									player.sendMessage("Nobody is on the list to play! They can join with /juggernaut");
								}
							}
							else {
								player.sendMessage("You do not have permission to start the game!");
							}
						}
						else if (args[0].equalsIgnoreCase("list")) {
							if (player.hasPermission("juggernaut.list")) {
								// /juggernaut list
								// prints list of players in game
								player.sendMessage("Current Players in game:");
								for (int i = 0; i < joined.size(); i++) {
									player.sendMessage(joined.get(i).getName());
								}
							}
							else {
								player.sendMessage("You dont have permission to list players in game!");
							}
						}
						else {
							player.sendMessage("Usage: /juggernaut <list|start>");
						}
					}else {
						if (player.hasPermission("juggernaut.join")) {
							// add to game queue
							joined.add(player);
							player.sendMessage("You have been added to the game!");
							player.sendMessage("Players ready: " + String.valueOf(joined.size()));
						}
					}
				}
				else {
					// console
					
				}
			}
			return false;
		}
		public static Player randomPlayer(Player[] players) {
			Random r = new Random();
			int n = r.nextInt((players.length - 1));
			
			return players[n];
			
		}
		public static void createJuggernaut(Player player) {	// Transform player into the Jugg
			player.sendMessage("You are thicc af");
			
			
		}
		public static void removeJuggernaut(Player player) {	// Transform player back into a normie
			player.sendMessage("You lose your chiseled jawline, normie");
			
		}
}

