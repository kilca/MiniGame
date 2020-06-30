package poly.bedtech.weapons;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class CustomWeapon{

	//chiant a faire: autotarget / airstrike 
	//todo explosion dmg
	
	public String name;
	public WeaponType type;
	public String id;
	
	public Material material;
	
	public int maxUse;
	public int currUse;
	
	public double damage;
	
	public CustomWeapon(String name, String id) {
		this.name = name;
		this.id = id;
		if (id == null) {
			System.err.println("wrong material or inexistent");
		}
		this.material = Material.matchMaterial(id);
		if (this.material == null) {
			System.err.println("material not found for :"+name);
			this.material = Material.DIRT;
		}
	}
	
	public abstract void leftClick(PlayerInteractEvent event, Player p);
	
	public abstract void rightClick(PlayerInteractEvent event, Player p);
	
	public ItemStack getItem() {
		ItemStack item =  new ItemStack(material);
		ItemMeta itemM = item.getItemMeta();
		itemM.setDisplayName(name);
		item.setItemMeta(itemM);
		
		//todo put use in lore
		
		return item;
	}
	
	public Class<? extends Entity> getEntityByName(String s) {
		
		switch (s.toUpperCase()) {
			case "SNOWBALL":
				return Snowball.class;
			case "EGG":
				return Egg.class;
			case "ARROW":
				return Arrow.class;
			case "FIREBALL":
				return Fireball.class;
			case "WITHERSKULL":
				return WitherSkull.class;
		}
		System.err.println("incorrect entity :"+s);
		return Snowball.class;
		
	}
	
	public void explode(Entity entity, float radius) {
		
		entity.getWorld().createExplosion(entity.getLocation(), radius);
		
	}

	
	
	
}
