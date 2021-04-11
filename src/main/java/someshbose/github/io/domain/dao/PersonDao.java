package someshbose.github.io.domain.dao;

import java.util.List;
import org.hibernate.SessionFactory;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.query.Query;
import someshbose.github.io.domain.model.Person;

import javax.inject.Inject;

public class PersonDao extends AbstractDAO<Person> {
  @Inject
  public PersonDao(SessionFactory factory) {
      super(factory);
  }

  public List<Person> getAll() {
      return list((Query<Person>)  namedQuery("someshbose.github.io.model.Person.findAll"));
  }

  public Person findById(int id) {
      return (Person) currentSession().get(Person.class, id);
  }

  public void delete(Person person) {
      currentSession().delete(person);
  }

  public void update(Person person) {
      currentSession().saveOrUpdate(person);
  }

  public Person insert(Person person) {
      return persist(person);
  }
}