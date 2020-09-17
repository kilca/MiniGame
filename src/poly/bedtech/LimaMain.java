package poly.bedtech;

import org.bukkit.plugin.java.JavaPlugin;

import poly.bedtech.arena.ArenaEditGUI;
import poly.bedtech.arena.ArenaEvent;
import poly.bedtech.arena.ArenaManager;
import poly.bedtech.commands.CommandLima;
import poly.bedtech.weapons.WeaponEvent;
import poly.bedtech.weapons.WeaponManager;

public class LimaMain extends JavaPlugin {

	//https://www.spigotmc.org/threads/multiple-config.56384/
	
    public static LimaMain INSTANCE;
	
    private void initListeners() {
    	
    	getServer().getPluginManager().registerEvents(new ArenaEditGUI(), this);
    	getServer().getPluginManager().registerEvents(new WeaponEvent(), this);
    	getServer().getPluginManager().registerEvents(new ArenaEvent(), this);
    }
    
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
		
		CommandLima cl = new CommandLima();
		cl.setup();
		
		getCommand("lima").setExecutor(cl);
		
		initListeners();
		
	}
	
	@Override
	public void onDisable() {
		System.out.println("Le plugin vient de s'eteindre");
	}
	
}
