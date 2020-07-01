package poly.bedtech.arena;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ArenaTeam {

	public List<Player> players;

	public List<ItemStack> items;
	
	public int maxPlayer;
	public int currentPlayer;
	
	public ArenaTeam(List<ItemStack> items, int maxPlayer) {
		currentPlayer = 0;
		this.items = items;//Attention reference, a copier une fois debut game
		this.maxPlayer = maxPlayer;
		players = new ArrayList<Player>();
	}
	
	public void joinTeam(Player p) {
		if (currentPlayer >= maxPlayer) {
			return;
		}
		currentPlayer++;
		players.add(p);
	}
	
	public boolean isFull() {
		return (currentPlayer >= maxPlayer);
	}
	
	public void leaveTeam(Player p) {
		if (players.contains(p)) {
			players.remove(p);
		}
		currentPlayer--;
	}
	
	
}
