package com.teambr.bookshelf.achievement;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

import java.util.HashMap;

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * This is the registry for mod achievements. The only use this is to a mod is to trigger the achievement. This will
 * handle holding the achievements for each mod and as long as you super the constructor of AchievementList it will
 * get added here automatically
 *
 * @author Paul Davis pauljoda
 * @since July 31, 2015
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
                player.addStat(achieve, 1);
                return;
            }
        }
    }
}
