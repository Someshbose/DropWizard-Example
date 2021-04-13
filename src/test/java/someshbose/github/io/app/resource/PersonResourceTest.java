package someshbose.github.io.app.resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import someshbose.github.io.domain.dao.PersonDao;

public class PersonResourceTest {

    @InjectMocks
    private PersonResource resource;

    @Mock
    private PersonDao dao;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllTest(){

    }

    @Test
    void addTest(){

    }
}
