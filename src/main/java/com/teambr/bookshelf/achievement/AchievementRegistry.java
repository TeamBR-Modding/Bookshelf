package com.teambr.bookshelf.achievement;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

import java.util.HashMap;

/**
 * Bookshelf
 * Created by Paul Davis on 7/31/2015
 */
public class AchievementRegistry {
    public static AchievementRegistry instance = new AchievementRegistry();

    private  HashMap<String, AchievementList> achievements;

    public AchievementRegistry() {
        achievements = new HashMap<String, AchievementList>();
    }

    /**
     * Puts the list into the global registry
     * @param list The list we are adding
     */
    public void putAchievementList(AchievementList list) {
        achievements.put(list.getName(), list);
    }

    /**
     * Tells minecraft to include these achievements
     * @param name The name (mod name)
     * @param achievements The achievements to display
     */
    public void registerModAchievements(String name, Achievement... achievements) {
        AchievementPage modpage = new AchievementPage(name, achievements);
        AchievementPage.registerAchievementPage(modpage);
    }

    /**
     * Call this to trigger an achievement
     * @param modName The mod triggering
     * @param achievementName The name of the achievement
     * @param player The player triggering
     */
    public void triggerAchievement(String modName, String achievementName, EntityPlayer player) {
        for(Achievement achieve : achievements.get(modName).getAchievements()) {
            if(achieve.statId.equals(achievementName)) {
                player.triggerAchievement(achieve);
                return;
            }
        }
    }
}