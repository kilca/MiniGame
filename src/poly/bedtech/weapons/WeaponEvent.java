package poly.bedtech.weapons;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
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
		
		
		ItemStack item = player.getInventory().getItem(player.getInventory().getHeldItemSlot());
		if (item == null)
			return;
		String name = item.getItemMeta().getDisplayName();
		CustomWeapon weapon = (CustomWeapon) WeaponManager.getWeaponByName(name);

		if (weapon == null) {
			System.err.println("weapon not found");
			return;
		}
		event.setDamage(weapon.damage);
		//event.setCancelled(true);
	}
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
