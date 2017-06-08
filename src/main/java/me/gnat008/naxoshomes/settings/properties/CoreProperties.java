package me.gnat008.naxoshomes.settings.properties;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.properties.Property;

import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

/**
 * Holds the core settings for NaxosHomes.
 */
public final class CoreProperties implements SettingsHolder
{
    @Comment("Print out extra debug messages to the console")
    public static final Property<Boolean> DEBUG_MODE =
            newProperty("debug-mode", false);
}
