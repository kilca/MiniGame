package poly.bedtech.weapons;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.WitherSkull;
import org.bukkit.inventory.ItemStack;

import poly.bedtech.LimaMain;

public class WeaponManager {

	
	public static List<CustomWeapon> weapons = new ArrayList<CustomWeapon>();
	
	
	public static FileConfiguration cfg;
	
	public static CustomWeapon getWeaponByName(String s) {
		
		for(CustomWeapon w : weapons) {
			if (w.name.equals(s))
				return w;
			
		}
		return null;
	}
	
	public static ItemStack getItemByName(String s) {
		
		for(CustomWeapon w : weapons) {
			if (w.name.equals(s))
				return w.getItem();
			
		}
		return null;
		
	}
	
	public static CustomWeapon getWeaponByNameMaterial(String s, Material m) {
		
		for(CustomWeapon w : weapons) {
			if (w.name.equals(s) && w.material.equals(m))
				return w;
			
		}
		return null;
		
	}
	
	public static void showItemList(Player p) {
		
		for(int i=0;i<WeaponManager.weapons.size();i++) {
			
			p.sendMessage(i+")"+WeaponManager.weapons.get(i).name);
			
		}
		
	}
	
	public static void loadWeapons(LimaMain plugin) {
		File f = new File(plugin.getDataFolder(),"weapons.yml");
		cfg = YamlConfiguration.loadConfiguration(f);
		
		
		if (cfg.getConfigurationSection("guns") == null) {
			return;
		}
		
		for(String s : cfg.getConfigurationSection("guns").getKeys(false)) {
			String id = "";
			String projectile = "";
		    double velocity = 2.0;
		    double damage = 2.0;
		    double radius = 2.0;
		    boolean destruct = false;		
		    int fragDispersion = 1;//nombre de tirs
			double knockback = 0.0;
			
			//penser a verif si cle existe
			
			id = cfg.getConfigurationSection("guns").getString(s+".id");
			projectile = cfg.getConfigurationSection("guns").getString(s+".projectile");
			
			velocity = cfg.getConfigurationSection("guns").getInt(s+".velocity");
			damage = cfg.getConfigurationSection("guns").getInt(s+".damage");
			radius = cfg.getConfigurationSection("guns").getInt(s+".radius");
			destruct = cfg.getConfigurationSection("guns").getBoolean(s+".destruct");
			fragDispersion = cfg.getConfigurationSection("guns").getInt(s+".fragDispersion");
			knockback = cfg.getConfigurationSection("guns").getDouble(s+".knockback");
			
			CustomGun temp = new CustomGun(s,id,projectile);
			
			temp.velocity = velocity;
			temp.damage = damage;
			temp.radius = radius;
			temp.destruct = destruct;
			temp.knockback = knockback;
			temp.fragDispersion = fragDispersion;
			
			weapons.add(temp);
		}
		
	}
	
	
	
}
