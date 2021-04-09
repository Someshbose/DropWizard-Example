package someshbose.github.io.app.service;

import com.google.common.util.concurrent.AbstractScheduledService;
import com.google.inject.Inject;
import io.dropwizard.lifecycle.Managed;
import lombok.extern.slf4j.Slf4j;
import someshbose.github.io.app.config.HelloWorldApplicationConfiguration;

import java.util.concurrent.TimeUnit;

@Slf4j
public class SchedulerService extends AbstractScheduledService implements Managed {

    private final Scheduled scheduled;
    private final HelloWorldApplicationConfiguration config;

    @Inject
    public SchedulerService(Scheduled scheduled , HelloWorldApplicationConfiguration config){
        this.scheduled=scheduled;
        this.config=config;
    }

    @Override
    protected void runOneIteration() throws Exception {
      log.info("Running scheduler...");
      scheduled.run();
    }

    @Override
    protected Scheduler scheduler() {
        return Scheduler.newFixedDelaySchedule(config.getSchedulerConfig().getInitialDelayinMin(),config.getSchedulerConfig().getDelayInMin(), TimeUnit.MINUTES);
    }

    @Override
    public void start() throws Exception {
        log.info("Scheduler started...");
        this.startAsync();
    }

    @Override
    public void stop() throws Exception {
        log.info("Scheduler stopped...");
        this.stopAsync();
    }
}
