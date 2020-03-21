package com.jacksonjude.JJCooleyQuests.objective;

import java.util.Date;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import me.blackvein.quests.CustomObjective;
import me.blackvein.quests.Quest;
import me.blackvein.quests.Quester;
import me.blackvein.quests.Quests;

public class JJCooleyVelocityObjective extends CustomObjective {
	private static Quests quests = (Quests) Bukkit.getServer().getPluginManager().getPlugin("Quests");
	private long lastVelocityTime = 0;
	private Location lastLocation;
	
	public JJCooleyVelocityObjective()
	{
		this.setName("Velocity");
    	this.setAuthor("JJCooley");
        
    	this.addStringPrompt("Velocity", "Velocity:", "2");
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e)
	{
		if (lastLocation != null && lastVelocityTime != 0 && (new Date()).getTime()-lastVelocityTime >= 500)
		{
			double playerVelocity = 10*e.getPlayer().getLocation().distance(lastLocation)/(((new Date()).getTime()-lastVelocityTime)/500);
			updateObjective(e.getPlayer(), playerVelocity);
		}
		
		if ((new Date()).getTime()-lastVelocityTime >= 500)
		{
			lastVelocityTime = (new Date()).getTime();
		}
		lastLocation = e.getPlayer().getLocation();
	}
	
	public void updateObjective(Player player, double velocity)
	{
		Quester quester = quests.getQuester(player.getUniqueId());
		
		for (Quest q : quester.getCurrentQuests().keySet()) {
			Map<String, Object> map = getDataForPlayer(player, this, q);
			
			if (map == null || map.get("Velocity") == null) { continue; }
			
			int velocityNeeded = Integer.parseInt((String) map.get("Velocity"));
			
			if (velocity >= velocityNeeded)
			{
				incrementObjective(player, this, 1, q);
			}
		}
	}
}
