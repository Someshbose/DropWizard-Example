package someshbose.github.io.app.objectStorage.serialization;

import java.io.IOException;
import java.time.Instant;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Serializer for Instant
 * 
 * @author kasowmya
 *
 */


public class InstantSerializer extends JsonSerializer<Instant> {

  @Override
  public void serialize(Instant arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException {
    arg1.writeString(arg0.toString());
  }

}
