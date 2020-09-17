package poly.bedtech.arena;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_15_R1.EntityFox.i;
import poly.bedtech.LimaMain;
import poly.bedtech.StructureAPI;
import poly.bedtech.weapons.CustomWeapon;
import poly.bedtech.weapons.WeaponManager;

public class ArenaManager {

	//singleton ?
	
	private static final int maxArenaDist = 100;
	
	private static List<Arena> arenas = new ArrayList<Arena>();
	
	//A Tester
	public static void setupConfigs(LimaMain lima) {
		
		System.out.println("We setup the config");
		
		if (lima.getConfig().getConfigurationSection("arenas") == null) {
			return;
		}
		
		for(String s : lima.getConfig().getConfigurationSection("arenas").getKeys(false)) {
			
			String name = s;
			World world;
			Location l1 = null;
			Location l2 = null;
			
			world = Bukkit.getServer().getWorld(lima.getConfig().getConfigurationSection("arenas").getString(s+".world"));
			
			
			boolean haveLoc1 = lima.getConfig().getConfigurationSection("arenas").isSet(s+".loc1.x");
			boolean haveLoc2 = lima.getConfig().getConfigurationSection("arenas").isSet(s+".loc2.x");
			boolean haveSpecLoc = lima.getConfig().getConfigurationSection("arenas").isSet(s+".specLoc.x");
			
			if (haveLoc1) {
				double loc1x = lima.getConfig().getConfigurationSection("arenas").getDouble(s+".loc1.x");
				double loc1y = lima.getConfig().getConfigurationSection("arenas").getDouble(s+".loc1.y");
				double loc1z = lima.getConfig().getConfigurationSection("arenas").getDouble(s+".loc1.z");
				
				l1 = new Location(world,loc1x,loc1y,loc1z);
			}
			if (haveLoc2) {
				double loc2x = lima.getConfig().getConfigurationSection("arenas").getDouble(s+".loc2.x");
				double loc2y = lima.getConfig().getConfigurationSection("arenas").getDouble(s+".loc2.y");
				double loc2z = lima.getConfig().getConfigurationSection("arenas").getDouble(s+".loc2.z");
				
				l2 = new Location(world,loc2x,loc2y,loc2z);
			}
			
			Arena ar = new Arena(name,l1,l2,world);
			
			Location specLoc = null;
			int minPlayer = lima.getConfig().getConfigurationSection("arenas").getInt(s+".minPlayer");
			int maxPlayer = lima.getConfig().getConfigurationSection("arenas").getInt(s+".maxPlayer");
			String weaponName = lima.getConfig().getConfigurationSection("arenas").getString(s+".weapon");
			boolean isOpen = lima.getConfig().getConfigurationSection("arenas").getBoolean(s+".isOpen");
			CustomWeapon weapon = WeaponManager.getWeaponByName(weaponName);
			
			
			if (haveSpecLoc) {
				double loc2x = lima.getConfig().getConfigurationSection("arenas").getDouble(s+".specLoc.x");
				double loc2y = lima.getConfig().getConfigurationSection("arenas").getDouble(s+".specLoc.y");
				double loc2z = lima.getConfig().getConfigurationSection("arenas").getDouble(s+".specLoc.z");
				
				specLoc = new Location(world,loc2x,loc2y,loc2z);
			}
			
			//marche pas
			List<Location> spawnLocs = new ArrayList<Location>();
			if (lima.getConfig().getConfigurationSection("arenas."+s+".spawnlocs") != null) {
				for(String s2 : lima.getConfig().getConfigurationSection("arenas."+s+".spawnlocs").getKeys(false))
				{
					double x = lima.getConfig().getConfigurationSection("arenas."+s+".spawnlocs").getDouble(s2+".x");
					double y = lima.getConfig().getConfigurationSection("arenas."+s+".spawnlocs").getDouble(s2+".y");
					double z = lima.getConfig().getConfigurationSection("arenas."+s+".spawnlocs").getDouble(s2+".z");
					
					
					spawnLocs.add(new Location(world,x,y,z));	
				}
			}
			
			

			
			
			ar.spawnLocs = spawnLocs;
			ar.specLoc = specLoc;
			ar.minPlayer = minPlayer;
			ar.maxPlayer = maxPlayer;
			ar.weapon = weapon;
			ar.isOpen = isOpen;
			
			addArena(ar);
			
		}
		
		
	}
	
	public static Arena getArenaByName(String name) {
		
		for(Arena a : arenas) {
			if (a.getName().equals(name)) {
				return a;
			}
			
		}
		
		return null;
		
	}
	
	public static void giveList(CommandSender sender) {
		
		for(int i=0;i<arenas.size();i++) {
			sender.sendMessage(i+")"+arenas.get(i).getName());
			
		}
		
	}
	
	public static void saveArena(Player p, Arena ar) {
		
		if (ar.loc1 == null || ar.loc2 == null)
			p.sendMessage("Error, location not defined");
		
		if (ar.loc1.distance(ar.loc2) > maxArenaDist) {
			p.sendMessage("Error, location distance too high");
		}
		
		Location l1 = ar.loc1;
		Location l2 = ar.loc2;
		
		Material[][][] m = StructureAPI.getStructureByLocation(l1, l2);
		StructureAPI.save(ar.name, m);
		
		p.sendMessage("arena saved");
		
	}
	
	public static void loadArena(Player p, Arena ar) {
		
		if (ar.loc1 == null || ar.loc2 == null)
			p.sendMessage("Error, location not defined");
		
		Material[][][] m = StructureAPI.load(ar.name);
		
		if (m == null) {
			p.sendMessage("schematic error");
		}
		
		Location block = ar.loc1;
		Location block2= ar.loc2;
		
        double minX = block.getX() < block2.getX() ? block.getX() : block2.getX();
        double minZ = block.getZ() < block2.getZ() ? block.getZ() : block2.getZ();
        double minY = block.getY() < block2.getY() ? block.getY() : block2.getY();
		
		
        Location tempLocation = new Location(ar.loc1.getWorld(),minX,minY,minZ);
        
		StructureAPI.paste(m,tempLocation);
		p.sendMessage("arena loaded");
	}
	
	
	public static boolean addArena(Arena arena) {
		for(Arena a : arenas) {
			if (a.name.equals(arena.name)) {
				return false;
			}
			
		}
		
		arena.saveConfig(LimaMain.INSTANCE);
		
		arenas.add(arena);
		return true;
	}
	
	public static Arena getArena(Player p) {
		for(Arena a : arenas) {
			if (a.players.contains(p))
				return a;
		}
		return null;
	}
	
	public static void leaveArena(Player p) {
		for(Arena a : arenas) {
			if (a.players.contains(p))
				a.leaveArena(p);
		}
		
	}
	
	
	

	
}
