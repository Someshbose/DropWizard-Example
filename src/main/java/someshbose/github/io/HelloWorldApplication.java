package someshbose.github.io;

import io.dropwizard.Application;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import someshbose.github.io.app.config.HelloWorldConfiguration;
import someshbose.github.io.app.controller.HelloWorldResource;
import someshbose.github.io.app.health.TemplateHealthCheck;

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {

  public static void main(String[] args) throws Exception {
    new HelloWorldApplication().run("server", "example.yml");
  }

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
    
    final TemplateHealthCheck healthCheck=new TemplateHealthCheck(configuration.getTemplate());
    
    environment.healthChecks().register("template", healthCheck);
    environment.jersey().register(resource);
  }

}
