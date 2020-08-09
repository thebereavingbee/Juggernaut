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
					if (args.length >= 1) {								//checks if command has arguments
						switch(args[0]) {
						default:
							player.sendMessage("Usage: /juggernaut <start|list>");
							break;
						case "start":
							if (player.hasPermission("juggernaut.start")) {
								if (joined.size() >= 1) {								//checks if the arraylist joined has at least one person
									Player theJugg = randomPlayer((Player[])joined.toArray());
									createJuggernaut(theJugg);
								}
								else {
									player.sendMessage("Nobody is on the list to play! They can join with /juggernaut");
								}
							} else {
								player.sendMessage("You do not have permission to start the game!");
								break;
							}
						case "list":
							if (player.hasPermission("juggernaut.list")) {		// /juggernaut list		
								player.sendMessage("Current Players in game:");	// prints list of players in game
								for (int i = 0; i < joined.size(); i++) {
									player.sendMessage(joined.get(i).getName());
								}
							}
							else {
								player.sendMessage("You dont have permission to list players in game!");
							}
						}
					}else {														//If command doesn't have arguments, assumes player wants to join the game
						if (player.hasPermission("juggernaut.join")) {	// add to game queue
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
		public static void createJuggernaut(Player player) {		// Transform player into the Jugg
			player.sendMessage("You are thicc af");
			
			
		}
		public static void removeJuggernaut(Player player) {		// Transform player back into a normie
			player.sendMessage("You lose your chiseled jawline, normie");
			
		}
}