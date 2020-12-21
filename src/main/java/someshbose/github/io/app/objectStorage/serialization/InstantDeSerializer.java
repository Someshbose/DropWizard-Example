/*-
    =============================================================================
     Copyright (c) 2005, 2020 Oracle and/or its affiliates. All rights reserved.
    ================================================================================
*/
package someshbose.github.io.app.objectStorage.serialization;


import java.io.IOException;
import java.time.Instant;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * Deserializer for Instant
 * 
 * @author kasowmya
 *
 */
public class InstantDeSerializer extends JsonDeserializer<Instant> {
  /**
   * Instant Deserializer
   * 
   * @param arg0
   * @param arg1
   * @return Instant
   */
  @Override
  public Instant deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException {
    if (!arg0.getText().isEmpty()) {
      return Instant.parse(arg0.getText());
    } else {
      return null;
    }
  }
}
