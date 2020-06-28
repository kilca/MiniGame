package poly.bedtech.weapons;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.metadata.FixedMetadataValue;

import poly.bedtech.LimaMain;

public class CustomGun extends CustomWeapon{

	public double velocity;
	
	public String projectile;
    public double damage;
    public double radius;
    public boolean destruct;

    public int fragDispersion;//nombre de tirs
	public double knockback;
	//-----------
	
	public Class projectileClass;
	
	public CustomGun(String name, String id, String projectile) {
		super(name, id);
		this.projectile = projectile;
		projectileClass = this.getEntityByName(projectile);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void leftClick(Player player) {
		
		Entity ball = player.getWorld().spawn(player.getEyeLocation(), projectileClass);
		ball.setVelocity(player.getLocation().getDirection().multiply(velocity));
		ball.setMetadata("gun", new FixedMetadataValue(LimaMain.INSTANCE,this.name));
		//snowball.setShooter(player);
	}
	
	public void explode(Entity entity) {
		
		entity.getWorld().createExplosion(entity.getLocation(), (float)radius);
		
	}
	
	
}
