package poly.bedtech;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		System.out.println("Le plugin viens de s'allumer !");
	}
	
	@Override
	public void onDisable() {
		System.out.println("Le plugin viens de s'eteindre");
	}
	
}
