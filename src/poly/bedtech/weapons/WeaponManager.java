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

	
	//le rocket jump marche
	
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
	
	public static void loadGuns() {
		
		
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
		    int bullet_number = 1;//nombre de tirs
			double knockback = 0.0;
			float zoom = 0.0f;
			double spread = 0.0;
			
			//[!] penser a verif si cle existe
			
			id = cfg.getConfigurationSection("guns").getString(s+".id");
			projectile = cfg.getConfigurationSection("guns").getString(s+".projectile");
			
			velocity = cfg.getConfigurationSection("guns").getInt(s+".velocity");
			damage = cfg.getConfigurationSection("guns").getInt(s+".damage");
			radius = cfg.getConfigurationSection("guns").getInt(s+".radius");
			destruct = cfg.getConfigurationSection("guns").getBoolean(s+".destruct");
			bullet_number = cfg.getConfigurationSection("guns").getInt(s+".bullet_number");
			knockback = cfg.getConfigurationSection("guns").getDouble(s+".knockback");
			zoom =(float) cfg.getConfigurationSection("guns").getDouble(s+".zoom");
			spread =  cfg.getConfigurationSection("guns").getDouble(s+".spread");
			
			CustomGun temp = new CustomGun(s,id,projectile);
			
			temp.velocity = velocity;
			temp.damage = damage;
			temp.radius = radius;
			temp.destruct = destruct;
			temp.knockback = knockback;
			temp.bullet_number = bullet_number;
			temp.zoom = zoom;
			temp.spread = spread;
			
			weapons.add(temp);
		}
		
	}
	
	public static void loadMelee() {
		
		
		if (cfg.getConfigurationSection("melee") == null) {
			return;
		}
		
		for(String s : cfg.getConfigurationSection("melee").getKeys(false)) {
			String id = "";
		    double damage = 2.0;
		    double knockback = 2.0;
			
			//[!] penser a verif si cle existe
			
			id = cfg.getConfigurationSection("melee").getString(s+".id");
			damage = cfg.getConfigurationSection("melee").getInt(s+".damage");
			knockback = cfg.getConfigurationSection("melee").getInt(s+".knockback");
			
			CustomMelee temp = new CustomMelee(s,id);
			
			temp.damage = damage;
			temp.knockback = knockback;
			
			weapons.add(temp);
		}
		
	}
	
	public static void loadGrenade() {
		
		
		if (cfg.getConfigurationSection("grenade") == null) {
			return;
		}
		
		for(String s : cfg.getConfigurationSection("grenade").getKeys(false)) {
			String id = "";
		    double damage = 2.0;
		    double radius;
		    double delay;
		    boolean fire;
		    
			//[!] penser a verif si cle existe
			
			id = cfg.getConfigurationSection("grenade").getString(s+".id");
			damage = cfg.getConfigurationSection("grenade").getInt(s+".damage");
			radius = cfg.getConfigurationSection("grenade").getInt(s+".radius");
			delay = cfg.getConfigurationSection("grenade").getDouble(s+".delay");
			fire = cfg.getConfigurationSection("grenade").getBoolean(s+".fire");
			
			CustomGrenade temp = new CustomGrenade(s,id);
			
			temp.damage = damage;
			temp.radius = radius;
			temp.explosionDelay = delay;
			temp.fire = fire;
			
			weapons.add(temp);
		}
		
	}
	
	
	public static void loadWeapons(LimaMain plugin) {
		File f = new File(plugin.getDataFolder(),"weapons.yml");
		cfg = YamlConfiguration.loadConfiguration(f);
		
		loadGuns();
		loadMelee();
		loadGrenade();
		
	}
	
	
	
}
