package poly.bedtech.weapons;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_15_R1.PacketPlayOutPlayerInfo;
import poly.bedtech.LimaMain;

public class CustomGrenade extends CustomWeapon{

	//doit rebondir :O
	

	public double explosionDelay;
	
    public double damage;
    public double radius;
    
    //public boolean destruct;
    //public int fragments;
    
    public boolean fire;//a voir si compliqué
    //public double bounceStrength;

	public CustomGrenade(String s, String id) {
		super(s,id);
		// TODO Auto-generated constructor stub
	}
    
	@Override
	public void leftClick(PlayerInteractEvent event, Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rightClick(PlayerInteractEvent event, Player player) {
		Vector shootRotation;
		
		shootRotation = player.getLocation().getDirection();

		shootRotation.multiply(2.0);
		ItemStack item = WeaponManager.getItemByName(this.name);
		Entity ent = player.getWorld().dropItem(player.getLocation(), item); 
		//Entity ball = player.getWorld().spawn(player.getEyeLocation(), projectileClass);
		ent.setVelocity(shootRotation);
		//ball.setMetadata("gun", new FixedMetadataValue(LimaMain.INSTANCE,this.name));
		System.out.println("delay:"+explosionDelay);
		
		//60 tick = 3 secondes
		new BukkitRunnable() {
	        @Override
	        public void run() {
	        	ent.getWorld().createExplosion(ent.getLocation(), (float)radius,fire);
	        }
	    }.runTaskLater(LimaMain.INSTANCE, (long) explosionDelay * 20 );
		
		
		event.setCancelled(true);
		
	}
	
}
