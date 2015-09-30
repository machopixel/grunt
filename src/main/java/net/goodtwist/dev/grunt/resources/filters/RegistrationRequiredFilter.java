package net.goodtwist.dev.grunt.resources.filters;

import com.google.common.base.Strings;
import com.google.common.base.Optional;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.db.IUserAccountDAO;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.Map;

@Provider
@RegistrationRequired
@Priority(Priorities.AUTHENTICATION)
public class RegistrationRequiredFilter  implements ContainerRequestFilter{

    @Inject
    private IUserAccountDAO userAccountDAO;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        Map<String, Cookie> cookies = containerRequestContext.getCookies();

        if ((cookies.get("security") != null) && (!Strings.isNullOrEmpty(cookies.get("security").getValue()))){

            Optional<UserAccount> userAccount = this.userAccountDAO.findByUsername(cookies.get("security").getValue());

            if (userAccount.isPresent()){
                containerRequestContext.setProperty("userAccount", userAccount);
            }else{
                containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }

        }else{
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}
