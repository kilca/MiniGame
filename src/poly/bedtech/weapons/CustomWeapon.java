package poly.bedtech.weapons;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
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
	
	private static int staticCount = 0;
	private int weaponIndex;
	
	public String localizedName;//name for localize
	public String name;//shown name
	
	public WeaponType type;
	public String id;
	
	public Material material;
	
	public int maxUse;
	public int currUse;
	
	public double damage;
	
	public List<String> lore;
	
	//get index in WeaponManager Array
	public int getWeaponIndex() {
		return weaponIndex;
	}
	
	public CustomWeapon(String localizedName, String name, String id) {
		weaponIndex = staticCount;
		staticCount++;
		
		this.localizedName = localizedName;
		this.name = name;
		this.id = id;
		
		lore = new ArrayList<String>();
		
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
	
    private String translateColor(String s){
    	return ChatColor.translateAlternateColorCodes('&',s);
    }
    
	public void addLore(String s) {
		lore.add(translateColor(s));
	}
	
	public ItemStack getItem() {
		
		
		ItemStack item =  new ItemStack(material);
		ItemMeta itemM = item.getItemMeta();
		itemM.setDisplayName(translateColor(name));
		itemM.setLocalizedName(localizedName);
		if (!lore.isEmpty())
			itemM.setLore(lore);
		
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
	public void explode(Location l, float radius) {
		l.getWorld().createExplosion(l, radius);
		
	}
	public void explode(Entity entity, float radius) {
		
		entity.getWorld().createExplosion(entity.getLocation(), radius);
		
	}

	
	
	
}
