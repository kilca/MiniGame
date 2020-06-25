package poly.bedtech.commands;

import org.bukkit.command.CommandSender;

public abstract class SubCommand {


    public String permissionName;
	
    public SubCommand() {
    }
    
    public abstract void callSubCommand(CommandSender sender, String[] args);

    public boolean hasPermission() {
    	return true;
    }
   
    
    public abstract String name();

    public abstract String info();

    public abstract String[] aliases();
	
}
