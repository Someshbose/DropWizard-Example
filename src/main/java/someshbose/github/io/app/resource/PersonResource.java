package someshbose.github.io.app.resource;

import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import io.dropwizard.hibernate.UnitOfWork;
import someshbose.github.io.domain.dao.PersonDao;
import someshbose.github.io.domain.model.Person;

@Path("/person")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class PersonResource {

    private PersonDao personDAO;

    @Inject
    public PersonResource(PersonDao personDAO) {
        this.personDAO = personDAO;
    }

    @GET
    @UnitOfWork
    public List<Person> getAll(){
        return personDAO.getAll();
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Person get(@PathParam("id") Integer id){
        return personDAO.findById(id);
    }

    @POST
    @UnitOfWork
    public Person add(@Valid Person person) {
        Person newPerson = personDAO.insert(person);

        return newPerson;
    }

    @PUT
    @Path("/{id}")
    @UnitOfWork
    public Person update(@PathParam("id") Integer id, @Valid Person person) {
        person = person.setId(id);
        personDAO.update(person);

        return person;
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public void delete(@PathParam("id") Integer id) {
        personDAO.delete(personDAO.findById(id));
    }
}
