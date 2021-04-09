package someshbose.github.io.app.config;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class SchedulerConfig {

    @NotNull private long initialDelayinMin;

    @NotNull private long delayInMin;
}
