package me.gnat008.naxoshomes;

import ch.jalu.injector.Injector;
import ch.jalu.injector.InjectorBuilder;
import me.gnat008.naxoshomes.settings.Settings;
import me.gnat008.naxoshomes.settings.properties.CoreProperties;
import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public class NaxosHomes extends JavaPlugin
{

    private Settings settings;

    /*
     * Constructor for testing purposes.
     */
    protected NaxosHomes(final JavaPluginLoader loader, final PluginDescriptionFile description,
                                final File dataFolder, final File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onEnable()
    {
        long startTime = System.currentTimeMillis();
        ConsoleLogger.setLogger(getLogger());

        // Create the data folder
        if (!(new File(getDataFolder() + File.separator + "data").exists())) {
            new File(getDataFolder() + File.separator + "data").mkdirs();
        }

        /* Initialization */
        Injector injector = new InjectorBuilder().addDefaultHandlers("me.gnat008.naxoshomes").create();
        injector.register(NaxosHomes.class, this);
        injector.register(Server.class, getServer());
        injector.register(PluginManager.class, getServer().getPluginManager());
        this.settings = initSettings();
        ConsoleLogger.setUseDebug(settings.getProperty(CoreProperties.DEBUG_MODE));
        injectServices(injector);

        // register commands
        registerCommands(injector);

        ConsoleLogger.info("NaxosHomes is enabled! Initialization took " + (System.currentTimeMillis() - startTime) + "ms.");
    }

    @Override
    public void onDisable()
    {
        getServer().getScheduler().cancelTasks(this);
    }

    public void reload() {
        ConsoleLogger.setUseDebug(settings != null && settings.getProperty(CoreProperties.DEBUG_MODE));
    }

    private Settings initSettings()
    {
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveResource("config.yml", false);
        }
        return new Settings(configFile);
    }

    void injectServices(Injector injector)
    {

    }

    void registerCommands(Injector injector)
    {

    }
}
