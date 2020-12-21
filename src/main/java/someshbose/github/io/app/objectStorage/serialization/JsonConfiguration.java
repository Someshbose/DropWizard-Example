package someshbose.github.io.app.objectStorage.serialization;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.TimeZone;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonConfiguration {

  /**
   * @return a {@link ObjectMapper}
   */
  public ObjectMapper customJson() {
    ObjectMapper objectMapper = new ObjectMapper();

    objectMapper.setTimeZone(TimeZone.getTimeZone("UTC"));
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.setSerializationInclusion(Include.ALWAYS);
    objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

    objectMapper.setFilterProvider(new SimpleFilterProvider().setFailOnUnknownId(false));

    SimpleModule simpleModule = new SimpleModule();
    simpleModule.addSerializer(OffsetDateTime.class, new CustomDateSerializer());
    simpleModule.addDeserializer(Instant.class, new InstantDeSerializer());
    objectMapper.registerModule(simpleModule);

    return objectMapper;
  }

}

