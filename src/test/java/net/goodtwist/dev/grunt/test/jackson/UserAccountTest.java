package net.goodtwist.dev.grunt.test.jackson;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.*;
import io.dropwizard.jackson.Jackson;
import net.goodtwist.dev.grunt.core.UserAccount;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserAccountTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void serializeToJSON() throws Exception {
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername("user_test");
        userAccount.setPassword("pass_test");
        userAccount.setEmail("email_test");

        final String expected = MAPPER.writeValueAsString(MAPPER.readValue(fixture("fixtures/useraccount.json"), UserAccount.class));

        assertThat(MAPPER.writeValueAsString(userAccount)).isEqualTo(expected);
    }
    
    @Test
    public void deserializeFromJSON() throws Exception {
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername("user_test");
        userAccount.setPassword("pass_test");
        userAccount.setEmail("email_test");
        
        assertThat(MAPPER.readValue(fixture("fixtures/useraccount.json"), UserAccount.class)).isEqualToComparingFieldByField(userAccount);
    }
}