package poly.bedtech.weapons;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomMelee extends CustomWeapon{

	//public double velocity;//oui
	
	//poke = rien
	
	public double knockback;

	public CustomMelee(String name, String id) {
		super(name,id);
		
	}
	
	public CustomMelee(String name, String id, double knockback, double damage) {
		super(name,id);
		this.knockback = knockback;
		this.damage = damage;
	}

	@Override
	public ItemStack getItem() {
		ItemStack item = super.getItem();
		
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
