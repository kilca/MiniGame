package poly.bedtech;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import poly.bedtech.arena.ArenaEditGUI;
import poly.bedtech.arena.ArenaEvent;
import poly.bedtech.arena.ArenaManager;
import poly.bedtech.commands.CommandMG;
import poly.bedtech.weapons.WeaponEvent;
import poly.bedtech.weapons.WeaponManager;



/*TODO :
 * 
 * 
 *Config sert a stocker les messages custom
 *fichier player.yml 
 *récompense custom si on souhaite
 * 
 * empecher casser arene
 * */

public class MinGame extends JavaPlugin {

	//https://www.spigotmc.org/threads/multiple-config.56384/
	
    public static MinGame INSTANCE;
	
    
    //---Player
    
	public static FileConfiguration cfg;
	public static File file;
    
    private void initListeners() {
    	
    	getServer().getPluginManager().registerEvents(new ArenaEditGUI(), this);
    	getServer().getPluginManager().registerEvents(new WeaponEvent(), this);
    	getServer().getPluginManager().registerEvents(new ArenaEvent(), this);
    }
    /*
     * Item (baaseball) not found
     * */
    
    private void loadPlayerFile() {
		file = new File(this.getDataFolder(),"players.yml");
		cfg = YamlConfiguration.loadConfiguration(file);
    	
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
		loadPlayerFile();
		
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
