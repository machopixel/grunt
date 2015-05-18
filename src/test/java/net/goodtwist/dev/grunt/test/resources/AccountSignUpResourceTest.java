package net.goodtwist.dev.grunt.test.resources;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.dropwizard.testing.junit.ResourceTestRule;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.db.h2.UserAccountDAOH2;
import net.goodtwist.dev.grunt.resources.AccountSignUpResource;

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
public class AccountSignUpResourceTest {
    private static final UserAccountDAOH2 dao = mock(UserAccountDAOH2.class);
    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new AccountSignUpResource(dao))
            .build();
    
    private UserAccount userAccount;
    private List<UserAccount> userAccountList;

    @Before
    public void setUp() {
    	userAccount = new UserAccount();
        userAccount.setUsername("user_test");
        userAccount.setPassword("pass_test");
        userAccount.setEmail("email_test");
        
        userAccountList = new LinkedList<UserAccount>();
    }

    @After
    public void tearDown() {
        reset(dao);
    }

    @Test
    public void ValidSignUpTest() throws JsonProcessingException {
        when(dao.findByEqualUsername(any(String.class))).thenReturn(userAccountList);
        when(dao.create(any(UserAccount.class))).thenReturn(userAccount);
        final Response response = resources.client().target("/api/account/sign-up")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(userAccount, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
    }
}