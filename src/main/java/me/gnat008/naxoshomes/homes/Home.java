package me.gnat008.naxoshomes.homes;

import org.bukkit.Location;

/**
 * Class that represents a player's home.
 */
public class Home
{

    private String name;
    private String world;
    private double x, y, z;
    private float pitch, yaw;

    public Home(String name, Location location)
    {
        this.name = name;
        this.world = location.getWorld().getName();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.pitch = location.getPitch();
        this.yaw = location.getYaw();
    }

    /**
     * Get the name of this home.
     *
     * @return The name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Get the name of the world this home is in.
     *
     * @return The world's name
     */
    public String getWorld()
    {
        return world;
    }

    /**
     * Get the X coordinate of this home.
     *
     * @return The X coordinate
     */
    public double getX()
    {
        return x;
    }

    /**
     * Get the Y coordinate of this home.
     *
     * @return The Y coordinate
     */
    public double getY()
    {
        return y;
    }

    /**
     * Get the Z coordinate of this home.
     *
     * @return The Z coordinate
     */
    public double getZ()
    {
        return z;
    }

    /**
     * Get the pitch of the Player's head for this home.
     *
     * @return The Player's pitch
     */
    public float getPitch()
    {
        return pitch;
    }

    /**
     * Get the yaw of the Player's head for this home.
     *
     * @return The Player's yaw
     */
    public float getYaw()
    {
        return yaw;
    }
}
