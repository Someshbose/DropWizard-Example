package someshbose.github.io.app.config;

import com.google.inject.AbstractModule;
import lombok.extern.slf4j.Slf4j;
import someshbose.github.io.app.controller.HelloWorldApplicationResource;

@Slf4j
public class HelloWorldApplicationModule extends AbstractModule {

    private final HelloWorldApplicationConfiguration config;

    public HelloWorldApplicationModule(HelloWorldApplicationConfiguration config){
        this.config=config;
    }
    @Override
    protected void configure() {
        log.info("Binding..");
        bind(HelloWorldApplicationConfiguration.class).toInstance(config);
        bind(HelloWorldApplicationResource.class).toInstance(new HelloWorldApplicationResource(config.getTemplate(),config.getDefaultName()));
    }
}
