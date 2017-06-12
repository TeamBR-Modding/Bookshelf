package com.teambr.bookshelf.achievement;

//import net.minecraft.block.Block;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.stats.Achievement;

import java.util.ArrayList;
import java.util.List;

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * The AchievementList is what a mod should use to add mods. Extend this class and add achievements to the list
 * during the initAchievements method. You can use the buildAchievement helper method to make this easier provided by
 * this class
 *
 * @author Paul Davis pauljoda
 * @since July 31, 2015
 */
public abstract class AchievementList {
//    private String label;
//    protected List<Achievement> achievements;
//
//    /**
//     * Used to create a new list of achievements
//     * @param name The name of the list (Usually the mod Name)
//     */
//    public AchievementList(String name) {
//        achievements = new ArrayList<>();
//        label = name;
//        AchievementRegistry.instance.putAchievementList(this);
//        initAchievements();
//        AchievementRegistry.instance.registerModAchievements(label, achievements.toArray(new Achievement[achievements.size()]));
//    }
//
//    /**
//     * Called before adding and registering, build all achievements here
//     */
//    public abstract void initAchievements();
//
//    /**
//     * Stub method to start
//     */
//    public void start() {}
//
//    /**
//     * Get the name of this list
//     * @return The mod name
//     */
//    public String getName() {
//        return label;
//    }
//
//    /**
//     * Get the achievements
//     * @return A list of achievements
//     */
//    public List<Achievement> getAchievements() {
//        return achievements;
//    }
//
//    /**
//     * Used to return an achievement by the id
//     * @param id The id. Case sensitive
//     * @return The achievement if found, otherwise null
//     */
//    public Achievement getAchievementByName(String id) {
//        for(Achievement achievement : achievements)
//            if(achievement.statId.equals(id))
//                return achievement;
//        return null;
//    }
//
//    /**
//     * Creates an achievement
//     * @param id The string id, the achievement tag will be put in for you
//     *           achievement.NAMEHERE
//     * @param x X Position in the GUI, think of a grid
//     * @param y Y Position in the GUI, think of a grid
//     * @param stack The stack to display
//     * @param parent Does this have a parent?, if so list it here
//     */
//    public void buildAchievement(String id, int x, int y, Item stack, Achievement parent) {
//        achievements.add(new Achievement(id, id, x, y, stack, parent).registerStat());
//    }
//
//    /**
//     * Creates an achievement
//     * @param id The string id, the achievement tag will be put in for you
//     *           achievement.NAMEHERE
//     * @param x X Position in the GUI, think of a grid
//     * @param y Y Position in the GUI, think of a grid
//     * @param stack The block to display
//     * @param parent Does this have a parent?, if so list it here
//     */
//    public void buildAchievement(String id, int x, int y, Block stack, Achievement parent) {
//        achievements.add(new Achievement(id, id, x, y, stack, parent).registerStat());
//    }
//
//    /**
//     * Creates an achievement
//     * @param id The string id, the achievement tag will be put in for you
//     *           achievement.NAMEHERE
//     * @param x X Position in the GUI, think of a grid
//     * @param y Y Position in the GUI, think of a grid
//     * @param stack The item to display
//     * @param parent Does this have a parent?, if so list it here
//     */
//    public void buildAchievement(String id, int x, int y, ItemStack stack, Achievement parent) {
//        achievements.add(new Achievement(id, id, x, y, stack, parent).registerStat());
//    }
}

