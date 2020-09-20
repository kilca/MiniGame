package poly.bedtech.arena;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import poly.bedtech.MinGame;
import poly.bedtech.StructureAPI;
import poly.bedtech.weapons.CustomWeapon;
import poly.bedtech.weapons.WeaponManager;

public class ArenaManager {

	//singleton ?
	
	private static final int maxArenaDist = 64;
	
	private static List<Arena> arenas = new ArrayList<Arena>();
	
	public static FileConfiguration cfg;
	
	public static File file;
	
	//A Tester
	public static void setupConfigs(MinGame lima) {
		
		
		file = new File(lima.getDataFolder(),"arenas.yml");
		cfg = YamlConfiguration.loadConfiguration(file);
		
		System.out.println("We setup the config");
		
		if (cfg.getConfigurationSection("arenas") == null) {
			return;
		}
		
		for(String s : cfg.getConfigurationSection("arenas").getKeys(false)) {
			
			String name = s;
			World world;
			Location l1 = null;
			Location l2 = null;
			
			world = Bukkit.getServer().getWorld(cfg.getConfigurationSection("arenas").getString(s+".world"));
			
		
			boolean haveLoc1 = cfg.getConfigurationSection("arenas").isSet(s+".loc1.x");
			boolean haveLoc2 = cfg.getConfigurationSection("arenas").isSet(s+".loc2.x");
			boolean haveSpecLoc = cfg.getConfigurationSection("arenas").isSet(s+".specLoc.x");
			
			if (haveLoc1) {
				double loc1x = cfg.getConfigurationSection("arenas").getDouble(s+".loc1.x");
				double loc1y = cfg.getConfigurationSection("arenas").getDouble(s+".loc1.y");
				double loc1z = cfg.getConfigurationSection("arenas").getDouble(s+".loc1.z");
				
				l1 = new Location(world,loc1x,loc1y,loc1z);
			}
			if (haveLoc2) {
				double loc2x = cfg.getConfigurationSection("arenas").getDouble(s+".loc2.x");
				double loc2y = cfg.getConfigurationSection("arenas").getDouble(s+".loc2.y");
				double loc2z = cfg.getConfigurationSection("arenas").getDouble(s+".loc2.z");
				
				l2 = new Location(world,loc2x,loc2y,loc2z);
			}
			
			Arena ar = new Arena(name,l1,l2,world);
			
			Location specLoc = null;
			int minPlayer = cfg.getConfigurationSection("arenas").getInt(s+".minPlayer");
			int maxPlayer = cfg.getConfigurationSection("arenas").getInt(s+".maxPlayer");
			
			int waitingTime = cfg.getConfigurationSection("arenas").getInt(s+".waitingTime");
			
			String weaponName = cfg.getConfigurationSection("arenas").getString(s+".weapon");
			boolean isOpen = cfg.getConfigurationSection("arenas").getBoolean(s+".isOpen");
			CustomWeapon weapon = WeaponManager.getWeaponByName(weaponName);
			
			
			if (haveSpecLoc) {
				double loc2x = cfg.getConfigurationSection("arenas").getDouble(s+".specLoc.x");
				double loc2y = cfg.getConfigurationSection("arenas").getDouble(s+".specLoc.y");
				double loc2z = cfg.getConfigurationSection("arenas").getDouble(s+".specLoc.z");
				
				specLoc = new Location(world,loc2x,loc2y,loc2z);
			}
			
			//marche pas
			List<Location> spawnLocs = new ArrayList<Location>();
			if (cfg.getConfigurationSection("arenas."+s+".spawnlocs") != null) {
				for(String s2 : cfg.getConfigurationSection("arenas."+s+".spawnlocs").getKeys(false))
				{
					double x = cfg.getConfigurationSection("arenas."+s+".spawnlocs").getDouble(s2+".x");
					double y = cfg.getConfigurationSection("arenas."+s+".spawnlocs").getDouble(s2+".y");
					double z = cfg.getConfigurationSection("arenas."+s+".spawnlocs").getDouble(s2+".z");
					
					
					spawnLocs.add(new Location(world,x,y,z));	
				}
			}
			
			

			
			
			ar.spawnLocs = spawnLocs;
			ar.specLoc = specLoc;
			ar.minPlayer = minPlayer;
			ar.maxPlayer = maxPlayer;
			ar.waitingTime = waitingTime;
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
			return;
		}
		
		Location l1 = ar.loc1;
		Location l2 = ar.loc2;
		
		Material[][][] m = StructureAPI.getStructureByLocation(l1, l2);
		StructureAPI.save(ar.name, m);
		
		p.sendMessage("arena saved");
		
	}
	
	public static void loadArena(Player p, Arena ar) {
		
		if (p != null && (ar.loc1 == null || ar.loc2 == null)) {
			p.sendMessage("Error, location not defined");
			return;
		}
		
		Material[][][] m = StructureAPI.load(ar.name);
		
		if (p != null && m == null) {
			p.sendMessage("schematic error, admin must save the arena before");
			return;
		}
		
		Location block = ar.loc1;
		Location block2= ar.loc2;
		
        double minX = block.getX() < block2.getX() ? block.getX() : block2.getX();
        double minZ = block.getZ() < block2.getZ() ? block.getZ() : block2.getZ();
        double minY = block.getY() < block2.getY() ? block.getY() : block2.getY();
		
		
        Location tempLocation = new Location(ar.loc1.getWorld(),minX,minY,minZ);
        
		StructureAPI.paste(m,tempLocation);
		if (p != null) {
			p.sendMessage("arena loaded");
		}
	}
	
	
	public static boolean addArena(Arena arena) {
		for(Arena a : arenas) {
			if (a.name.equals(arena.name)) {
				return false;
			}
			
		}
		
		arena.saveConfig(MinGame.INSTANCE);
		
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
				a.leaveArena(p,false);
		}
		
	}
	
	
	

	
}
