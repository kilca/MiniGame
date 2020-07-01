package poly.bedtech.arena;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import poly.bedtech.LimaMain;

public class Arena {

	//must be UNIQUE
	public String name;
	
	//devrait etre privee pour saveConfig a chaque fois
	public Location loc1;
	public Location loc2;
	
	//mettre Equipes
	
	public boolean isOpen;
	public boolean isStarted;
	
	public World world;
	
	//dans autre classe ?

	public BukkitRunnable borderRun;
	
	public List<ArenaTeam> teams;
	
	//default inventory .. don't modify except config
	public List<ItemStack> items = new ArrayList<ItemStack>();
	
	//final
	public int totalMaxPlayer =2;
	public int teamCount = 2; 
	
	public Arena(String name, World world) {
		
		this.name = name;
		this.world = world;
		ajouterTeam();
	}
	
	public Arena(String name, Location l1, Location l2, World world) {
		this.name = name;
		this.loc1 = l1;
		this.loc2 = l2;
		
		this.world = world;
		ajouterTeam();
	}
	
	private void ajouterTeam() {
		teams = new ArrayList<ArenaTeam>();
		for(int i=0;i<teamCount;i++) {
			teams.add(new ArenaTeam(items,totalMaxPlayer/teamCount));
		}
	}
	
	public void changeBorder() {
		
		if (borderRun == null)
			showBorder();
		else
			unShowBorder();
		
	}
	
	private void unShowBorder() {
		borderRun.cancel();
		borderRun = null;
	}
	
	private void showBorder() {
		borderRun = new BukkitRunnable() {
			@Override
			public void run() {
				
				int startX = Math.min(loc1.getBlockX(), loc2.getBlockX());
				int startY = Math.min(loc1.getBlockY(), loc2.getBlockY());
				int startZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
				int endX = Math.max(loc1.getBlockX(), loc2.getBlockX());
				int endY = Math.max(loc1.getBlockX(), loc2.getBlockY());
				int endZ = Math.max(loc1.getBlockX(), loc2.getBlockZ());
				
				System.out.println("we show particle");
				
				for (double x = startX + 0.5; x <= endX + 1; x++) {
		            for (double y = startY; y <= endY + 1; y++) {
		                for (double z = startZ + 0.5; z <= endZ + 1; z++) {
		                        if ((int) x == startX || (int) x == endX || 
		                            (int) y == startY || (int) y == endY + 1|| 
		                            (int) z == startZ || (int) z == endZ) {
		                	        //loc1.getWorld().spawnParticle(Particle.REDSTONE, (float) x, (float) y,(float) z, 1, new Particle.DustOption(Color.RED,1));
		                        	loc1.getWorld().spawnParticle(Particle.BARRIER, (float) x, (float) y,(float) z, 1);
		                        	/*
		                            Object packet = Reflections.getPacket("PacketPlayOutWorldParticles",
		                                    "reddust", (float) x, (float) y, (float) z,
		                                    0f, 0f, 0f, 0f, 1);
		                            Reflections.sendPacket(p, packet);
		                            */
		                      }
		                }
		             }
		         }
				
			}
			
			
			
		};
		borderRun.runTaskTimer(LimaMain.INSTANCE, 0, 20);
		//Bukkit.getScheduler().runTaskTimer(LimaMain.INSTANCE, borderRun,0,20);
		
		
	}
	
	public String getName() {
		return name;
	}
	
	public void saveConfig(LimaMain limInstance) {
		String def = "arenas."+name+".";
		
		limInstance.getConfig().set(def+"name", this.name);
		
		limInstance.getConfig().set(def+"world", this.world.getName());
		
		if (loc1 != null) {
			limInstance.getConfig().set(def+"loc1.x", loc1.getX());
			limInstance.getConfig().set(def+"loc1.y", loc1.getY());
			limInstance.getConfig().set(def+"loc1.z", loc1.getZ());
		}
		if (loc2 != null) {
			limInstance.getConfig().set(def+"loc2.x", loc2.getX());
			limInstance.getConfig().set(def+"loc2.y", loc2.getY());
			limInstance.getConfig().set(def+"loc2.z", loc2.getZ());
		}
		for(ItemStack it : items) {
			/*
			if (it.containsEnchantment(Enchantment.LUCK)) {
				limInstance.getConfig().set(def+"items."+it.getItemMeta().getDisplayName(), 0);
			}else {
				limInstance.getConfig().set(def+"items."+it.getItemMeta().getDisplayName(), it.getAmount());
			}
			*/
			limInstance.getConfig().set(def+"items."+it.getItemMeta().getDisplayName(), it.getAmount());
		}
		limInstance.saveConfig();
		
	}
	
	private List<Player> getAllPlayers(){
		
		List<Player> retour = new ArrayList<Player>();
		for(ArenaTeam t : teams) {
			retour.addAll(t.players);
		}
		return retour;
		
	}
	
	public void tryBeginGame() {
		
		for(ArenaTeam t : this.teams) {
			if (!t.isFull()) {
				return;
			}
		}
		
		Arena self = this;
		borderRun = new BukkitRunnable() {
			int count = 10;
			
			@Override
			public void run() {
				
				for(ArenaTeam t : self.teams) {
					if (!t.isFull()) {
						this.cancel();
					}
				}
				
				for(Player p : self.getAllPlayers()) {
					p.sendMessage("game begin in :"+count);
				}
				count--;
				if (count == 0) {
					self.startGame();
					this.cancel();
				}
			}
			
			
			
		};
		borderRun.runTaskTimer(LimaMain.INSTANCE, 0, 20);
		
	}
	
	private void startGame() {
		System.out.println("game started");
		
	}
	
	public void joinArena(Player player) {
		
		for(ArenaTeam t : this.teams) {
			if (t.players.contains(player)) {
				player.sendMessage("You are already in a team");
				return;
			}
		}
		
		for(ArenaTeam t : this.teams) {
			if (!t.isFull()) {
				t.joinTeam(player);
				player.sendMessage("arena joined");
				tryBeginGame();
				return;
			}
		}
		player.sendMessage("arena is full");
		
	}
	
	public void leaveArena(Player player) {
		for(ArenaTeam t : this.teams) {
			if (!t.isFull()) {
				t.leaveTeam(player);
				return;
			}
		}
		
	}
	public String getPlayerCount() {
		
		int playerCount = 0;
		for(ArenaTeam at : teams) {
			playerCount+=at.currentPlayer;
		}
		return playerCount+"/"+totalMaxPlayer;
		
	}

	
	
}
