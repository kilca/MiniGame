package poly.bedtech.arena;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_16_R2.PacketPlayInClientCommand;
import net.minecraft.server.v1_16_R2.PacketPlayInClientCommand.EnumClientCommand;
import poly.bedtech.ItemUtils;
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
    
    /*
    @EventHandler
    public void onThrow(PlayerDropItemEvent e){
        Item t = e.getItemDrop();
        if (t == Material.IRON_AXE){
            e.setCancelled(true);
        }else{
            e.setCancelled(false);
        }
    }
    */
    
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
    	
    	System.out.println("A player is dead");
    	
    	Player p = event.getEntity();
    	
		for(ItemStack i : event.getDrops()) {
			if (ItemUtils.getItemTag(i,"weaponName") != null)
				i.setAmount(0);
		}
    	
    	Arena a = ArenaManager.getArena(p);
    	if (a != null) {
    		
    		for(ItemStack i : event.getDrops()) {
    			i.setAmount(0);
    		}
    		
    		Bukkit.getScheduler().scheduleSyncDelayedTask(MinGame.INSTANCE, new Runnable() {
    		    @Override
    		    public void run() {
    		    	((CraftPlayer) p).getHandle().playerConnection.a(new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN));
    	    		a.goSpec(p);
    		    }
    		}, 1L); //20 Tick (1 Second) delay before run() is called
    		
    		
    	}
    
    }
    
    @EventHandler
    public void onItemDrop (PlayerDropItemEvent e) {
        if (ItemUtils.getItemTag(e.getItemDrop().getItemStack(),"weaponName") != null) {
        	e.setCancelled(true);
        }
    }
    

    
    
	
}
