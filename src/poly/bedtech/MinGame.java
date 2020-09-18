package poly.bedtech;

import org.bukkit.plugin.java.JavaPlugin;

import poly.bedtech.arena.ArenaEditGUI;
import poly.bedtech.arena.ArenaEvent;
import poly.bedtech.arena.ArenaManager;
import poly.bedtech.commands.CommandMG;
import poly.bedtech.weapons.WeaponEvent;
import poly.bedtech.weapons.WeaponManager;

/*Bug:
 * 
 * Bug 1.16
 * 
 * ?	Direct respawn lorsque l'arene est fini et j'ai gagné
 * 		(game already started ??)
 * 
 * 
 * X	Item a détruire
 * 
 * 
 * X	Possibilité que l'affichage des barriere fasse laguer si trop gros
 * 
 * X	join arene meme si op
 * 
 * X?	rejoindre arene alors que en cours (relance timer et tout)
 * 
 * ?	double timer
 * 
 * X	broadcast a tt le monde : truc a rejoin, faites ... pour rejoindre l'arene
 * 
 * 
 * X	Spawn des fois weapon not found
 * 
 * X	Rennommer arme marche
 * 
 * X	bloquer le fait de rejoindre si déja rejoint autre arene
 * 
 * pas d'explosion
 * 
 * */

/*
 * Possibilite : calcul taille arene en f du volume
 * 
 * 
 * 
 * */

/*TODO :
 * 
 * Fix Border effect	X
 * Fix Spawn Loc effect	X
 * 
 * Change msg info
 * 
 *Config sert a stocker les messages custom
 *fichier player.yml 
 *récompense custom si on souhaite
 *fichier arena.yml
 * 
 * */

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
