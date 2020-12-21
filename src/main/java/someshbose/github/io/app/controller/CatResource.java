package someshbose.github.io.app.controller;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import someshbose.github.io.app.dto.CatDto;
import someshbose.github.io.app.objectStorage.ObjectStorageExample;

@Path("/cat")
@Produces(MediaType.APPLICATION_JSON)
public class CatResource {

  @POST
  @Path("/upload")
  public Response addCat(@QueryParam("name")String inputParam , CatDto dto) {
    try {
      ObjectStorageExample.main(inputParam,dto);
    } catch (InvalidKeyException | NoSuchAlgorithmException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return Response.accepted().build();
  }
  
  @GET
  public String getCats(@QueryParam("name")String inputParam) {
    try {
      return ObjectStorageExample.getCatList(inputParam).bucket();
    } catch (InvalidKeyException | NoSuchAlgorithmException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return "failed";
  }
  
}
