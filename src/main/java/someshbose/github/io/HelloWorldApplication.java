package someshbose.github.io;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;
import someshbose.github.io.app.config.HelloWorldApplicationModule;
import someshbose.github.io.app.config.HelloWorldApplicationConfiguration;
import someshbose.github.io.app.controller.HelloWorldApplicationResource;
import someshbose.github.io.app.health.HelloWorldApplicationHealthCheck;

@Slf4j
public class HelloWorldApplication extends Application<HelloWorldApplicationConfiguration> {

  private static final String SERVICE_NAME = "Hello-World-Application";

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
    super.initialize(bootstrap);
  }

  @Override
  public void run(HelloWorldApplicationConfiguration configuration, Environment environment) throws Exception {

    log.info("Initializing {}...",SERVICE_NAME);

    log.info("Configuring Guice Injector");
    Injector injector = Guice.createInjector(new HelloWorldApplicationModule(configuration));
    registerResources(injector,environment,configuration);
    registerHealthCheck(environment,configuration);

  }

  private void registerResources(Injector injector, Environment environment, HelloWorldApplicationConfiguration configuration){
    //final HelloWorldResource resource =
      //      new HelloWorldResource(configuration.getTemplate(), configuration.getDefaultName());
    environment.jersey().register(injector.getInstance(HelloWorldApplicationResource.class));

  }

  private void registerHealthCheck(Environment environment, HelloWorldApplicationConfiguration configuration){
    environment.healthChecks().register("template", new HelloWorldApplicationHealthCheck(configuration.getTemplate()));
  }

}
