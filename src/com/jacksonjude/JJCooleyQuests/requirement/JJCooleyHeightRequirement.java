package com.jacksonjude.JJCooleyQuests.requirement;

import java.util.Map;
import org.bukkit.entity.Player;
import me.blackvein.quests.CustomRequirement;

public class JJCooleyHeightRequirement extends CustomRequirement {
    public JJCooleyHeightRequirement() {
    	this.setName("Height");
    	this.setAuthor("JJCooley");
    	this.addStringPrompt("Height", "Enter the y coord", null);
	}
    
    @Override
    public boolean testRequirement(Player player, Map<String, Object> data) {
    	int heightToTest = Integer.parseInt((String) data.get("Height"));
    	
    	return player.getLocation().getBlockY() >= heightToTest;
    }
}