package poly.bedtech.weapons;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.WitherSkull;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import poly.bedtech.LimaMain;

public class WeaponManager {

	
	//le rocket jump marche
	
	public static List<CustomWeapon> weapons = new ArrayList<CustomWeapon>();
	
	
	public static FileConfiguration cfg;
	
	public static CustomWeapon getWeaponByName(String s) {
		
		for(CustomWeapon w : weapons) {
			if (w.localizedName.equals(s))
				return w;
			
		}
		return null;
	}
	
	public static ItemStack getItemByName(String s) {
		
		for(CustomWeapon w : weapons) {
			if (w.localizedName.equals(s))
				return w.getItem();
			
		}
		return null;
		
	}
	
	public static CustomWeapon getWeaponByNameMaterial(String s, Material m) {
		
		for(CustomWeapon w : weapons) {
			if (w.localizedName.equals(s) && w.material.equals(m))
				return w;
			
		}
		return null;
		
	}
	
	public static void showItemList(Player p) {
		
		for(int i=0;i<WeaponManager.weapons.size();i++) {
			
			p.sendMessage(i+")"+WeaponManager.weapons.get(i).localizedName);
			
		}
		
	}
	
	public static void loadGuns() {
		
		
		if (cfg.getConfigurationSection("guns") == null) {
			return;
		}
		
		for(String s : cfg.getConfigurationSection("guns").getKeys(false)) {
			
			String name = s;
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
			
			if (cfg.getConfigurationSection("guns").contains(s+".name")) {
				name = cfg.getConfigurationSection("guns").getString(s+".name");
			}
			
			projectile = cfg.getConfigurationSection("guns").getString(s+".projectile");
			
			velocity = cfg.getConfigurationSection("guns").getInt(s+".velocity");
			damage = cfg.getConfigurationSection("guns").getInt(s+".damage");
			radius = cfg.getConfigurationSection("guns").getInt(s+".radius");
			destruct = cfg.getConfigurationSection("guns").getBoolean(s+".destruct");
			bullet_number = cfg.getConfigurationSection("guns").getInt(s+".bullet_number");
			knockback = cfg.getConfigurationSection("guns").getDouble(s+".knockback");
			zoom =(float) cfg.getConfigurationSection("guns").getDouble(s+".zoom");
			spread =  cfg.getConfigurationSection("guns").getDouble(s+".spread");
			
			CustomGun temp = new CustomGun(s,name,id,projectile);
			
            for(String lore : cfg.getConfigurationSection("melee").getStringList(s+".lore")) {
        		temp.addLore(lore);
            }
			
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
			
			String name = s;
			String id = "";
		    double damage = 2.0;
		    double knockback = 2.0;
			
			//[!] penser a verif si cle existe
			if (cfg.getConfigurationSection("melee").contains(s+".name")) {
				name = cfg.getConfigurationSection("melee").getString(s+".name");
			}
			
			id = cfg.getConfigurationSection("melee").getString(s+".id");
			
			damage = cfg.getConfigurationSection("melee").getInt(s+".damage");
			knockback = cfg.getConfigurationSection("melee").getInt(s+".knockback");
			
			CustomMelee temp = new CustomMelee(s,name,id);
			
			temp.damage = damage;
			temp.knockback = knockback;
			
			System.out.println("load melee");
			
            for(String lore : cfg.getConfigurationSection("melee").getStringList(s+".lore")) {
            		temp.addLore(lore);
            }
			
            
            for(String enchant : cfg.getConfigurationSection("melee").getStringList(s+".enchantments")) {
               
            	
            	String enchantname = enchant.split(":")[0];
                Integer enchantlvl = Integer.valueOf(enchant.split(":")[1]);
                //Enchantment e = Enchantment.getByName(enchantname);
                Enchantment e = Enchantment.getByKey(NamespacedKey.minecraft(enchantname.toLowerCase()));
                if (e == null)
                	continue;
                temp.enchants.add(e);
                temp.enchantslvl.add(enchantlvl);
                //itemmeta.addEnchant(Enchantment.getByName(enchantname), enchantlvl, true);
           }
            
           for(String flag : cfg.getConfigurationSection("melee").getStringList(s+".flags")) {
               
        	   	ItemFlag f = ItemFlag.valueOf(flag);
        	   	if (f == null)
        	   		continue;
        	   	
                temp.addFlag(f);
           }
			
			weapons.add(temp);
		}
		
	}

	
	public static void loadWeapons(LimaMain plugin) {
		File f = new File(plugin.getDataFolder(),"weapons.yml");
		cfg = YamlConfiguration.loadConfiguration(f);
		
		loadGuns();
		loadMelee();

	}
	
	
	
}
