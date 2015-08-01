package com.teambr.bookshelf.achievement;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;

import java.util.ArrayList;
import java.util.List;

/**
 * Bookshelf
 * Created by Paul Davis on 7/31/2015
 */
public abstract class AchievementList {
    private String label;
    protected List<Achievement> achievements;

    /**
     * Used to create a new list of achievements
     * @param name The name of the list (Usually the mod Name)
     */
    public AchievementList(String name) {
        achievements = new ArrayList<>();
        label = name;
        AchievementRegistry.instance.putAchievementList(this);
        initAchievements();
        AchievementRegistry.instance.registerModAchievements(label, achievements.toArray(new Achievement[achievements.size()]));
    }

    /**
     * Called before adding and registering, build all achievements here
     */
    public abstract void initAchievements();

    /**
     * Stub method to start
     */
    public void start() {}

    /**
     * Get the name of this list
     * @return The mod name
     */
    public String getName() {
        return label;
    }

    /**
     * Get the achievements
     * @return A list of achievements
     */
    public List<Achievement> getAchievements() {
        return achievements;
    }

    /**
     * Used to return an achievement by the id
     * @param id The id. Case sensitive
     * @return The achievement if found, otherwise null
     */
    public Achievement getAchievementByName(String id) {
        for(Achievement achievement : achievements)
            if(achievement.statId.equals(id))
                return achievement;
        return null;
    }

    /**
     * Creates an achievement
     * @param id The string id, the achievement tag will be put in for you
     *           achievement.NAMEHERE
     * @param x X Position in the GUI, think of a grid
     * @param y Y Position in the GUI, think of a grid
     * @param stack The stack to display
     * @param parent Does this have a parent?, if so list it here
     */
    public void buildAchievement(String id, int x, int y, Item stack, Achievement parent) {
        achievements.add(new Achievement(id, id, x, y, stack, parent).registerStat());
    }

    /**
     * Creates an achievement
     * @param id The string id, the achievement tag will be put in for you
     *           achievement.NAMEHERE
     * @param x X Position in the GUI, think of a grid
     * @param y Y Position in the GUI, think of a grid
     * @param stack The block to display
     * @param parent Does this have a parent?, if so list it here
     */
    public void buildAchievement(String id, int x, int y, Block stack, Achievement parent) {
        achievements.add(new Achievement(id, id, x, y, stack, parent).registerStat());
    }

    /**
     * Creates an achievement
     * @param id The string id, the achievement tag will be put in for you
     *           achievement.NAMEHERE
     * @param x X Position in the GUI, think of a grid
     * @param y Y Position in the GUI, think of a grid
     * @param stack The item to display
     * @param parent Does this have a parent?, if so list it here
     */
    public void buildAchievement(String id, int x, int y, ItemStack stack, Achievement parent) {
        achievements.add(new Achievement(id, id, x, y, stack, parent).registerStat());
    }
}

