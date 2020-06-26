package poly.bedtech.arena;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import poly.bedtech.LimaMain;

public class ArenaEditGUI implements Listener {

	
	private static final String itemName = "§c Arena Edit Tool";
	
	private final String pos1Name = "Set Pos1";
	private final String pos2Name = "Set Pos2";
	private final String infoName = "Arena info";
	private final String saveName = "Save";
	private final String loadName = "Load";
	
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
		
		inv.setItem(0, itemPos1);
		inv.setItem(1, itemPos2);
		inv.setItem(2, itemInfo);
		inv.setItem(3, itemSave);
		inv.setItem(4, itemLoad);
		
		player.openInventory(inv);
		
	}
	
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
			default:
				return;
		
		
		}
		
		ar.saveConfig(LimaMain.INSTANCE);
		
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
