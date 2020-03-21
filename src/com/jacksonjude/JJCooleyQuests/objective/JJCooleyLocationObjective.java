package com.jacksonjude.JJCooleyQuests.objective;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import me.blackvein.quests.CustomObjective;
import me.blackvein.quests.Quest;
import me.blackvein.quests.Quester;
import me.blackvein.quests.Quests;

public class JJCooleyLocationObjective extends CustomObjective {
	private static Quests quests = (Quests) Bukkit.getServer().getPluginManager().getPlugin("Quests");
	
	public JJCooleyLocationObjective()
	{
		this.setName("Location");
    	this.setAuthor("JJCooley");
    	
    	this.addStringPrompt("XPos", "X position:", "-1");
    	this.addStringPrompt("YPos", "Y position:", "-1");
    	this.addStringPrompt("ZPos", "Z position:", "-1");
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e)
	{
		updateObjective(e.getPlayer());
	}
	
	public void updateObjective(Player player)
	{
		int xCoord = player.getLocation().getBlockX();
		int yCoord = player.getLocation().getBlockY();
		int zCoord = player.getLocation().getBlockZ();
		Quester quester = quests.getQuester(player.getUniqueId());
		
		for (Quest q : quester.getCurrentQuests().keySet()) {
			Map<String, Object> map = getDataForPlayer(player, this, q);
			
			if (map == null || map.get("XPos") == null || map.get("YPos") == null || map.get("ZPos") == null) { continue; }
			
			int xNeeded = Integer.valueOf((String) map.get("XPos"));
			int yNeeded = Integer.valueOf((String) map.get("YPos"));
			int zNeeded = Integer.valueOf((String) map.get("ZPos"));
			
			//System.out.println(xNeeded + "," + yNeeded + "," + zNeeded + " / " + xCoord + "," + yCoord + "," + zCoord);
			
			if ((xNeeded < 0 || xCoord == xNeeded) && (yNeeded < 0 || yCoord == yNeeded) && (zNeeded < 0 || zCoord == zNeeded))
				incrementObjective(player, this, 1, q);
		}
	}
}
