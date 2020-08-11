package me.thebereavingbee.Juggernaut;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
ArrayList<Player> joined = new ArrayList<Player>();
Player theJugg;
private Map<Player, String> jugMap = new HashMap<>();

		@Override
		public void onEnable() {
			this.getServer().getPluginManager().registerEvents(this, this);
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
							player.sendMessage(ChatColor.RED + "Usage: /juggernaut <start|list|clear>");
							break;
						case "start":
							if (player.hasPermission("juggernaut.start")) {
								if (joined.size() >= 1) {											//checks if the arraylist joined has at least one person
									//Player theJugg = randomPlayer((Player[])joined.toArray());
									
									int n = createRandom(joined.size());							//gets random int between 0 and total number of joined.
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

		public void createJuggernaut(Player player) {		// Transform player into the Jugg
			jugMap.put(player, "The Jugg");
			player.sendMessage("You are thicc af");
			
			PlayerInventory inventory = player.getInventory();
			if (inventory.firstEmpty() == -1) {
				//inventory is full
				player.sendMessage(ChatColor.ITALIC + "You try to put on the jugg armor, but apparently it doesnt fit over you current armor!");
			}
			inventory.clear();
			inventory.setArmorContents(juggItems());		//gives player the armor
		}
		
		public static void removeJuggernaut(Player player) {		// Transform player back into a normie
			player.sendMessage("You lose your chiseled jawline, normie");
			player.getInventory().clear();
		}
		
		public static int createRandom(int max) {
			Random r = new Random();
			int n = r.nextInt(max);
			return n;
		}
		
		public ItemStack[] juggItems() {
			List<ItemStack> items = new ArrayList<ItemStack>();
			
				// Boots lore and data
			ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
			ItemMeta meta = boots.getItemMeta();
			
			meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Juggernaut Boots");
			List<String> lore = new ArrayList<String>();
			lore.add("");
			lore.add(ChatColor.GOLD + "" + ChatColor.ITALIC + "Some say the boots of a true"); 
			lore.add(ChatColor.GOLD + "" + ChatColor.ITALIC + "Juggernaut never stop running.");
			meta.setLore(lore);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			meta.setUnbreakable(true);
			
			boots.setItemMeta(meta);
			
			items.add(boots);
			
				// Pants lore and data
			ItemStack pants = new ItemStack(Material.DIAMOND_LEGGINGS);
			ItemMeta pantsMeta = pants.getItemMeta();
			
			pantsMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Juggernaut Pants");
			lore.clear();
			lore.add("");
			lore.add(ChatColor.GOLD + "" + ChatColor.ITALIC + "The pants of a Juggernaut are");
			lore.add(ChatColor.GOLD + "" + ChatColor.ITALIC + "slick to quickly evade danger.");
			pantsMeta.setLore(lore);
			pantsMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			pantsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			pantsMeta.setUnbreakable(true);
			
			pants.setItemMeta(pantsMeta);
			
			items.add(pants);
			
				// Chest Piece lore and data
			ItemStack chest = new ItemStack(Material.DIAMOND_CHESTPLATE);
			ItemMeta chestMeta = chest.getItemMeta();
			
			chestMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Juggernaut ChestPiece");
			lore.clear();
			lore.add("");
			lore.add(ChatColor.GOLD + "" + ChatColor.ITALIC + "Emblazened with the sigil of the Juggernauts!");
			chestMeta.setLore(lore);
			chestMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			chestMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			chestMeta.setUnbreakable(true);
			
			chest.setItemMeta(chestMeta);
			
			items.add(chest);

				// Helmet lore and data
			ItemStack helm = new ItemStack(Material.DIAMOND_HELMET);
			ItemMeta helmMeta = pants.getItemMeta();
			
			helmMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Juggernaut Helmet");
			lore.clear();
			lore.add("");
			lore.add(ChatColor.GOLD + "" + ChatColor.ITALIC + "The helmet of a Juggernaut is ");
			lore.add(ChatColor.GOLD + "" + ChatColor.ITALIC + "able to punch through any advisary.");
			helmMeta.setLore(lore);
			helmMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			helmMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			helmMeta.setUnbreakable(true);
			
			helm.setItemMeta(helmMeta);
			
			items.add(helm);
			
			return items.toArray(new ItemStack[items.size()]);
		}
		
		@EventHandler
		public void onDeath(PlayerDeathEvent e) {
			
			
			if(e.getEntity().getKiller() == null) {
				return;	//player was killed by mob/environment, skip changing the Jugg
			}
						
			Player player = e.getEntity();		//player was killed by another player (killer)
			Player killer = player.getKiller();
			
			if (jugMap.get(player) == "The Jugg") {
				Bukkit.broadcastMessage(ChatColor.RED + "The Jugg has been killed by " + killer.getName() + "!");
				jugMap.put(killer, "The Jugg");
				createJuggernaut(killer);
				jugMap.remove(player, "The Jugg");
				removeJuggernaut(player);
			}
			else if (jugMap.get(killer) == "The Jugg") {
				Bukkit.broadcastMessage(ChatColor.RED + "The Jugg Has Killed!");
			}
		}
}








