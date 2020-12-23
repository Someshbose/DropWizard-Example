package someshbose.github.io;

import java.util.ArrayList;
import java.util.List;
import org.apache.kafka.clients.consumer.Consumer;
import io.dropwizard.Application;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.kafka.KafkaConsumerBundle;
import io.dropwizard.kafka.KafkaConsumerFactory;
import io.dropwizard.kafka.KafkaProducerBundle;
import io.dropwizard.kafka.KafkaProducerFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import someshbose.github.io.app.config.KafkaConfiguration;
import someshbose.github.io.app.controller.PersonEventResource;
import someshbose.github.io.app.message.PersonEventConsumer;
import someshbose.github.io.app.message.PersonEventProducer;
import someshbose.github.io.app.message.SaveOffsetsOnRebalance;

public class HelloWorldApplication extends Application<KafkaConfiguration> {

  public static void main(String[] args) throws Exception {
    sample.add("Sample-topic");
    new HelloWorldApplication().run("server", "config.yml");
  }

  /*
   * @Override public String getName() { return "Hello-world"; }
   * 
   * @Override public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
   * bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider()); super.initialize(bootstrap); }
   * 
   * @Override public void run(HelloWorldConfiguration configuration, Environment environment) throws Exception { final
   * HelloWorldResource resource = new HelloWorldResource(configuration.getTemplate(), configuration.getDefaultName());
   * 
   * final TemplateHealthCheck healthCheck=new TemplateHealthCheck(configuration.getTemplate());
   * 
   * environment.healthChecks().register("template", healthCheck); environment.jersey().register(resource); }
   */
  
  public static List<String> sample= new ArrayList<String>();
  
  private final KafkaProducerBundle<String, String, KafkaConfiguration> kafkaProducer =
      new KafkaProducerBundle<String, String, KafkaConfiguration>(sample) {
        @Override
        public KafkaProducerFactory<String, String> getKafkaProducerFactory(KafkaConfiguration configuration) {
          return configuration.getKafkaProducerFactory();
        }
      };

  private final KafkaConsumerBundle<String, String, KafkaConfiguration> kafkaConsumer =
      new KafkaConsumerBundle<String, String, KafkaConfiguration>(sample,new SaveOffsetsOnRebalance()) {
        @Override
        public KafkaConsumerFactory<String, String> getKafkaConsumerFactory(KafkaConfiguration configuration) {
          return configuration.getKafkaConsumerFactory();
        }
      };
  @Override
  public void initialize(Bootstrap<KafkaConfiguration> bootstrap) {
    bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
    bootstrap.addBundle(kafkaProducer);
    bootstrap.addBundle(kafkaConsumer);
  }

  @Override
  public void run(KafkaConfiguration config, Environment environment) {
    final PersonEventProducer personEventProducer = new PersonEventProducer(kafkaProducer.getProducer());
    environment.jersey().register(new PersonEventResource(personEventProducer));
    final PersonEventConsumer personEventConsumer = new PersonEventConsumer(kafkaConsumer.getConsumer());
    personEventConsumer.startConsuming();
  }
}
