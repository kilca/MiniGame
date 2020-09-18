package poly.bedtech.arena;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.minecraft.server.v1_15_R1.PacketPlayInClientCommand;
import net.minecraft.server.v1_15_R1.PacketPlayInClientCommand.EnumClientCommand;
import poly.bedtech.MinGame;

public class ArenaEvent implements Listener{

	MinGame lima;
	public ArenaEvent(){
		super();
		lima = MinGame.INSTANCE;
	}
	
	//on est sympa quand même
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
    	
    	System.out.println("A player is dead");
    	
    	Player p = event.getEntity();
    	
    	
    	
    	Arena a = ArenaManager.getArena(p);
    	if (a != null) {
    		
    		Bukkit.getScheduler().scheduleSyncDelayedTask(MinGame.INSTANCE, new Runnable() {
    		    @Override
    		    public void run() {
    		    	((CraftPlayer) p).getHandle().playerConnection.a(new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN));
    	    		a.goSpec(p);
    		    }
    		}, 1L); //20 Tick (1 Second) delay before run() is called
    		
    		
    	}
    
    }

    
    
	
}
