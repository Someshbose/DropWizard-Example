package someshbose.github.io.app.message;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

public class SaveOffsetsOnRebalance implements ConsumerRebalanceListener {
  private Consumer<?, ?> consumer;

/*  public SaveOffsetsOnRebalance(Consumer<?, ?> consumer) {
    this.consumer = consumer;
  }
  */
  private Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap();

  public void addOffset(String topic, int partition, long offset) {
    currentOffsets.put(new TopicPartition(topic, partition), new OffsetAndMetadata(offset, "Commit"));
  }

  public Map<TopicPartition, OffsetAndMetadata> getCurrentOffsets() {
    return currentOffsets;
  }

  public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
    // save the offsets in an external store using some custom code not described here
    System.out.println("Following Partitions Revoked ....");
    for (TopicPartition partition : partitions)
      System.out.println(partition.partition() + ",");


    System.out.println("Following Partitions commited ....");
    for (TopicPartition tp : currentOffsets.keySet())
      System.out.println(tp.partition());

    consumer.commitSync(currentOffsets);
    currentOffsets.clear();
  }

  public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
    // read the offsets from an external store using some custom code not described here
    System.out.println("Following Partitions Revoked ....");
    for (TopicPartition partition : partitions)
      System.out.println(partition.partition() + ",");


    System.out.println("Following Partitions commited ....");
    for (TopicPartition tp : currentOffsets.keySet())
      System.out.println(tp.partition());

    consumer.commitSync(currentOffsets);
    currentOffsets.clear();
  }
}
