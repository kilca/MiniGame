package poly.bedtech;

import org.bukkit.plugin.java.JavaPlugin;

import poly.bedtech.commands.CommandBonjour;
import poly.bedtech.commands.CommandLima;

public class LimaMain extends JavaPlugin {

    public static LimaMain INSTANCE;
	
	@Override
	public void onEnable() {
		System.out.println("Le plugin vient de s'allumer !");
		
		INSTANCE = this;
		StructureAPI.plugin = this;
		
		CommandLima cl = new CommandLima();
		cl.setup();
		
		getCommand("bonjour").setExecutor(new CommandBonjour());
		getCommand("lima").setExecutor(cl);
	}
	
	@Override
	public void onDisable() {
		System.out.println("Le plugin vient de s'eteindre");
	}
	
}
