package poly.bedtech.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import poly.bedtech.MinGame;
import poly.bedtech.arena.Arena;
import poly.bedtech.arena.ArenaEditGUI;
import poly.bedtech.arena.ArenaManager;
import poly.bedtech.weapons.WeaponManager;

public class CommandWeapon extends SubCommand  {

private Player player;
	
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
			
			System.out.println(args[0]);
		

			
		switch(arg0) {
		
			case "help":
				sender.sendMessage("list");
				sender.sendMessage("give [item]");
				break;
			case "list":
				if (!player.isOp()) {
					player.sendMessage("You need to be op to perform this command");
					return;
				}
				WeaponManager.showItemList(player);
				break;
			case "give":
				if (!player.isOp()) {
					player.sendMessage("You need to be op to perform this command");
					return;
				}
				if (arg1 != null) {
					ItemStack item = WeaponManager.getItemByName(arg1);
					if (item != null)
						player.getInventory().addItem(item);
					else
						player.sendMessage("error, weapon not found");
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
