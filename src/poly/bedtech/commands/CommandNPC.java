package poly.bedtech.commands;

import poly.bedtech.npc.*;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CommandNPC implements CommandExecutor {

	private static int id = 0;
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		
		if(sender instanceof Player) {
			Player player = (Player)sender;
			NPC npc = new NPC(player.getLocation(), "Thanos" + id);
			id+=1;
			npc.spawnNPC();
			npc.showNPC();
		} else {
			sender.sendMessage("You're not a player !");
		}
		
		return false;
	}

}
