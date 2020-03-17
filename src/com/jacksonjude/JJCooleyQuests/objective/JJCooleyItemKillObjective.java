package com.jacksonjude.JJCooleyQuests.objective;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import me.blackvein.quests.CustomObjective;
import me.blackvein.quests.Quest;
import me.blackvein.quests.Quester;
import me.blackvein.quests.Quests;

public class JJCooleyItemKillObjective extends CustomObjective {
	private static Quests quests = (Quests) Bukkit.getServer().getPluginManager().getPlugin("Quests");
	
	public JJCooleyItemKillObjective()
	{
		this.setName("Item Kill");
    	this.setAuthor("JJCooley");
    	
    	this.addStringPrompt("Item", "Item to kill a player with:", "DIAMOND_HOE");
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e)
	{
		Player killer = e.getEntity().getKiller();
		
		if (killer.getType() != EntityType.PLAYER) return;
		
		updateObjective(killer);
	}
	
	public void updateObjective(Player player)
	{
		Quester quester = quests.getQuester(player.getUniqueId());
		
		for (Quest q : quester.getCurrentQuests().keySet()) {
			Map<String, Object> map = getDataForPlayer(player, this, q);
			
			if (map == null || map.get("Item") == null) { continue; }
			
			Material type = Material.getMaterial((String) map.get("Item"));
			ItemStack heldItem = player.getInventory().getItemInMainHand();
			
			if (heldItem.getType().getKey().equals(type.getKey()))
				incrementObjective(player, this, 1, q);
		}
	}
}
