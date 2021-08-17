package someshbose.github.io.app.health;

import com.codahale.metrics.health.HealthCheck;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HelloWorldApplicationHealthCheckTest {

    private HelloWorldApplicationHealthCheck healthCheck;
    @BeforeEach
    public void setUp(){
        healthCheck= new HelloWorldApplicationHealthCheck(new String("%s test"));
    }

    @Test
    public void checkHealthTest() throws Exception {
        Assertions.assertEquals(HealthCheck.Result.healthy().isHealthy(),healthCheck.check().isHealthy());
    }
}
