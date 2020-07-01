package poly.bedtech.weapons;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import poly.bedtech.LimaMain;

public class CustomLandmine extends CustomWeapon{

	
   public int size;
   public int radius;
   // fire: true
	
	public CustomLandmine(String name, String id) {
		super(name, id);
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

		shootRotation.multiply(1.0);
		ItemStack item = WeaponManager.getItemByName(this.name);
		Entity ent = player.getWorld().dropItem(player.getLocation(), item); 
		//Entity ball = player.getWorld().spawn(player.getEyeLocation(), projectileClass);
		ent.setVelocity(shootRotation);
		//ball.setMetadata("gun", new FixedMetadataValue(LimaMain.INSTANCE,this.name));
		
		//60 tick = 3 secondes
		CustomLandmine self = this;
		new BukkitRunnable() {
	        @Override
	        public void run() {
	        	if (ent.isOnGround()) {
	        		Location l = ent.getLocation();
	        		List<Block> blocks = new ArrayList<Block>();
	        		for(int i=0;i<size;i++) {
	        			for(int j =0;j<size;j++) {
	        				Location newLoc = l.add(new Location(l.getWorld(),i,0,j));
	        				if (newLoc.getBlock().getType() == Material.AIR) {
	               				newLoc.getBlock().setType(material);    
	               				newLoc.getBlock().setMetadata("mine", new FixedMetadataValue(LimaMain.INSTANCE,self.name));
	               				blocks.add(newLoc.getBlock());
	        				}
	 
	        			}
	        			
	        		}
	        		for(Block b : blocks) {
           				b.setMetadata("mineother", new FixedMetadataValue(LimaMain.INSTANCE,blocks));
	        		}
	        		
	        		System.out.println("item touch ground:"+l);
	        		ent.remove();
	        		this.cancel();
	        	}
	        }
	    }.runTaskTimer(LimaMain.INSTANCE, 0,20 );
		
		
		event.setCancelled(true);
		
	}

}
