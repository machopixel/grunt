package net.goodtwist.dev.grunt.test.resources;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.dropwizard.testing.junit.ResourceTestRule;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.core.UserAuthentication;
import net.goodtwist.dev.grunt.db.h2.UserAccountDAOH2;
import net.goodtwist.dev.grunt.db.h2.UserAuthenticationDAOH2;
import net.goodtwist.dev.grunt.resources.GetTokenResource;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetTokenResourceTest {
	private static final UserAccountDAOH2 userAccountDAO = mock(UserAccountDAOH2.class);
	private static final UserAuthenticationDAOH2 userAuthenticationDAO = mock(UserAuthenticationDAOH2.class);
    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new GetTokenResource(userAccountDAO, userAuthenticationDAO))
            .build();
    
    private UserAccount userAccount;
    private List<UserAccount> userAccountList;
    private UserAuthentication userAuthentication;

    @Before
    public void setUp() {
        userAuthentication = new UserAuthentication();
    	
    	userAccount = new UserAccount();
        userAccount.setUsername("user_test");
        userAccount.setPassword("pass_test");
        userAccount.setEmail("email_test");
        userAccount.addUserAuthentications(userAuthentication);
        
        userAccountList = new LinkedList<UserAccount>();
        userAccountList.add(userAccount);
    }

    
    @After
    public void tearDown() {
        reset(userAccountDAO);
        reset(userAuthenticationDAO);
    }

    @Test
    public void ValidSignInTest() throws JsonProcessingException {
        when(userAccountDAO.findByEqualUsername(any(String.class))).thenReturn(userAccountList);
        final Response response = resources.client().target("/api/security/get-token")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(userAccount, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
    }
}