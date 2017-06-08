package me.gnat008.naxoshomes;

import ch.jalu.configme.properties.Property;
import ch.jalu.injector.Injector;
import ch.jalu.injector.InjectorBuilder;
import me.gnat008.naxoshomes.settings.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.scheduler.BukkitScheduler;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import static me.gnat008.naxoshomes.TestUtils.setField;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Initialization tests for {@link NaxosHomes}.
 */
@RunWith(MockitoJUnitRunner.class)
@Ignore
public class NaxosHomesInitializationTest
{

    private NaxosHomes plugin;

    @Mock
    private PluginManager pluginManager;

    @Mock
    private Server server;

    @Mock
    private Settings settings;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private File dataFolder;

    @Before
    public void setUpPlugin() throws IOException
    {
        dataFolder = temporaryFolder.newFolder();

        // Wire various Bukkit components
        setField(Bukkit.class, "server", null, server);
        given(server.getLogger()).willReturn(mock(Logger.class));
        given(server.getScheduler()).willReturn(mock(BukkitScheduler.class));
        given(server.getPluginManager()).willReturn(pluginManager);
        given(server.getVersion()).willReturn("1.9.4-RC1");

        // SettingsManager always returns the default
        given(settings.getProperty(any(Property.class))).willAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return ((Property<?>) invocation.getArguments()[0]).getDefaultValue();
            }
        });

        // PluginDescriptionFile is final and so cannot be mocked
        PluginDescriptionFile descriptionFile = new PluginDescriptionFile(
                "NaxosHomes", "N/A", NaxosHomes.class.getCanonicalName());
        JavaPluginLoader pluginLoader = new JavaPluginLoader(server);
        plugin = new NaxosHomes(pluginLoader, descriptionFile, dataFolder, null);
        setField(JavaPlugin.class, "logger", plugin, mock(PluginLogger.class));
    }

    @Test
    public void shouldInitializeAllServices()
    {
        // given
        Injector injector = new InjectorBuilder().addDefaultHandlers("me.gnat008.naxoshomes").create();
        injector.register(NaxosHomes.class, plugin);
        injector.register(Server.class, server);
        injector.register(PluginManager.class, pluginManager);
        injector.register(Settings.class, settings);
        //injector.registerProvider(DataSource.class, DataSourceProvider.class);

        // when
        plugin.injectServices(injector);
        plugin.registerCommands(injector);

        // then - check various samples
        //TODO: assertThat(injector.getIfAvailable(DataSource.class), instanceOf(FlatFile.class));

        //TODO: CommandVerifier commandVerifier = new CommandVerifier(plugin, injector);
        //TODO: commandVerifier.assertHasCommand("naxoshomes", NaxosHomesCommand.class);
        //TODO: commandVerifier.assertHasCommand("reload", ReloadCommand.class);
    }

    /*private static final class CommandVerifier
    {

        private final Injector injector;
        private final Map<String, ExecutableCommand> commands;

        CommandVerifier(NaxosHomes plugin, Injector injector)
        {
            this.injector = injector;
            this.commands = getField(NaxosHomes.class, "commands", plugin);
        }

        void assertHasCommand(String label, Class<? extends ExecutableCommand> expectedClass)
        {
            ExecutableCommand command = commands.get(label);
            assertThat(command, not(nullValue()));
            assertThat(command, sameInstance(injector.getIfAvailable(expectedClass)));
        }
    }*/
}
