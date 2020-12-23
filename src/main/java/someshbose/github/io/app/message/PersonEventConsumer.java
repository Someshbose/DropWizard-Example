package someshbose.github.io.app.message;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import someshbose.github.io.HelloWorldApplication;

public class PersonEventConsumer {
  private final Consumer<String, String> kafkaConsumer;

  public PersonEventConsumer(Consumer<String, String> kafkaConsumer) {
    this.kafkaConsumer = kafkaConsumer;
  }

  public void startConsuming() {
    kafkaConsumer.subscribe(HelloWorldApplication.sample);
    ConsumerRecords<String, String> records = kafkaConsumer.poll(100);

    for (ConsumerRecord<String, String> record : records) {
      System.out.println(record.value());
    }
  }
}
