package someshbose.github.io.domain.dao;

import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import someshbose.github.io.domain.model.Person;

@ExtendWith(DropwizardExtensionsSupport.class)
public class PersonDaoTest {

    public DAOTestExtension daoTestExtension = DAOTestExtension.newBuilder()
            .addEntityClass(Person.class).build();
    private PersonDao personDao;

    @BeforeEach
    public void setUp(){
        personDao = new PersonDao(daoTestExtension.getSessionFactory());
    }

    @Test
    public void findByIdTest(){
        Person person = Person.builder().id(1).name("Test").build();

        daoTestExtension.inTransaction(
                ()->{
                    personDao.insert(person);
                }
        );

        Person actual=personDao.findById(1);

        Assertions.assertEquals(person,actual);
    }
}
