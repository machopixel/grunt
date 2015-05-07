package net.goodtwist.dev.grunt.test.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Optional;

import io.dropwizard.testing.junit.ResourceTestRule;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.core.UserAuthentication;
import net.goodtwist.dev.grunt.core.UserFriends;
import net.goodtwist.dev.grunt.db.h2.UserAccountDAOH2;
import net.goodtwist.dev.grunt.db.h2.UserAuthenticationDAOH2;
import net.goodtwist.dev.grunt.db.h2.UserFriendsDAOH2;
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
	private static final UserFriendsDAOH2 userFriendsDAO = mock(UserFriendsDAOH2.class);
	    
    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new AccountListFriendsResource(userAccountDAO, userAuthenticationDAO, userFriendsDAO))
            .build();
    
    private UserAccount userAccount;
    private Optional<UserAccount> oUserAccount;
    private UserAuthentication userAuthentication;
    private List<UserAuthentication> authList;
    private UserFriends userFriends;
    private List<UserFriends> frientList;

    @Before
    public void setUp() {

        userAuthentication = new UserAuthentication();
        userAuthentication.setAccountId(0);
        
        authList = new LinkedList<UserAuthentication>();
        authList.add(userAuthentication);
        
        userFriends = new UserFriends();
        userFriends.setFriendAccountId(0);
        
        frientList = new LinkedList<UserFriends>();
        frientList.add(userFriends);
    	
    	userAccount = new UserAccount();
        userAccount.setUsername("user_test");
        userAccount.setPassword("pass_test");
        userAccount.setEmail("email_test");
        userAccount.setId(0);
    	oUserAccount = Optional.of(userAccount);
    }

    @After
    public void tearDown() {
        reset(userAccountDAO);
        reset(userAuthenticationDAO);
        reset(userFriendsDAO);
    }

    @Test
    public void ValidAccountListFriendsTest() throws JsonProcessingException {
        when(userAuthenticationDAO.findByToken(any(String.class))).thenReturn(authList);
        when(userFriendsDAO.listByAccountId(any(Long.class))).thenReturn(frientList);
        when(userAccountDAO.findById(any(Long.class))).thenReturn(oUserAccount);
        final Response response = resources.client().target("/api/account/friends")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(userAccount, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
    }
}
