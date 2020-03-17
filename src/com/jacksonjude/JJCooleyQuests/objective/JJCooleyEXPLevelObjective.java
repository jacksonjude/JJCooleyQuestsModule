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

public class JJCooleyEXPLevelObjective extends CustomObjective {
	private static Quests quests = (Quests) Bukkit.getServer().getPluginManager().getPlugin("Quests");
	
	public JJCooleyEXPLevelObjective()
	{
		this.setName("EXP Level");
    	this.setAuthor("JJCooley");
    	
    	this.addStringPrompt("Level", "EXP level to reach:", "50");
	}
	
	@EventHandler
	public void onPlayerExpChange(PlayerExpChangeEvent e)
	{
		this.updateObjective(e);
	}
	
	public static int xpToLevels(int xp){
        if (xp <= 352) {
            return (int) (Math.sqrt(xp+9)-3);
        } else if (xp >= 394 && xp <= 1507) {
            return (int) ((Math.sqrt(40*xp-7839)+81)*0.1);
        } else if (xp >= 1628) {
            return (int) ((Math.sqrt(72*xp-54215)+325)/18);
        }
        return -1;
    }
	
	public static int levelsToXp(int level){
        if(level <= 16){
            return (int) (Math.pow(level,2) + 6*level);
        } else if(level <= 31){
            return (int) (2.5*Math.pow(level,2) - 40.5*level + 360.0);
        } else {
            return (int) (4.5*Math.pow(level,2) - 162.5*level + 2220.0);
        }
    }
	
	public void updateObjective(PlayerExpChangeEvent e)
	{
		Player player = e.getPlayer();
		
		Quester quester = quests.getQuester(player.getUniqueId());
		
		for (Quest q : quester.getCurrentQuests().keySet()) {
			Map<String, Object> map = getDataForPlayer(e.getPlayer(), this, q);
			
			if (map == null || map.get("Level") == null) { continue; }
			
			int levelNeeded = Integer.parseInt((String) map.get("Level"));
			int levelAt = xpToLevels(levelsToXp(player.getLevel()) + e.getAmount());
			
			if (levelNeeded == levelAt)
				incrementObjective(player, this, 1, q);
		}
	}
}
