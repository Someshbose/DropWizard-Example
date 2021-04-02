package someshbose.github.io.app.controller;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.auth.Auth;
import someshbose.github.io.domain.model.Saying;
import someshbose.github.io.domain.model.User;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldApplicationResource {

  private final String template;
  private final String defaultName;
  private final AtomicLong counter;

  @Inject
  public HelloWorldApplicationResource(String template, String defaultName) {
      this.template = template;
      this.defaultName = defaultName;
      this.counter = new AtomicLong();
  }

  @GET
  @RolesAllowed({ "ADMIN"})
  @Timed
  public Saying sayHello(@QueryParam("name") Optional<String> name, @Auth User user) {
      final String value = String.format(template, name.orElse(defaultName));
      return new Saying(counter.incrementAndGet(), value);
  }
}
