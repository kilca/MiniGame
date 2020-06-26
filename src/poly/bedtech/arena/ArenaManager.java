package poly.bedtech.arena;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import poly.bedtech.LimaMain;
import poly.bedtech.StructureAPI;

public class ArenaManager {

	//singleton ?
	
	private static final int maxArenaDist = 100;
	
	private static List<Arena> arenas = new ArrayList<Arena>();
	
	//A Tester
	public static void setupConfigs(LimaMain lima) {
		
		for(String s : lima.getConfig().getConfigurationSection("arenas").getKeys(false)) {
			
			Arena ar = new Arena(s);
			//todo check
			//if (lima.getConfig().contains(a))
			double loc1x = lima.getConfig().getConfigurationSection("arenas").getDouble(s+"loc1.x");
			double loc1y = lima.getConfig().getConfigurationSection("arenas").getDouble(s+"loc1.x");
			double loc1z = lima.getConfig().getConfigurationSection("arenas").getDouble(s+"loc1.x");
			
			double loc2x = lima.getConfig().getConfigurationSection("arenas").getDouble(s+"loc2.x");
			double loc2y = lima.getConfig().getConfigurationSection("arenas").getDouble(s+"loc2.x");
			double loc2z = lima.getConfig().getConfigurationSection("arenas").getDouble(s+"loc2.x");
			
			//System.out.println(loc1x);
			
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
		
		for(Arena a : arenas) {
			sender.sendMessage(a.getName());
		}
		
	}
	
	public static void saveArena(Player p, Arena ar) {
		
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

		Material[][][] m = StructureAPI.load(ar.name);
		StructureAPI.paste(m,ar.loc1);
		p.sendMessage("arena loaded");
	}
	
	
	public static void addArena(Arena arena) {
		for(Arena a : arenas) {
			if (a.name.equals(arena.name)) {
				return;
			}
			
		}
		
		arena.saveConfig(LimaMain.INSTANCE);
		
		arenas.add(arena);
	}
	
	public void joinArena(Player p) {
		//todo
	}
	
}
