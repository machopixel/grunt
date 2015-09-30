package net.goodtwist.dev.grunt.resources.factories;

import net.goodtwist.dev.grunt.core.UserAccount;
import org.glassfish.hk2.api.Factory;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;

public class UserAccountFactory implements Factory<UserAccount> {

    private final ContainerRequestContext context;

    @Inject
    public UserAccountFactory(ContainerRequestContext context) {
        this.context = context;
    }

    @Override
    public UserAccount provide() {
        return (UserAccount)context.getProperty("userAccount");
    }

    @Override
    public void dispose(UserAccount t) {}
}
