package someshbose.github.io;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;
import someshbose.github.io.app.config.HelloWorldApplicationModule;
import someshbose.github.io.app.config.HelloWorldApplicationConfiguration;
import someshbose.github.io.app.health.HelloWorldApplicationHealthCheck;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.hibernate.HibernateBundle;
import someshbose.github.io.app.resource.HelloWorldApplicationResource;
import someshbose.github.io.app.resource.PersonResource;
import someshbose.github.io.app.service.SchedulerService;

@Slf4j
public class HelloWorldApplication extends Application<HelloWorldApplicationConfiguration> {

  private static final String SERVICE_NAME = "Hello-World-Application";

  private final HibernateBundle<HelloWorldApplicationConfiguration> hibernate = new HibernateBundle<HelloWorldApplicationConfiguration>(Person.class) {
    @Override
    public DataSourceFactory getDataSourceFactory(HelloWorldApplicationConfiguration configuration) {
      return configuration.getDatabaseAppDataSourceFactory();
    }
  };

  public static void main(String[] args) throws Exception {
    //new HelloWorldApplication().run("server","local.yml");
    log.info("Starting {}..",SERVICE_NAME);
    new HelloWorldApplication().run(args);
  }
  @Override
  public String getName() {
    return SERVICE_NAME;
  }

  @Override
  public void initialize(Bootstrap<HelloWorldApplicationConfiguration> bootstrap) {
    log.info("Bootstrapping {}...",SERVICE_NAME);
    //bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
    bootstrap.addBundle(hibernate);

    bootstrap.addBundle(new MigrationsBundle<HelloWorldApplicationConfiguration>() {
      @Override
      public DataSourceFactory getDataSourceFactory(HelloWorldApplicationConfiguration configuration) {
        return configuration.getDatabaseAppDataSourceFactory();
      }
    });
    super.initialize(bootstrap);
  }
  @Override
  public void run(HelloWorldApplicationConfiguration configuration, Environment environment) throws Exception {

    log.info("Initializing {}...",SERVICE_NAME);

    log.info("Configuring Guice Injector");
    Injector injector = Guice.createInjector(new HelloWorldApplicationModule(configuration,hibernate));
    registerResources(injector,environment,configuration);
    registerHealthCheck(environment,configuration);
    //Managed
    manage(environment,injector, SchedulerService.class);

  }

  private void registerResources(Injector injector, Environment environment, HelloWorldApplicationConfiguration configuration){
    environment.jersey().register(injector.getInstance(HelloWorldApplicationResource.class));
    environment.jersey().register(injector.getInstance(PersonResource.class));
  }

  private void registerHealthCheck(Environment environment, HelloWorldApplicationConfiguration configuration){
    environment.healthChecks().register("HelloWorldApplicationHealthCheck", new HelloWorldApplicationHealthCheck(configuration.getTemplate()));
  }

  private void manage(Environment environment, Injector injector, Class<? extends Managed> clazz){
    Managed managed=injector.getInstance(clazz);
    environment.lifecycle().manage(managed);
  }
  
}
