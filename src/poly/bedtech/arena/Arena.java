package poly.bedtech.arena;

import org.bukkit.Location;

import poly.bedtech.LimaMain;

public class Arena {

	//must be UNIQUE
	public String name;
	
	//devrait etre privee pour saveConfig a chaque fois
	public Location loc1;
	public Location loc2;
	
	//mettre Equipes
	public boolean isStarted;
	
	public Arena(String name) {
		
		this.name = name;
	}
	
	public Arena(String name, Location l1, Location l2) {
		this.name = name;
		this.loc1 = l1;
		this.loc2 = l2;
	}
	
	public String getName() {
		return name;
	}
	
	public void saveConfig(LimaMain limInstance) {
		String def = "arenas."+name+".";
		
		limInstance.getConfig().set(def+"name", this.name);
		
		if (loc1 != null) {
			limInstance.getConfig().set(def+"loc1.x", loc1.getX());
			limInstance.getConfig().set(def+"loc1.y", loc1.getY());
			limInstance.getConfig().set(def+"loc1.z", loc1.getZ());
		}
		if (loc2 != null) {
			limInstance.getConfig().set(def+"loc2.x", loc2.getX());
			limInstance.getConfig().set(def+"loc2.y", loc2.getY());
			limInstance.getConfig().set(def+"loc2.z", loc2.getZ());
		}
		
		limInstance.saveConfig();
		
	}
	

	
	
}
