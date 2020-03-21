package com.jacksonjude.JJCooleyQuests.objective;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;

import me.blackvein.quests.CustomObjective;
import me.blackvein.quests.Quest;
import me.blackvein.quests.Quester;
import me.blackvein.quests.Quests;

public class JJCooleyItemBreakObjective extends CustomObjective
{
private static Quests quests = (Quests) Bukkit.getServer().getPluginManager().getPlugin("Quests");
	
	public JJCooleyItemBreakObjective()
	{
		this.setName("Break Item");
    	this.setAuthor("JJCooley");
    	
    	this.addStringPrompt("Item", "Item to break:", "DIAMOND_HOE");
	}
	
	@EventHandler
	public void onItemBreak(PlayerItemBreakEvent e)
	{
		updateObjective(e);
	}
	
	public void updateObjective(PlayerItemBreakEvent e)
	{
		Player player = e.getPlayer();
		Quester quester = quests.getQuester(player.getUniqueId());
		
		for (Quest q : quester.getCurrentQuests().keySet()) {
			Map<String, Object> map = getDataForPlayer(player, this, q);
			
			if (map == null || map.get("Item") == null) { continue; }
			
			Material type = Material.getMaterial((String) map.get("Item"));
			ItemStack brokenItem = e.getBrokenItem();
			
			if (brokenItem.getType().getKey().equals(type.getKey()))
				incrementObjective(player, this, 1, q);
		}
	}
}
