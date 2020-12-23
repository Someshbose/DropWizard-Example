package someshbose.github.io.app.controller;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import someshbose.github.io.app.message.PersonEventProducer;

@Path("/publish")
public class PersonEventResource {

  private final PersonEventProducer personEventProducer;

  public PersonEventResource(PersonEventProducer personEventProducer) {
    this.personEventProducer = personEventProducer;
  }

  @POST
  public void send() {
    personEventProducer.sendMessage("Hi");
  }
}
