package poly.bedtech.commands;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import poly.bedtech.arena.Arena;
import poly.bedtech.arena.ArenaEditGUI;
import poly.bedtech.arena.ArenaManager;

public class CommandArena extends SubCommand {

	private Player player;
	
	private String tc(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	public void callSubCommand(CommandSender sender, String[] args) {
		
		System.out.println("dans le callsub de arena");
		
		if (sender instanceof Player){
			player = (Player)sender;
		}
		
		String arg0 = "";
		String arg1 = null;
		
		if (args.length > 0)
			arg0 = args[0];
		
		if (args.length > 1)
			arg1 = args[1];
			
		//System.out.println(args[0]);
			
		if (!sender.hasPermission("mg.*") && !sender.isOp()) {
			if (!sender.hasPermission("mg.arena."+arg0) && !sender.hasPermission("mg.arena.*")){
				sender.sendMessage("You do not have permission to perform this command");
				return;
			}
		}
			
		switch(arg0) {
		
			case "help":
				sender.sendMessage(tc("&e-------- &9MiniGame arena &e----------"));
				sender.sendMessage(tc("&6/mg arena list"));
				sender.sendMessage(tc("&6/mg arena create &3[name]"));
				sender.sendMessage(tc("&6/mg arena edit &3[name]"));
				sender.sendMessage(tc("&6/mg arena edititem &3[name]"));
				sender.sendMessage(tc("&6/mg arena remove &3[name]"));
				sender.sendMessage(tc("&6/mg arena join &3[name]"));
				sender.sendMessage(tc("&6/mg arena leave &3[name]"));
			break;
		
			case "list":
				
					ArenaManager.giveList(sender);
				break;
		
			case "create":
				
				if (arg1 != null) {
					if (ArenaManager.addArena(new Arena(arg1,player.getWorld()))) {
						player.sendMessage("arena created");
					}else {
						player.sendMessage("can't create arena");
					}
				}
				break;
			case "edit":
				
				
				if (arg1 != null) {
					Arena ar = ArenaManager.getArenaByName(arg1);
					if (ar == null) {
						sender.sendMessage("arena not found");
						return;	
					}
					
					
					
					
				}
				break;
			case "edititem":
				
				System.out.println(arg1);
				if (arg1 != null) {
					Arena ar = ArenaManager.getArenaByName(arg1);
					if (ar == null) {
						sender.sendMessage("arena not found");
						return;	
					}
					
					
					ArenaEditGUI.giveEditItem(player,ar);
					
				}
				
				break;
					
			case "remove":
				if (arg1 != null) {
					Arena ar = ArenaManager.getArenaByName(arg1);
					if (ar == null) {
						sender.sendMessage("arena not found");
						return;	
					}
					
					
					
					
				}

				break;
					
			case "join":
				
				if (arg1 != null) {
					Arena ar = ArenaManager.getArenaByName(arg1);
					if (ar == null) {
						sender.sendMessage("arena not found");
						return;	
					}
					
					
					ar.joinArena(player);
					player.sendMessage("need "+ar.getNumberPlayerNeeded()+" more players");
				}

				break;
			case "leave":
				
				if (arg1 != null) {
					Arena ar = ArenaManager.getArenaByName(arg1);
					if (ar == null) {
						sender.sendMessage("arena not found");
						return;	
					}
					
					ar.leaveArena(player,true);
					player.sendMessage("it now need "+ar.getNumberPlayerNeeded()+" more players");
				}

				break;
			
			
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
