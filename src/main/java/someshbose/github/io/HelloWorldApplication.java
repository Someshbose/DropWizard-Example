package someshbose.github.io;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import someshbose.github.io.app.config.HelloWorldApplicationModule;
import someshbose.github.io.app.config.HelloWorldApplicationConfiguration;
import someshbose.github.io.app.controller.HelloWorldApplicationResource;
import someshbose.github.io.app.health.HelloWorldApplicationHealthCheck;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.hibernate.HibernateBundle;
import someshbose.github.io.app.controller.PersonResource;
import someshbose.github.io.domain.model.Person;
import someshbose.github.io.domain.model.User;
import someshbose.github.io.infra.auth.AppAuthorizer;
import someshbose.github.io.infra.auth.AppBasicAuthenticator;

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
    registerAuthentication(environment);
  }

  private void registerResources(Injector injector, Environment environment, HelloWorldApplicationConfiguration configuration){
    environment.jersey().register(injector.getInstance(HelloWorldApplicationResource.class));
    environment.jersey().register(injector.getInstance(PersonResource.class));
  }

  private void registerHealthCheck(Environment environment, HelloWorldApplicationConfiguration configuration){
    environment.healthChecks().register("HelloWorldApplicationHealthCheck", new HelloWorldApplicationHealthCheck(configuration.getTemplate()));
  }

  /****** Dropwizard security - custom classes ***********/
  private void registerAuthentication(Environment environment){
    environment.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
            .setAuthenticator(new AppBasicAuthenticator())
            .setAuthorizer(new AppAuthorizer())
            .setRealm("BASIC-AUTH-REALM")
            .buildAuthFilter()));
    environment.jersey().register(RolesAllowedDynamicFeature.class);
    environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
  }
  
}
