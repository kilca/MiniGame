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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class CustomWeapon{

	//chiant a faire autotarget / airstrike 
	
	public String name;
	public WeaponType type;
	public String id;
	
	public Material material;
	
	
	public CustomWeapon(String name, String id) {
		this.name = name;
		this.id = id;
		this.material = Material.matchMaterial(id);
		if (this.material == null) {
			System.err.println("material not found for :"+name);
			this.material = Material.DIRT;
		}
	}
	
	public abstract void leftClick(Player p);
	
	public ItemStack getItem() {
		ItemStack item =  new ItemStack(material);
		ItemMeta itemM = item.getItemMeta();
		itemM.setDisplayName(name);
		item.setItemMeta(itemM);
		
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
	
	
	
}
