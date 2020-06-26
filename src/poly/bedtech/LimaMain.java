package poly.bedtech;

import org.bukkit.plugin.java.JavaPlugin;

import poly.bedtech.arena.ArenaEditGUI;
import poly.bedtech.arena.ArenaManager;
import poly.bedtech.commands.CommandBonjour;
import poly.bedtech.commands.CommandLima;

public class LimaMain extends JavaPlugin {

    public static LimaMain INSTANCE;
	
    private void initListeners() {
    	
    	getServer().getPluginManager().registerEvents(new ArenaEditGUI(), this);
    	
    }
    
	@Override
	public void onEnable() {
		saveDefaultConfig();
		
		System.out.println("Le plugin vient de s'allumer !");
		
		INSTANCE = this;
		StructureAPI.plugin = this;
		
		ArenaManager.setupConfigs(this);
		
		CommandLima cl = new CommandLima();
		cl.setup();
		
		getCommand("bonjour").setExecutor(new CommandBonjour());
		getCommand("lima").setExecutor(cl);
		
		initListeners();
		
	}
	
	@Override
	public void onDisable() {
		System.out.println("Le plugin vient de s'eteindre");
	}
	
}
