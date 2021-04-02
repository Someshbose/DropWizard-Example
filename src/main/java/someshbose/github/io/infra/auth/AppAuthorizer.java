package someshbose.github.io.infra.auth;

import io.dropwizard.auth.Authorizer;
import someshbose.github.io.domain.model.User;

public class AppAuthorizer implements Authorizer<User>
{
    @Override
    public boolean authorize(User user, String role) {
        return user.getRoles() != null && user.getRoles().contains(role);
    }
}
