package poly.bedtech.arena;

import org.bukkit.entity.Player;

public class ArenaPlayer {

	public Player player;
	
	//true if in arena, false if is spectator
	public boolean isInGame = true;
	
	public void becomeSpec() {
		isInGame = false;
	}
	
    public ArenaPlayer(Player player) {
		super();
		this.player = player;
	}

	@Override
    public boolean equals(Object object)
    {
    	if(object == null)
    		return false;
    	if (object instanceof Player) {
    		Player p  = (Player) object;
    		return player.getName().equals(p.getName());
    	}else if (object instanceof ArenaPlayer) {
    		ArenaPlayer ap = (ArenaPlayer) object;
    		return this.player.getName().equals(ap.player.getName());
    	}
		return false;

    	
    }
	
}
