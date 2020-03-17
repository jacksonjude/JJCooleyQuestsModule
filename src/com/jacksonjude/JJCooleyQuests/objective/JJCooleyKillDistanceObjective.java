package com.jacksonjude.JJCooleyQuests.objective;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import me.blackvein.quests.CustomObjective;
import me.blackvein.quests.Quest;
import me.blackvein.quests.Quester;
import me.blackvein.quests.Quests;

public class JJCooleyKillDistanceObjective extends CustomObjective {
	private static Quests quests = (Quests) Bukkit.getServer().getPluginManager().getPlugin("Quests");
	
	public JJCooleyKillDistanceObjective() {
    	this.setName("Kill Distance");
    	this.setAuthor("JJCooley");
    	
    	this.addStringPrompt("Distance", "Distance to kill from:", "100");
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e)
	{
		updateObjective(e);
	}
	
	public void updateObjective(PlayerDeathEvent e)
	{
		Player killed = e.getEntity();
		Player killer = e.getEntity().getKiller();
		
		if (killer.getType() != EntityType.PLAYER) return;
		
		double killDistance = killer.getLocation().distance(killed.getLocation());
		
		Quester quester = quests.getQuester(killer.getUniqueId());
		
		for (Quest q : quester.getCurrentQuests().keySet()) {
			Map<String, Object> map = getDataForPlayer(killer, this, q);
			
			if (map == null || map.get("Distance") == null) { continue; }
			
			int distance = Integer.valueOf((String) map.get("Distance"));
			
			if (killDistance >= distance)
				incrementObjective(killer, this, 1, q);
		}
	}
}
