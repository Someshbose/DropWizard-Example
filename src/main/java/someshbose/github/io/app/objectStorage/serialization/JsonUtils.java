/*-
    =============================================================================
     Copyright (c) 2005, 2018 Oracle and/or its affiliates. All rights reserved.

    ================================================================================
*/
package someshbose.github.io.app.objectStorage.serialization;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Provides utilities to manipulate Java objects and Json elements.
 */
public class JsonUtils {

  private ObjectMapper mapper;

  public JsonUtils(){
    JsonConfiguration config= new JsonConfiguration();
    ObjectMapper mapper=config.customJson();
    this.mapper=mapper;
  }
  
  /**
   * @param object
   *          to transform into a InputStream
   *
   * @return a {@link InputStream} representation of Object.
   */
  public InputStream writeObjectToInputStream(Object object) {

    try {
      String str = mapper.writeValueAsString(object);
      return new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
    } catch (JsonProcessingException e) {
      throw new UncheckedIOException("JSON serialization error", e);
    }

  }

  /**
   * @param stream
   *          {@lin InputStream} representation of an Object of type T
   * @param type
   *          class
   * @param <T>
   *          type
   *
   * @return Object of type <T>
   */
  public <T> T readObjectFromStream(InputStream stream, Class<T> type) {
    try {
      return mapper.readValue(stream, type);
    } catch (IOException e) {
      throw new UncheckedIOException("JSON deserialization error", e);
    }
  }

  /**
   * @param string
   *          a String representation of an Object of type T
   * @param type
   *          class
   * @param <T>
   *          type
   *
   * @return Object of type <T>
   */
  public <T> T readObjectFromString(String string, Class<T> type) {
    try {
      return mapper.readValue(string, type);
    } catch (IOException e) {
      throw new UncheckedIOException("JSON deserialization error", e);
    }
  }
}
