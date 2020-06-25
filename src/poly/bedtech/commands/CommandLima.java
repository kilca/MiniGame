package poly.bedtech.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

import java.util.Arrays;
import java.util.HashMap;


public class CommandLima implements CommandExecutor{
	
	//Classe générale appelant les autres classes (handler)
	
	//attention ne prend pas en compte les aliases
	private HashMap<String,SubCommand> subCommands = new HashMap<String,SubCommand>();
	
	public void setup() {
		
	
		subCommands.put("arena", new CommandArena());
		
	}
	
	public SubCommand getCommand(String s) {
		return subCommands.get(s);
	}
	
	//on pourrait rendre plus propre le hasArgs
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		boolean hasArgs = (args != null) && (args.length >1);
		
		if (hasArgs){
			String subArray[] = Arrays.copyOfRange(args, 1, args.length);
			
			SubCommand sc = getCommand(args[0]);
			if (sc != null) {
				sc.callSubCommand(sender, subArray);
			}else {
				System.out.println("command not found:"+args[0]);
			}

		}else {
			
			sender.sendMessage(ChatColor.RED+"Please specify which command or type help");	
		}
		
		
		return false;
	}
	

}
