package someshbose.github.io.app.message;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class PersonEventProducer {

  private final Producer<String, String> kafkaProducer;

  public PersonEventProducer(Producer<String, String> producer) {
    this.kafkaProducer = producer;
  }

  public void sendMessage(String message) {
    kafkaProducer.send(new ProducerRecord<String, String>("Sample-topic", "Hi Kafka!"));
  }

}
