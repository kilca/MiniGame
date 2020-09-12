package poly.bedtech.weapons;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.enchantments.Enchantment;

public class CustomMelee extends CustomWeapon{

	//public double velocity;//oui
	
	//poke = rien
	
	public double knockback;

	public List<Enchantment> enchants = new ArrayList<Enchantment>();
	public List<Integer> enchantslvl = new ArrayList<Integer>();
	
	public List<ItemFlag> flags;
	
	public CustomMelee(String localName, String name, String id) {
		super(localName,name,id);
		
		flags = new ArrayList<ItemFlag>();
		
	}
	
	public CustomMelee(String localName, String name, String id, double knockback, double damage) {
		super(localName,name,id);
		this.knockback = knockback;
		this.damage = damage;
		
		flags = new ArrayList<ItemFlag>();
	}

	public void addFlag(ItemFlag f) {
		flags.add(f);
	}
	
	@Override
	public ItemStack getItem() {
		ItemStack item = super.getItem();
		
		for(int i=0;i<enchants.size();i++) {
			Enchantment e = enchants.get(i);
			int lvl = enchantslvl.get(i);
			
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.addEnchant(e, lvl, true);
			
			for(ItemFlag f : flags) {
				itemmeta.addItemFlags(f);
			}
			
			item.setItemMeta(itemmeta);
			
		}
		//ItemMeta itemM = item.getItemMeta();
		
		//todo put use in lore
		
		return item;
	}
	
	@Override
	public void leftClick(PlayerInteractEvent event, Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rightClick(PlayerInteractEvent event, Player p) {
		// TODO Auto-generated method stub
		
	}
	
	
}
