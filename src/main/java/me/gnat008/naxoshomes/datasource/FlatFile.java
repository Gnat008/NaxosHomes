package me.gnat008.naxoshomes.datasource;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import me.gnat008.naxoshomes.BukkitService;
import me.gnat008.naxoshomes.ConsoleLogger;
import me.gnat008.naxoshomes.homes.Home;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static me.gnat008.naxoshomes.util.FileUtils.createFileIfNotExists;
import static me.gnat008.naxoshomes.util.FileUtils.writeData;

/**
 * Flat file database.
 */
public class FlatFile implements DataSource
{

    private final File FILE_PATH;

    private BukkitService bukkitService;

    @Inject
    FlatFile(File dataFolder, BukkitService bukkitService)
    {
        this.FILE_PATH = new File(dataFolder, "user_data");
        this.bukkitService = bukkitService;
    }

    @Override
    public void saveHomesData(Player player, Set<Home> homes)
    {
        // Make sure the file exists
        File file = new File(FILE_PATH, player.getUniqueId().toString());
        try
        {
            createFileIfNotExists(file);
        }
        catch (IOException ex)
        {
            ConsoleLogger.severe("Unable to create file for player '" + player.getName() + "':", ex);
        }

        // Turn the set of homes into JSON
        Gson gson = new Gson();
        JsonObject root = new JsonObject();
        JsonArray j_homes = new JsonArray();
        for (Home home : homes)
        {
            JsonObject j_home = new JsonObject();
            j_home.addProperty("name", home.getName());
            j_home.addProperty("world", home.getWorld());
            j_home.addProperty("x", home.getX());
            j_home.addProperty("y", home.getY());
            j_home.addProperty("z", home.getZ());
            j_home.addProperty("pitch", home.getPitch());
            j_home.addProperty("yaw", home.getYaw());
            j_homes.add(j_home);
        }
        root.add("homes", j_homes);

        // Save the JSON to the file
        bukkitService.runTaskAsync(() -> writeData(file, gson.toJson(root)));
    }

    @Override
    public synchronized Set<Home> getHomes(Player player)
    {
        Set<Home> result = new HashSet<>();
        File file = new File(FILE_PATH, player.getUniqueId().toString());

        // If file doesn't exist, then no homes!
        if (!file.exists())
        {
            return result;
        }

        // Parse the Json
        try (JsonReader reader = new JsonReader(new FileReader(file)))
        {
            JsonParser parser = new JsonParser();
            JsonObject root = parser.parse(reader).getAsJsonObject();
            JsonArray homes = root.getAsJsonArray("homes");

            // Iterate through the elements in the array of homes
            Iterator<JsonElement> itr = homes.iterator();
            while (itr.hasNext())
            {
                JsonObject j_home = itr.next().getAsJsonObject();

                // Build a home from the data in the Json Array
                String name = j_home.get("name").getAsString();
                Home home = buildHome(name, j_home);

                result.add(home);
            }
        }
        catch (IOException ex)
        {
            ConsoleLogger.severe("Could not read file '" + file + "':", ex);
        }

        return result;
    }

    @Override
    public synchronized Home getHome(Player player, String homeName)
    {
        File file = new File(FILE_PATH, player.getUniqueId().toString());

        // If file doesn't exist, then no homes!
        if (!file.exists())
        {
            return null;
        }

        // Parse the Json
        try (JsonReader reader = new JsonReader(new FileReader(file)))
        {
            JsonParser parser = new JsonParser();
            JsonObject root = parser.parse(reader).getAsJsonObject();
            JsonArray homes = root.getAsJsonArray("homes");

            // Iterate through the elements in the array of homes
            Iterator<JsonElement> itr = homes.iterator();
            while (itr.hasNext())
            {
                JsonObject j_home = itr.next().getAsJsonObject();

                String name = j_home.get("name").getAsString();

                // Check the name of the home
                if (!name.equals(homeName))
                {
                    continue;
                }

                // Build a home from the data in the Json Object and return it
                return buildHome(name, j_home);
            }
        }
        catch (IOException ex)
        {
            ConsoleLogger.severe("Could not read file '" + file + "':", ex);
        }

        return null;
    }

    /**
     * Build a home from a JsonObject.
     *
     * @param name The name of the home
     * @param json The home's location
     * @return The finished home
     */
    private Home buildHome(String name, JsonObject json)
    {
        World world = Bukkit.getWorld(json.get("world").getAsString());
        Location loc = new Location(
                world,
                json.get("x").getAsDouble(),
                json.get("y").getAsDouble(),
                json.get("z").getAsDouble(),
                json.get("yaw").getAsFloat(),
                json.get("pitch").getAsFloat());

        return new Home(name, loc);
    }
}
