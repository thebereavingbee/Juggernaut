package me.thebereavingbee.Juggernaut;

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
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

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
							player.sendMessage(ChatColor.RED + "Usage: /juggernaut <start|list|clear|attacker>");
							break;
						case "start":
							if (player.hasPermission("juggernaut.start")) {
								if (joined.size() >= 1) {											//checks if the arraylist joined has at least one person
									//Player theJugg = randomPlayer((Player[])joined.toArray());
									
									int n = createRandom(joined.size());							//gets random int between 0 and total number of joined.
									Player theJugg = joined.get(n);									//turns that int into a player
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
						case "stop":
							if (player.hasPermission("juggernaut.stop")) {
								for (int i = 0; i < joined.size(); i++) {
									joined.get(i).getInventory().clear();
									joined.get(i).sendMessage(ChatColor.LIGHT_PURPLE + "The game has ended!");
									joined.get(i).sendMessage(ChatColor.LIGHT_PURPLE + "Thank you for playing!");
								}
								joined.clear();
							}
							else {
								player.sendMessage("You cant stop the game!");
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
						case "attacker":												//pretty much only used for debugging
							if (player.hasPermission("juggernaut.attackeritems")) {	
								player.getInventory().clear();
								player.getInventory().setContents(attackerItems());		//this just gives you the attacker items
								break;
							}
							else {
								player.sendMessage(ChatColor.RED + "You do not have permission to pull attacker items.");
								break;
							}
						}
					}else {												//If command doesn't have arguments, assumes player wants to join the game
						if (player.hasPermission("juggernaut.join")) {	// add to game queue
							for (int i = 0; i < joined.size(); i++) {
								if (joined.get(i) == player) {
									player.sendMessage(ChatColor.RED + "You are already on the list!");
									return false;
								}
							}
							
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
			
			
			player.getInventory().clear();
			player.getInventory().setArmorContents(juggItems());		//gives player the armor
			player.getWorld().strikeLightning(player.getLocation());	//strikes jugg with lightning for drama
		}
		
		public void removeJuggernaut(Player player) {		// Transform player back into a normie
			jugMap.remove(player, "The Jugg");
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
		
		public ItemStack[] attackerItems() {
			List<ItemStack> items = new ArrayList<ItemStack>();		//the items the attackers will get
			List<String> lore = new ArrayList<String>();
			
			ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
			ItemMeta swordMeta = sword.getItemMeta();
			swordMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Attack the Jugg!");
			lore.clear();
			lore.add("");
			lore.add(ChatColor.GOLD + "" + ChatColor.ITALIC + "Why are you reading this? Go attack the jugg!");
			swordMeta.setLore(lore);
			swordMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			swordMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			swordMeta.setUnbreakable(true);
			sword.setItemMeta(swordMeta);
			
			items.add(sword);
			
			ItemStack potion = new ItemStack(Material.SPLASH_POTION, 1);
			PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();
			potionMeta.setBasePotionData(new PotionData(PotionType.WEAKNESS));
			potion.setItemMeta(potionMeta);
			
			items.add(potion);
			
			return items.toArray(new ItemStack[items.size()]);
		}
		
		@EventHandler
		public void onDeath(PlayerDeathEvent e) {
			
			Player player = e.getEntity();
			
			if(e.getEntity().getKiller() == null) {
				if (jugMap.get(player) == "The Jugg") {
					int n = createRandom(joined.size());	//the jugg was killed by the environment, picks random player to be the jugg
					Player theJugg = joined.get(n);
					createJuggernaut(theJugg);
					return;
				}
				return;	//player was killed by mob/environment and isn't the jugg so nothing special happens
			}
			Player killer = player.getKiller();
			
			if (jugMap.get(player) == "The Jugg") {
				Bukkit.broadcastMessage(ChatColor.RED + "The Jugg has been killed by " + killer.getName() + "!");
				jugMap.put(killer, "The Jugg");
				createJuggernaut(killer);
				jugMap.remove(player, "The Jugg");
				removeJuggernaut(player);
				return;
			}
			else if (jugMap.get(killer) == "The Jugg") {
				Bukkit.broadcastMessage(ChatColor.RED + "The Jugg Has Killed!");
				return;
			}
		}
		
		@EventHandler
		public void onRespawn(PlayerRespawnEvent e) {
			Player player = e.getPlayer();
			for (int i = 0; i < joined.size(); i++) {
				if (joined.get(i) == player) {		//if the player is on the list of people playing jugg
					player.getInventory().setContents(attackerItems());	//give them attacker items
					return;	
				}
			}
		}
}








