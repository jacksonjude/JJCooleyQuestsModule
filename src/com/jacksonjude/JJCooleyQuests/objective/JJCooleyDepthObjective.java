package com.jacksonjude.JJCooleyQuests.objective;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import me.blackvein.quests.CustomObjective;
import me.blackvein.quests.Quest;
import me.blackvein.quests.Quester;
import me.blackvein.quests.Quests;

public class JJCooleyDepthObjective extends CustomObjective {
	private static Quests quests = (Quests) Bukkit.getServer().getPluginManager().getPlugin("Quests");
	
	public JJCooleyDepthObjective() {
    	this.setName("Depth");
    	this.setAuthor("JJCooley");
    	
        this.setShowCount(true);
        int maxHeight = Bukkit.getServer().getWorlds().get(0).getMaxHeight();
        this.setCountPrompt("Depth (" + maxHeight + "-y):");
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e)
	{
		updateObjective(e.getPlayer());
	}
	
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent e)
	{
		updateObjective(e.getPlayer());
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent e)
	{
		updateObjective(e.getPlayer());
	}
	
	public void updateObjective(Player player)
	{
		int yCoord = player.getLocation().getBlockY();
		Quester quester = quests.getQuester(player.getUniqueId());
		
		for (Quest q : quester.getCurrentQuests().keySet()) {
			int oldCount = 0;
			if (quester.getQuestData(q).customObjectiveCounts.containsKey(this.getName()))
				oldCount = quester.getQuestData(q).customObjectiveCounts.get(this.getName());
			
			int maxHeight = Bukkit.getServer().getWorlds().get(0).getMaxHeight();
			incrementObjective(player, this, (maxHeight-yCoord)-oldCount, q);
		}
	}
}
