package me.gnat008.naxoshomes.settings;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.migration.PlainMigrationService;
import ch.jalu.configme.resource.YamlFileResource;
import me.gnat008.naxoshomes.settings.properties.CoreProperties;

import java.io.File;

/**
 * Settings class for NaxosHomes properties.
 */
public class Settings extends SettingsManager
{

    public Settings(File yamlFile)
    {
        super(
                new YamlFileResource(yamlFile),
                new PlainMigrationService(),
                CoreProperties.class
        );
    }
}
