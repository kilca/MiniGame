package poly.bedtech;

import org.bukkit.plugin.java.JavaPlugin;

import poly.bedtech.commands.CommandBonjour;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		saveDefaultConfig();
		System.out.println("Le plugin vient de s'allumer !");
		getCommand("bonjour").setExecutor(new CommandBonjour());
	}
	
	@Override
	public void onDisable() {
		System.out.println("Le plugin vient de s'eteindre");
	}
	
}
