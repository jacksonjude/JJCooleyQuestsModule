package com.jacksonjude.JJCooleyQuests.objective;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jacksonjude.JJCooleyQuests.lib.JJCooleyProperties;

import me.blackvein.quests.CustomObjective;
import me.blackvein.quests.Quest;
import me.blackvein.quests.Quester;
import me.blackvein.quests.Quests;

public class JJCooleyAllKillObjective extends CustomObjective {
	private static Quests quests = (Quests) Bukkit.getServer().getPluginManager().getPlugin("Quests");
	
	public JJCooleyAllKillObjective() {
    	this.setName("All Kill");
    	this.setAuthor("JJCooley");
        this.setShowCount(true);
        this.setCountPrompt("Number of unique players to kill:");
        
//        if (this.getCount() == -1)
//        {
//        	int onlinePlayerCount = Bukkit.getServer().getOnlinePlayers().size();
//        	int offlinePlayerCount = Bukkit.getServer().getOfflinePlayers().length;
//        	System.out.println(Bukkit.getServer().getOnlinePlayers());
//        	System.out.println(Bukkit.getServer().getOfflinePlayers());
//        	int totalPlayers = onlinePlayerCount + offlinePlayerCount;
//            this.setCount(totalPlayers);
//        }
	}
	
	public JsonObject getPlayerKills()
	{
		JJCooleyProperties.loadProperties();
		String playerKillsJSONString = JJCooleyProperties.readProperties("kills");
		
		JsonParser parser = new JsonParser();
		JsonObject playerKills;
		if (playerKillsJSONString == null || playerKillsJSONString == "")
		{
			playerKills = parser.parse("{}").getAsJsonObject();
		}
		else
		{
			playerKills = parser.parse(playerKillsJSONString).getAsJsonObject();
		}
		
		return playerKills;
	}
	
	public void setPlayerKills(JsonObject playerKills)
	{
		JJCooleyProperties.setProperties("kills", playerKills.toString());
		JJCooleyProperties.storeProperties();
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e)
	{
		Player killed = e.getEntity();
		Player killer = e.getEntity().getKiller();
		
		if (killer.getType() != EntityType.PLAYER) return;
		
		JsonObject playerKills = getPlayerKills();
		
		JsonElement killerKillsElement = playerKills.get(killer.getUniqueId().toString());
		JsonArray killerKills;
		if (killerKillsElement == null)
		{
			killerKills = new JsonArray();
		}
		else
		{
			killerKills = killerKillsElement.getAsJsonArray();
		}
		
		boolean found = false;
		for (int i = 0; i < killerKills.size(); i++)
		    if (killerKills.get(i).getAsString().equals(killed.getUniqueId().toString()))
		        found = true;
		
		if (!found)
		{
			killerKills.add(killed.getUniqueId().toString());
			
			playerKills.remove(killer.getUniqueId().toString());
			playerKills.add(killer.getUniqueId().toString(), killerKills);
			
			setPlayerKills(playerKills);
			
			updateObjective(killer);
		}
	}
	
	public void updateObjective(Player player)
	{
		Quester quester = quests.getQuester(player.getUniqueId());
		
		for (Quest q : quester.getCurrentQuests().keySet()) {
//			int oldCount = 0;
//			if (quester.getQuestData(q).customObjectiveCounts.containsKey(this.getName()))
//				oldCount = quester.getQuestData(q).customObjectiveCounts.get(this.getName());
//			
//			System.out.println(oldCount);
//			System.out.println(this.getCount());
//			if (oldCount+1 >= this.getCount())
//			{
//				JsonObject playerKills = getPlayerKills();
//				playerKills.remove(player.getUniqueId().toString());
//				setPlayerKills(playerKills);
//			}
			
			incrementObjective(player, this, 1, q);
		}
	}
}
