package net.goodtwist.dev.grunt.test.jackson;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.*;
import io.dropwizard.jackson.Jackson;
import net.goodtwist.dev.grunt.core.Challenge;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ChallengeTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void serializeToJSON() throws Exception {
    	Challenge challenge = new Challenge();
    	challenge.setGameId(1001);
    	challenge.setCreator("Diego");
        challenge.setParticipantA("machopixel");
        challenge.setParticipantB("superalan");

        final String expected = MAPPER.writeValueAsString(MAPPER.readValue(fixture("fixtures/challenge.json"), Challenge.class));

        assertThat(MAPPER.writeValueAsString(challenge)).isEqualTo(expected);
    }
    
    @Test
    public void deserializeFromJSON() throws Exception {
    	Challenge challenge = new Challenge();
    	challenge.setGameId(1001);
        challenge.setCreator("Diego");
        challenge.setParticipantA("machopixel");
        challenge.setParticipantB("superalan");
        
        assertThat(MAPPER.readValue(fixture("fixtures/challenge.json"), Challenge.class)).isEqualToComparingFieldByField(challenge);
    }
}