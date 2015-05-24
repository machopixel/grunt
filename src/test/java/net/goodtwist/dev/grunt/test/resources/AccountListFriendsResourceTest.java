package net.goodtwist.dev.grunt.test.resources;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.dropwizard.testing.junit.ResourceTestRule;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.core.UserAuthentication;
import net.goodtwist.dev.grunt.db.h2.UserAccountDAOH2;
import net.goodtwist.dev.grunt.db.h2.UserAuthenticationDAOH2;
import net.goodtwist.dev.grunt.resources.AccountListFriendsResource;

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
public class AccountListFriendsResourceTest {
	private static final UserAccountDAOH2 userAccountDAO = mock(UserAccountDAOH2.class);
	private static final UserAuthenticationDAOH2 userAuthenticationDAO = mock(UserAuthenticationDAOH2.class);
	    
    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new AccountListFriendsResource(userAccountDAO, userAuthenticationDAO))
            .build();
    
    private UserAccount userAccount;
    private UserAccount friendUserAccount;
    private UserAuthentication userAuthentication;
    private List<UserAuthentication> authList;

    @Before
    public void setUp() {
    	userAccount = new UserAccount();
    	friendUserAccount = new UserAccount();
    	
    	userAccount.addFriendUserAccount(friendUserAccount);

        userAuthentication = new UserAuthentication();
        userAuthentication.setAccount(userAccount);
        
        authList = new LinkedList<UserAuthentication>();
        authList.add(userAuthentication);
    	
    }

    @After
    public void tearDown() {
        reset(userAccountDAO);
        reset(userAuthenticationDAO);
    }

    @Test
    public void ValidAccountListFriendsTest() throws JsonProcessingException {
        when(userAuthenticationDAO.findByToken(any(String.class))).thenReturn(authList);
        final Response response = resources.client().target("/api/account/friends")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", "test-token")
                .post(Entity.entity(userAccount, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
    }
}
