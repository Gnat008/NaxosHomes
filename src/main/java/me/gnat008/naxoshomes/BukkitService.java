package me.gnat008.naxoshomes;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import javax.inject.Inject;

/**
 * Service for scheduling things with the Bukkit API.
 */
public class BukkitService
{

    /** Number of ticks per second in the Bukkit main thread. */
    public static final int TICKS_PER_SECOND = 20;
    /** Number of ticks per minutes. */
    public static final int TICKS_PER_MINUTE = TICKS_PER_SECOND * 60;

    private final NaxosHomes plugin;

    @Inject
    BukkitService(NaxosHomes plugin)
    {
        this.plugin = plugin;
    }

    /**
     * Runs a task on the next server tick and returns the task.
     *
     * @param task The task to run.
     * @return A BukkitTask with the ID number.
     */
    public BukkitTask runTask(Runnable task)
    {
        return Bukkit.getScheduler().runTask(plugin, task);
    }

    /**
     * Runs a task to be run asynchronously.
     *
     * @param task The task to run.
     * @return A BukkitTask with the ID number.
     */
    public BukkitTask runTaskAsync(Runnable task)
    {
        return Bukkit.getScheduler().runTaskAsynchronously(plugin, task);
    }
}
