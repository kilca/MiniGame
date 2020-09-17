package poly.bedtech.weapons;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class WeaponEvent implements Listener{
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Action action = event.getAction();
		ItemStack it = event.getItem();
		
		if (it == null)
			return;
		if (it.getItemMeta() == null)
			return;
		
		CustomWeapon cw = WeaponManager.getWeaponByNameMaterial(it.getItemMeta().getDisplayName(), it.getType());
		
		if (cw == null)
			return;
		if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)
			cw.leftClick(event, player);
		else if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
			cw.rightClick(event, player);
	}
	
	public ItemStack getItemFromPlayer(Player player) {
		
		return player.getInventory().getItem(player.getInventory().getHeldItemSlot());
		
	}
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent event) {
		
		
		Player player = null;
		
		//si projectile
		if(event.getCause() == DamageCause.PROJECTILE) {
			//todo plus de cas
		    Snowball a = (Snowball) event.getDamager();
		    if(a.getShooter() instanceof Player) {
		        player = (Player) a.getShooter();
		    }
		//sinon
		}else if ((event.getDamager() instanceof Player)){
			player = (Player) event.getDamager();
			
			
		}
		
		if (player == null)
			return;
		
		
		ItemStack item = getItemFromPlayer(player);
		
		if (item == null)
			return;
		String name = item.getItemMeta().getLocalizedName();
		CustomWeapon weapon = (CustomWeapon) WeaponManager.getWeaponByName(name);

		if (weapon == null) {
			System.err.println("weapon not found");
			return;
		}
		event.setDamage(weapon.damage);
		//event.setCancelled(true);
	}
	
	@EventHandler
	public void onActivate(PlayerInteractEvent event) {
		//TODO try{}
	    /*
		if(event.getAction().equals(Action.PHYSICAL)) {
	    	System.out.println("clicked block :"+event.getClickedBlock());

			System.out.println("test has metadata mineother");
			if (event.getClickedBlock().hasMetadata("mineother")) {
				System.out.println("ici");
				FixedMetadataValue val  = (FixedMetadataValue) event.getClickedBlock().getMetadata("mineother").get(0);
				List<Block> blockmine = (List<Block>) val.value();
				System.out.println(blockmine.size());
				for(Block b : blockmine) {
					b.setType(Material.AIR);
				}
				event.setCancelled(true);
				event.getClickedBlock().setType(Material.AIR);
			}
	    }
	    */
	}
	
	/*
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (player == null)
			return;
		ItemStack item = getItemFromPlayer(player);
		
		if (item == null)
			return;
		
		
	//TODO: Add logic
	}
	*/
	
	/*
	@EventHandler
    public void onHit(ProjectileHitEvent event) {
    
    		
		if (event.getEntity().hasMetadata("gun")) {
			FixedMetadataValue val  = (FixedMetadataValue) event.getEntity().getMetadata("gun").get(0);
			CustomGun gun = (CustomGun) WeaponManager.getWeaponByName(val.asString());
			
			//System.out.println(gun.name);
			event(gun.damage);
			if (gun.destruct)
				gun.explode(event.getEntity());
		    
	    }
    }
    */
    
	
}
