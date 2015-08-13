package net.goodtwist.dev.grunt.resources.filters;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.Map;

/**
 * Created by Diego on 8/12/2015.
 */
@Provider
@RegistrationRequired
@Priority(Priorities.AUTHENTICATION)
public class RegistrationRequiredFilter  implements ContainerRequestFilter{
    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        Map<String, Cookie> cookies = containerRequestContext.getCookies();

        if (cookies.get("security") != null){

        }else{
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}
