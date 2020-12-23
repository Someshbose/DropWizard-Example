package someshbose.github.io.app.config;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.kafka.KafkaConsumerFactory;
import io.dropwizard.kafka.KafkaProducerFactory;
import lombok.Getter;

@Getter
public class KafkaConfiguration extends Configuration{
  
  @Valid
  @NotNull
  @JsonProperty("producer")
  private KafkaProducerFactory<String, String> kafkaProducerFactory;
  
  @Valid
  @NotNull
  @JsonProperty("consumer")
  private KafkaConsumerFactory<String, String> kafkaConsumerFactory;

}
