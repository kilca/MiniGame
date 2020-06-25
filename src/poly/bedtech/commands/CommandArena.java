package poly.bedtech.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import poly.bedtech.arena.ArenaManager;

public class CommandArena extends SubCommand {

	private Player player;
	
	public void callSubCommand(CommandSender sender, String[] args) {
		
		System.out.println("dans le callsub de arena");
		
		if (sender instanceof Player){
			player = (Player)sender;
		}
		if (args[0].length() > 0) {
			
			System.out.println(args[0]);
			
			switch(args[0]) {
				case "save":
					ArenaManager.saveArena(player, "test");
					break;
				case "load":
					ArenaManager.loadArena(player, "test");
					break;
			
			}
		}
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "arena";
	}

	@Override
	public String info() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] aliases() {
		// TODO Auto-generated method stub
		return null;
	}


	
}
