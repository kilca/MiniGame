package poly.bedtech.weapons;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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
		
		cw.leftClick(player);
	}
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent event) {
		
	}
	
	@EventHandler
    public void onHit(ProjectileHitEvent event) {
		if (event.getEntity().hasMetadata("gun")) {
			FixedMetadataValue val  = (FixedMetadataValue) event.getEntity().getMetadata("gun").get(0);
			CustomGun gun = (CustomGun) WeaponManager.getWeaponByName(val.asString());
			if (gun.destruct)
				gun.explode(event.getEntity());
		    
	    }
    }
	
}
