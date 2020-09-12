package poly.bedtech.weapons;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import poly.bedtech.LimaMain;

public class CustomGun extends CustomWeapon{

	public double velocity;//probleme decale ??
	
	public String projectile;
    public double radius;
    public boolean destruct;

    public int bullet_number;//nombre de tirs
	public double knockback;
	public float zoom;
	public double spread;
	//-----------
	
	public Class projectileClass;
	
	
	private boolean isZooming = false;
	
	//todo zoom
	
	public CustomGun(String localName, String name, String id, String projectile) {
		super(localName, name, id);
		this.projectile = projectile;
		projectileClass = this.getEntityByName(projectile);
		// TODO Auto-generated constructor stub
	}

	public double randomDouble(double rangeMin, double rangeMax) {
		
		Random r = new Random();
		double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
		return randomValue;
	}
	
	public void cancelZoom(Player player) {
        if (player != null) {
            //resets the walking speed to normal
            player.setWalkSpeed(0.2F);
        }
        isZooming = false;
		
	}
	
	@Override
	public void leftClick(PlayerInteractEvent event,Player player) {
		if (player.getWalkSpeed() == 0.2F && zoom!= 0) {
				player.setWalkSpeed(zoom);//ex -0.15
		}else {
			cancelZoom(player);
		}
		event.setCancelled(true);
	}
	
	@Override
	public void rightClick(PlayerInteractEvent event, Player player) {
		Vector shootRotation;
		
		
		for(int i=0;i<bullet_number;i++) {
			shootRotation = player.getLocation().getDirection();
			if (bullet_number != 0) {
				shootRotation.setX(shootRotation.getX() + randomDouble(-spread,spread));
				shootRotation.setY(shootRotation.getY() + randomDouble(-spread,spread));
				shootRotation.setZ(shootRotation.getZ() + randomDouble(-spread,spread));
			}
			shootRotation.multiply(velocity);
			Entity ball = player.launchProjectile(projectileClass);
			//Entity ball = player.getWorld().spawn(player.getEyeLocation(), projectileClass);
			ball.setVelocity(shootRotation);
			ball.setMetadata("gun", new FixedMetadataValue(LimaMain.INSTANCE,this.name));
		}
		this.cancelZoom(player);
		event.setCancelled(true);
		
	}
	
	
}
