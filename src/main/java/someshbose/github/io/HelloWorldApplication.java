package someshbose.github.io;

import io.dropwizard.Application;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import someshbose.github.io.app.config.ExampleConfiguration;
import someshbose.github.io.app.controller.PersonResource;
import someshbose.github.io.app.dao.PersonDAO;
import someshbose.github.io.model.Person;

public class HelloWorldApplication extends Application<ExampleConfiguration> {

  public static void main(String[] args) throws Exception {
    new HelloWorldApplication().run(args);
  }
  /*
  @Override
  public String getName() {
    return "Hello-world";
  }

  @Override
  public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
    bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
    super.initialize(bootstrap);
  }
  @Override
  public void run(HelloWorldConfiguration configuration, Environment environment) throws Exception {
    final HelloWorldResource resource =
        new HelloWorldResource(configuration.getTemplate(), configuration.getDefaultName());

    final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());

    environment.healthChecks().register("template", healthCheck);
    environment.jersey().register(resource);
  }
*/
  
  private final HibernateBundle<ExampleConfiguration> hibernate = new HibernateBundle<ExampleConfiguration>(Person.class) {
    @Override
    public DataSourceFactory getDataSourceFactory(ExampleConfiguration configuration) {
        return configuration.getDatabaseAppDataSourceFactory();
    }
  };
  
  @Override
  public String getName() {
      return "dropwizard-hibernate";
  }

  @Override
  public void initialize(Bootstrap<ExampleConfiguration> bootstrap) {
    bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());  
    bootstrap.addBundle(hibernate);
    bootstrap.addBundle(new MigrationsBundle<ExampleConfiguration>() {
      @Override
      public DataSourceFactory getDataSourceFactory(ExampleConfiguration configuration) {
          return configuration.getDatabaseAppDataSourceFactory();
      }
    });
    /*
    bootstrap.addBundle(new FlywayBundle<ExampleConfiguration>() {
      @Override
      public DataSourceFactory getDataSourceFactory(ExampleConfiguration configuration) {
          return configuration.getDataSourceFactory();
      }
      
      @Override
      public FlywayFactory getFlywayFactory(ExampleConfiguration configuration) {
          return configuration.getFlywayFactory();
      }
  });
  */
  }

  @Override
  public void run(ExampleConfiguration configuration, Environment environment) throws ClassNotFoundException {

      final PersonDAO personDAO = new PersonDAO(hibernate.getSessionFactory());

      final PersonResource personResource = new PersonResource(personDAO);

      environment.jersey().register(personResource);
  }
  
  
}
