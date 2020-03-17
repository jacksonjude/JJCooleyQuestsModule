package com.jacksonjude.JJCooleyQuests.objective;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerExpChangeEvent;

import me.blackvein.quests.CustomObjective;
import me.blackvein.quests.Quest;
import me.blackvein.quests.Quester;
import me.blackvein.quests.Quests;

public class JJCooleyRespawnLevelObjective extends CustomObjective {
	private static Quests quests = (Quests) Bukkit.getServer().getPluginManager().getPlugin("Quests");
	
	public JJCooleyRespawnLevelObjective()
	{
		this.setName("Respawn Level");
    	this.setAuthor("JJCooley");
    	
    	this.addStringPrompt("Level", "Respawn Level:", "7");
	}
	
	@EventHandler
	public void onPlayerExpChange(PlayerExpChangeEvent e)
	{
		updateObjective(e);
	}
	
	public static int XpToLevels(int xp){
        if (xp <= 352) {
            return (int) (Math.sqrt(xp+9)-3);
        } else if (xp >= 394 && xp <= 1507) {
            return (int) ((Math.sqrt(40*xp-7839)+81)*0.1);
        } else if (xp >= 1628) {
            return (int) ((Math.sqrt(72*xp-54215)+325)/18);
        }
        return -1;
    }
	
	public void updateObjective(PlayerExpChangeEvent e)
	{
		Player player = e.getPlayer();
		
		Quester quester = quests.getQuester(player.getUniqueId());
		
		for (Quest q : quester.getCurrentQuests().keySet()) {
			Map<String, Object> map = getDataForPlayer(e.getPlayer(), this, q);
			
			if (map == null || map.get("Level") == null) { continue; }
			
			String levelNeeded = (String) map.get("Level");
			
			if (player.getExp() == 0 && XpToLevels(e.getAmount()) == (int)Integer.parseInt(levelNeeded))
				incrementObjective(player, this, 1, q);
		}
	}
}
