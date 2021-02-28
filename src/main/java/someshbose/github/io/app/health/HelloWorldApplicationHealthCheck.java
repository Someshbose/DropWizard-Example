package someshbose.github.io.app.health;

import com.codahale.metrics.health.HealthCheck;

public class HelloWorldApplicationHealthCheck extends HealthCheck {

  private final String template;

  public HelloWorldApplicationHealthCheck(String template) {
    this.template = template;
  }

  @Override
  protected Result check() throws Exception {

    final String saying = String.format(template, "TEST");

    if (!saying.contains("TEST")) {
      return Result.unhealthy("HelloWorldApplicationHealthCheck doesn't include a name");
    }
    return Result.healthy();
  }

}
