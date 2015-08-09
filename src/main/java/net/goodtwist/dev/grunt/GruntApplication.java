package net.goodtwist.dev.grunt;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.goodtwist.dev.grunt.cassandra.CassandraManager;
import net.goodtwist.dev.grunt.db.IChallengeDAO;
import net.goodtwist.dev.grunt.db.IUserAccountDAO;
import net.goodtwist.dev.grunt.db.cassandra.UserAccountDAOCassandra;
import net.goodtwist.dev.grunt.db.cassandra.ChallengeDAOCassandra;
import net.goodtwist.dev.grunt.resources.UserAccountFriendsListResource;
import net.goodtwist.dev.grunt.resources.UserAccountResource;
import net.goodtwist.dev.grunt.resources.ChallengeResource;

public class GruntApplication extends Application<GruntConfiguration> {

	public static void main(String[] args) throws Exception {
		new GruntApplication().run(args);
	}

	@Override
	public String getName() {
		return "grunt";
	}

	@Override
	public void initialize(Bootstrap<GruntConfiguration> bootstrap) {
	}

	@Override
	public void run(GruntConfiguration configuration, Environment environment) {
		CassandraManager cassandraManager = new CassandraManager();

		final IUserAccountDAO userAccountDAO = new UserAccountDAOCassandra(cassandraManager);
		final IChallengeDAO challengeDAO = new ChallengeDAOCassandra();

		environment.jersey().register(new UserAccountResource(userAccountDAO));
		environment.jersey().register(new UserAccountFriendsListResource(userAccountDAO));
		environment.jersey().register(new ChallengeResource(userAccountDAO, challengeDAO));
	}
}