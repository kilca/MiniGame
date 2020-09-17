package poly.bedtech;

import org.bukkit.plugin.java.JavaPlugin;

import poly.bedtech.arena.ArenaEditGUI;
import poly.bedtech.arena.ArenaEvent;
import poly.bedtech.arena.ArenaManager;
import poly.bedtech.commands.CommandMG;
import poly.bedtech.weapons.WeaponEvent;
import poly.bedtech.weapons.WeaponManager;

public class MinGame extends JavaPlugin {

	//https://www.spigotmc.org/threads/multiple-config.56384/
	
    public static MinGame INSTANCE;
	
    private void initListeners() {
    	
    	getServer().getPluginManager().registerEvents(new ArenaEditGUI(), this);
    	getServer().getPluginManager().registerEvents(new WeaponEvent(), this);
    	getServer().getPluginManager().registerEvents(new ArenaEvent(), this);
    }
    /*
     * Item (baaseball) not found
     * */
    
	@Override
	public void onEnable() {
		saveDefaultConfig();
		saveResource("weapons.yml",true);
		
		System.out.println("Le plugin vient de s'allumer !");
		
		INSTANCE = this;
		StructureAPI.plugin = this;
		
		//l'ordre est important
		WeaponManager.loadWeapons(this);
		ArenaManager.setupConfigs(this);
		
		CommandMG cl = new CommandMG();
		cl.setup();
		
		getCommand("mg").setExecutor(cl);
		
		initListeners();
		
	}
	
	@Override
	public void onDisable() {
		System.out.println("Le plugin vient de s'eteindre");
	}
	
}
