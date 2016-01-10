package net.goodtwist.dev.grunt.external.mandrill;

import net.goodtwist.dev.grunt.external.IEmailService;

import javax.ws.rs.core.Response;
import java.util.UUID;

/**
 * Created by Diego on 1/10/2016.
 */
public class EmailServiceMandrill implements IEmailService {
    @Override
    public Response.Status sendEmailRecoveryTemplate(String email, String username, UUID template) {
        return Response.Status.OK;
    }
}
