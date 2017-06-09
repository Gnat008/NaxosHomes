package me.gnat008.naxoshomes.datasource;


import me.gnat008.naxoshomes.homes.Home;
import org.bukkit.entity.Player;

import java.util.Set;

public interface DataSource
{
    /**
     * Save all of a player's homes to the database.
     *
     * @param player The player to save the homes for
     * @param homes The full list of homes to save
     */
    void saveHomesData(Player player, Set<Home> homes);

    /**
     * Get all of a player's saved homes.
     * This will return an empty Set if there are no saved homes
     * for this player.
     *
     * @param player The player to get the homes for
     * @return A set of homes
     */
    Set<Home> getHomes(Player player);

    /**
     * Get a specific home for a player by its name.
     * If no home with this name is found, this method will
     * return null.
     *
     * @param player The player to get the home for
     * @param homeName The name of the home
     * @return The home, or null
     */
    Home getHome(Player player, String homeName);
}
