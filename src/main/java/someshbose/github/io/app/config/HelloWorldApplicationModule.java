package someshbose.github.io.app.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.dropwizard.hibernate.HibernateBundle;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import someshbose.github.io.app.controller.HelloWorldApplicationResource;
import someshbose.github.io.app.service.Scheduled;
import someshbose.github.io.app.service.SchedulerService;

@Slf4j
public class HelloWorldApplicationModule extends AbstractModule {

    private final HelloWorldApplicationConfiguration config;
    HibernateBundle hibernate;

    public HelloWorldApplicationModule(HelloWorldApplicationConfiguration config,HibernateBundle hibernate){
        this.config=config;
        this.hibernate=hibernate;
    }
    @Override
    protected void configure() {
        log.info("Binding..");
        bind(HelloWorldApplicationConfiguration.class).toInstance(config);
        bind(HelloWorldApplicationResource.class).toInstance(new HelloWorldApplicationResource(config.getTemplate(),config.getDefaultName()));

        //Scheduler bindings
        bind(Scheduled.class).in(Singleton.class);
        bind(SchedulerService.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    private SessionFactory getHibernateSession(){
        return hibernate.getSessionFactory();
    }
}
