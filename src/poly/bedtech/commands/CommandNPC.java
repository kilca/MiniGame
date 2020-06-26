package poly.bedtech.commands;

import poly.bedtech.npc.*;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandNPC implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		if(sender instanceof Player) {
			Player player = (Player)sender;
			NPC npc = new NPC(player.getLocation(), "");
			npc.spawnNPC();
			npc.showNPC(player);
		} else {
			sender.sendMessage("You're not a player");
		}
		
		return false;
	}

}
