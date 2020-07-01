package poly.bedtech.arena;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import net.md_5.bungee.api.ChatColor;

public class TeamManager implements Listener{

	private Scoreboard s;
	
	public void onEnable() {
		s = Bukkit.getScoreboardManager().getMainScoreboard();
		registerHealtBar();
		registerNameTag();
	}	
		
	public void playerJoin(PlayerJoinEvent e) {
		s.getTeam("blue").addPlayer(e.getPlayer());
	}
	
	public void registerHealtBar() {
		if (s.getObjective("health") != null){
				s.getObjective("health").unregister();
		}
		Objective o = s.registerNewObjective("health", "health", ChatColor.RED+ "♥");
		/*
		Objective o = s.registerNewObjective("health", "health");
		o.setDisplayName(ChatColor.RED+ "♥");
		*/
		o.setDisplaySlot(DisplaySlot.BELOW_NAME);
	}
	
	public void registerNameTag() {
		
		if (s.getTeam("blue") != null) {
			s.getTeam("blue").unregister();
		}
		Team t = s.registerNewTeam("blue");
		t.setPrefix(ChatColor.BLUE + "");
		
	}
	
}
