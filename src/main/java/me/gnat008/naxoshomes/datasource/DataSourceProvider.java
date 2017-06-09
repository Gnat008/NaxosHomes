package me.gnat008.naxoshomes.datasource;

import ch.jalu.injector.Injector;
import me.gnat008.naxoshomes.ConsoleLogger;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * Creates the data source.
 */
public class DataSourceProvider implements Provider<DataSource>
{

    private Injector injector;

    @Inject
    DataSourceProvider(Injector injector)
    {
        this.injector = injector;
    }

    @Override
    public DataSource get()
    {
        try
        {
            return createDataSource();
        }
        catch (Exception ex)
        {
            ConsoleLogger.severe("Unable to create data source:", ex);
            throw new IllegalStateException("Error during initialization of data source", ex);
        }
    }

    private DataSource createDataSource()
    {
        DataSourceType type = DataSourceType.FLATFILE;
        DataSource dataSource;

        switch (type)
        {
            case FLATFILE:
                dataSource = injector.getSingleton(FlatFile.class);
                break;
            default:
                throw new UnsupportedOperationException("Unknown data source type '" + type + "'");
        }

        return dataSource;
    }
}
