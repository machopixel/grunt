package net.goodtwist.dev.grunt.external;

import javax.ws.rs.core.Response;
import java.util.UUID;

/**
 * Created by Diego on 1/10/2016.
 */
public interface IEmailService {
    Response.Status sendEmailRecoveryTemplate(String email, String username, UUID template);
}
