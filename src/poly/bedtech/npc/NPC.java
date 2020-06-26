package poly.bedtech.npc;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.CraftServer;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.minecraft.server.v1_15_R1.DataWatcher;
import net.minecraft.server.v1_15_R1.DataWatcherObject;
import net.minecraft.server.v1_15_R1.DataWatcherRegistry;
import net.minecraft.server.v1_15_R1.EntityPlayer;
import net.minecraft.server.v1_15_R1.MinecraftServer;
import net.minecraft.server.v1_15_R1.PacketPlayOutEntity;
import net.minecraft.server.v1_15_R1.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_15_R1.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_15_R1.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_15_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_15_R1.PlayerConnection;
import net.minecraft.server.v1_15_R1.PlayerInteractManager;
import net.minecraft.server.v1_15_R1.WorldServer;
import poly.bedtech.LimaMain;

public class NPC {
	private Location location;
	private String name;
	private GameProfile gameProfile;
	private EntityPlayer entityNPC;
	private String textureID;
	private String signatureID;
	
	public NPC(Location location, String name) {
		this.location = location;
		this.name = name;
		/*this.textureID = textureID;
		this.signatureID = signatureID;*/
		this.textureID = "eyJ0aW1lc3RhbXAiOjE1NjgzMzg4NjM0MTgsInByb2ZpbGVJZCI6IjgyYzYwNmM1YzY1MjRiNzk4YjkxYTEyZDNhNjE2OTc3IiwicHJvZmlsZU5hbWUiOiJOb3ROb3RvcmlvdXNOZW1vIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9kOTQ1ZmIwMmRlYzllOGU1MWY3NWRkZTk0ZDQ2MmY2MjdkYjJjNDc5Yzg5MTZjZWJiYzQwZGRmN2RlOGUxMWU4In19fQ==";
		this.signatureID = "bqTpjg9XaWTev89StuYu8ZbJHCx5w5cA14r3bhKpII1ZHfU42mFTHdeoQFZw3Pmmzy0/HtOK8ZOqrw0Ieiaye+9xXBuBkgJQji+0qWlBfHSC+dTpEaJYEE2aaCNcN1OO9mmgonyyBQ6PYyNGVsgyiZfI0MZPAf7efa1ku67ipR3CkM9tzdCKv10m8OXkHY+54UOP6aXvxKd5UXYmVg73BfT3ULTh1XDXlr+nEeu+Wkc9jGsL9W8yZrAKKIrGXH8HC66oQR5OLwp2pYSlnr1IWrqaaZzZAPuhoRHXvzjEn18vYnZA/KbH0bNhpJ87gn4ugq83eZKFlRJHTQssCriBwM9eU6rz5Ua6TDjEiEwrl9aItwzhVsA7O3TV0rKm4dWePiIDpcIYKBPPhtBOoHQW6EkFPTQV0IsTVVgTDGNZ0EIpXZ17Q80pgQeDdGqTiq0s/v+Bqr5am7JA/ltZjGNqGIZwP7Boek3zrylr/QXzJOVIwg2XgFyjHO7U7Xb8BwmYaUBXtuzh5YBYVB0EjAr/WDIjDUlixPDlB8qhaUs19WPU2W6PoShjGB91ItWiqa4zL3vXJ0BKmlaeptUGM0AGOuP8w/v4UQyKWs5REAnHmkvNw7bpsl93B6T0S1+2aaTRPeJ0m32dlu6XPLCNmhVnPDd6Ul3jl5P5R2Us0UGw8kI=";
	}
	
	public void spawnNPC() {
		MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
		WorldServer nmsWorld = ((CraftWorld) location.getWorld()).getHandle();
		gameProfile = new GameProfile(UUID.randomUUID(), name);
		gameProfile.getProperties().put("textures", new Property("textures", textureID, signatureID));
		
		entityNPC = new EntityPlayer(nmsServer, nmsWorld, gameProfile, new PlayerInteractManager(nmsWorld));
		entityNPC.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	}
	
	public void showNPC(Player player) {
		PlayerConnection plConnection = ((CraftPlayer) player).getHandle().playerConnection;
		
		plConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityNPC));
		plConnection.sendPacket(new PacketPlayOutNamedEntitySpawn(entityNPC));
		
		new BukkitRunnable() {
	        @Override
	        public void run() {
	        	plConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityNPC));
	        }
	    }.runTaskLater(LimaMain.INSTANCE, 10); 
		
		plConnection.sendPacket(new PacketPlayOutEntityHeadRotation(entityNPC, (byte) (entityNPC.yaw * 256 / 360)));
		plConnection.sendPacket(new PacketPlayOutEntity.PacketPlayOutEntityLook(entityNPC.getId(), (byte)(entityNPC.yaw * 256 / 360), (byte)(entityNPC.pitch * 256 / 360), true));

		// DataWatcher to apply second layer of skin of the NPC to the client
		DataWatcher watcher = entityNPC.getDataWatcher();
		watcher.set(new DataWatcherObject<>(16, DataWatcherRegistry.a), (byte)127);
	    plConnection.sendPacket(new PacketPlayOutEntityMetadata(entityNPC.getId(), watcher, true));
        
	}
}
