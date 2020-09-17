package poly.bedtech.arena;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import poly.bedtech.LimaMain;

public class ArenaEvent implements Listener{

	LimaMain lima;
	public ArenaEvent(){
		super();
		lima = LimaMain.INSTANCE;
	}
	
	//on est sympa quand m�me
    @EventHandler
    public void onKick(PlayerKickEvent event){
    	Player p = event.getPlayer();
    	Arena a = ArenaManager.getArena(p);
    	if (a != null) {
    		a.goSpec(p);
    	} 
    }
    
    @EventHandler
    public void onLeave(PlayerQuitEvent event){
    	Player p = event.getPlayer();
    	Arena a = ArenaManager.getArena(p);
    	if (a != null) {
    		a.goSpec(p);
    	} 
    }
    
    @EventHandler
    public void PlayerJoin(PlayerLoginEvent event) {
    	Player p = event.getPlayer();

    	if (lima.getConfig().contains("players."+p.getName())) {
    		ArenaManager.leaveArena(p);
    	}
    }
    
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
    	Player p = event.getEntity();
    	Arena a = ArenaManager.getArena(p);
    	if (a != null) {
    		a.goSpec(p);
    	}
    }
    
	
}
