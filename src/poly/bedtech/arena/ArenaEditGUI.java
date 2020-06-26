package poly.bedtech.arena;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import poly.bedtech.LimaMain;

public class ArenaEditGUI implements Listener {

	
	private static final String itemName = "§c Arena Edit Tool";
	
	private final String pos1Name = "Set Pos1";
	private final String pos2Name = "Set Pos2";
	private final String infoName = "Arena info";
	private final String saveName = "Save";
	private final String loadName = "Load";
	
	private final String borderName = "Show/Hide Border";
	
	//peut etre pas une bonne idée la boussole car marche pas avec WorldEdit

	private void showUI(Player player, Arena a) {
		
		Inventory inv = Bukkit.createInventory(null, 18,a.getName());
		
		ItemStack itemPos1 = new ItemStack(Material.TORCH);
		ItemMeta itemPos1M = itemPos1.getItemMeta();
		itemPos1M.setDisplayName(pos1Name);
		itemPos1.setItemMeta(itemPos1M);
		
		ItemStack itemPos2 = new ItemStack(Material.TORCH);
		ItemMeta itemPos2M = itemPos2.getItemMeta();
		itemPos2M.setDisplayName(pos2Name);
		itemPos2.setItemMeta(itemPos2M);
		
		ItemStack itemInfo = new ItemStack(Material.OAK_SIGN);
		ItemMeta itemInfoM = itemInfo.getItemMeta();
		itemInfoM.setDisplayName(infoName);
		
		
		List<String> lore = new ArrayList<String>();
		lore.add("name:"+a.getName());
		if (a.loc1 != null) {
			int x = (int)a.loc1.getBlockX();
			int y = (int)a.loc1.getBlockY();
			int z = (int)a.loc1.getBlockZ();
			lore.add("loc1:"+x+","+y+","+z);
		}else {
			lore.add("loc1:null");
		}
		if (a.loc2 != null) {
			int x = (int)a.loc2.getBlockX();
			int y = (int)a.loc2.getBlockY();
			int z = (int)a.loc2.getBlockZ();
			lore.add("loc2:"+x+","+y+","+z);
		}else {
			lore.add("loc2:null");
		}
		lore.add("world:"+a.world.getName());
		
		
		itemInfoM.setLore(lore);
		
		itemInfo.setItemMeta(itemInfoM);
		
		
		ItemStack itemSave = new ItemStack(Material.PAPER);
		ItemMeta itemSaveM = itemSave.getItemMeta();
		itemSaveM.setDisplayName(saveName);
		itemSave.setItemMeta(itemSaveM);
		
		ItemStack itemLoad = new ItemStack(Material.BOOK);
		ItemMeta itemLoadM = itemSave.getItemMeta();
		itemLoadM.setDisplayName(loadName);
		itemLoad.setItemMeta(itemLoadM);
		
		Material borderMat = (a.borderRun != null ? Material.REDSTONE : Material.GLOWSTONE_DUST);
		ItemStack itemBorder = new ItemStack(borderMat);
		ItemMeta itemBorderM = itemBorder.getItemMeta();
		itemBorderM.setDisplayName(borderName);
		itemBorderM.setLore(Arrays.asList("wait 1 sec after remove border"));
		itemBorder.setItemMeta(itemBorderM);
		
		inv.setItem(0, itemPos1);
		inv.setItem(1, itemPos2);
		inv.setItem(2, itemInfo);
		inv.setItem(3, itemSave);
		inv.setItem(4, itemLoad);
		inv.setItem(5, itemBorder);
		
		player.openInventory(inv);
		
	}
	
	
	
	public void drawLine(
	        /* Would be your orange wool */Location point1, 
	        /* Your white wool */ Location point2,
	        /*Space between each particle*/double space
	        ) {
	 
	    World world = point1.getWorld();

	 
	    /*Distance between the two particles*/
	    double distance = point1.distance(point2);
	 
	    /* The points as vectors */
	    Vector p1 = point1.toVector();
	    Vector p2 = point2.toVector();
	 
	    /* Subtract gives you a vector between the points, we multiply by the space*/
	    Vector vector = p2.clone().subtract(p1).normalize().multiply(space);
	 
	    /*The distance covered*/
	    double covered = 0;
	 
	    /* We run this code while we haven't covered the distance, we increase the point by the space every time*/
	    for (; covered < distance; p1.add(vector)) {
	        /*Spawn the particle at the point*/
	        world.spawnParticle(Particle.REDSTONE, p1.getX(), p1.getY(), p1.getZ(), 1);
	 
	        /* We add the space covered */
	        covered += space;
	    }
	}
	

	/*
	@EventHandler
	public void onItemHeldChange(PlayerItemHeldEvent event) {
		
		Player player = (Player) event.getPlayer();
		ItemStack current = player.getInventory().getItem(event.getNewSlot());;
		
		if (current == null) return;
	
		if (current.getItemMeta().getLore() == null)
			return;
		Arena ar = ArenaManager.getArenaByName(current.getItemMeta().getLore().get(1));
		if (ar == null || ar.loc1 == null || ar.loc2 == null)
			return;
		
		showRegion(ar.loc1,ar.loc2);
		
	
	}
	*/
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		
		InventoryView invView= event.getView();
		
		Player player = (Player) event.getWhoClicked();
		ItemStack current = event.getCurrentItem();
		
		if (current == null) return;
		
		Arena ar = ArenaManager.getArenaByName(invView.getTitle());
		if (ar == null)
			return;
		
		switch (current.getItemMeta().getDisplayName()) {
			case pos1Name:
				ar.loc1 = player.getLocation();
				player.sendMessage("set pos 1");
				break;
			case pos2Name:
				ar.loc2 = player.getLocation();
				player.sendMessage("set pos 2");
				break;
			case infoName:
				break;
			case saveName:
				ArenaManager.saveArena(player, ar);
				break;
			case loadName:
				ArenaManager.loadArena(player, ar);
				break;
			case borderName:
				ar.changeBorder();
				break;
			default:
				return;
		
		
		}
		
		ar.saveConfig(LimaMain.INSTANCE);
		
		showUI(player,ar);
		
		event.setCancelled(true);
		
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Action action = event.getAction();
		ItemStack it = event.getItem();
		
		if (it == null)
			return;
		if (it.getType() == Material.COMPASS && it.getItemMeta().getDisplayName().equals(itemName)) {
			if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK ) {
				
				Arena ar = ArenaManager.getArenaByName(it.getItemMeta().getLore().get(1));
				if (ar == null) {
					player.sendMessage("arena doesn't exist anymore");
					return;
				}
				
				showUI(player,ar);
				
			}
			
		}
		
		
	}
	
	
	public static void giveEditItem(Player p, Arena a) {
		
		ItemStack customBoussole = new ItemStack(Material.COMPASS);
		ItemMeta cbMeta = customBoussole.getItemMeta();
		cbMeta.setDisplayName(itemName);
		cbMeta.setLore(Arrays.asList("Custom edit tool for arena",a.getName()));
		cbMeta.addEnchant(Enchantment.LUCK, 2, true);
		cbMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		
		customBoussole.setItemMeta(cbMeta);
		
		p.getInventory().addItem(customBoussole);
		p.updateInventory();
		
	}
	
}
