package someshbose.github.io.app.objectStorage.serialization;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Custom OffsetDateTime serializer which enables flexibility to represent dates out of our API's to
 * comply with ISO 8601 with RFC 3339 is the International Standard for the representation of dates
 * and times. For more info , please see https://tools.ietf.org/html/rfc3339
 * 
 * @author caruruiz
 *
 */
public class CustomDateSerializer extends JsonSerializer<OffsetDateTime> {

  public static final DateTimeFormatter ISO_8601_DATE_FORMAT = DateTimeFormatter
      .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").withZone(ZoneId.of("UTC"));

  @Override
  public void serialize(OffsetDateTime value, JsonGenerator gen, SerializerProvider provider)
      throws IOException, JsonProcessingException { // NOSONAR This is
                                                    // part of method
                                                    // signature.
    gen.writeString(ISO_8601_DATE_FORMAT.format(value));
  }
}
