package poly.bedtech.npc;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import net.minecraft.server.v1_15_R1.EntityPlayer;
import net.minecraft.server.v1_15_R1.PacketPlayInUseEntity;

public class ClientNPCPacketListener implements Listener {
	
	@EventHandler
	public void onjoin(PlayerJoinEvent event) {
		for(EntityPlayer npc : NPC.getListNPC()) {
			NPC.showNPC(event.getPlayer(), npc);
		}
		addPacketListener(event.getPlayer());
	}
	
	@EventHandler
	public void onleave(PlayerQuitEvent event) {
		removePacketListener(event.getPlayer());
	}
	
	private void addPacketListener(Player player) {
		ChannelDuplexHandler duplex = new ChannelDuplexHandler() {
			
			@Override
			public void channelRead(ChannelHandlerContext context, Object packet) throws Exception {
				if(packet instanceof PacketPlayInUseEntity) {
					PacketPlayInUseEntity entityInUsePacket = (PacketPlayInUseEntity) packet;
					Field entityID = packet.getClass().getDeclaredField("a");
					entityID.setAccessible(true);
					for(EntityPlayer pl : NPC.getListNPC()) {
						if(pl.getId() == entityID.getInt(packet)) {
							System.out.println("NPC " + pl.getName() + " selected");
						}
					}
					entityID.setAccessible(false);
				}
				super.channelRead(context, packet);
			}
			
			/*
			@Override
			public void write(ChannelHandlerContext context, Object packet, ChannelPromise promise) throws Exception {
				//getLogger().info(ChatColor.AQUA + "WRITE >>" + packet.toString());
				//Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "WRITE >>" + packet.toString());
				super.write(context, packet, promise);
			}
			*/
		};
		
		ChannelPipeline pipeline = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel.pipeline();
		pipeline.addBefore("packet_handler", player.getName(), duplex);
	}
	
	private void removePacketListener(Player player) {
		Channel channel = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
		channel.eventLoop().submit(() -> {
			channel.pipeline().remove(player.getName());
			return null;
		});
	}
}
