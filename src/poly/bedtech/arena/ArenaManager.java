package poly.bedtech.arena;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import poly.bedtech.StructureAPI;

public class ArenaManager {

	private List<Arena> arenas = new ArrayList<Arena>();
	
	public Material[][][] savedArena;
	
	public static void saveArena(Player p, String name) {
		Location l1 = new Location(p.getWorld(),-10,80,-10);
		Location l2 = new Location(p.getWorld(),10,90,10);
		
		Material[][][] m = StructureAPI.getStructureByLocation(l1, l2);
		StructureAPI.save(name, m);
		
		p.sendMessage("arena saved");
		
	}
	
	public static void loadArena(Player p, String name) {

		Material[][][] m = StructureAPI.load(name);
		StructureAPI.paste(m,new Location(p.getWorld(),-10,80,-10));
		p.sendMessage("arena loaded");
	}
	
	
	public void addArena(Arena arena) {
		this.arenas.add(arena);
	}
	
	public void joinArena(Player p) {
		//todo
	}
	
}
