package net.goodtwist.dev.grunt;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.goodtwist.dev.grunt.cassandra.CassandraManager;
import net.goodtwist.dev.grunt.db.IChallengeDAO;
import net.goodtwist.dev.grunt.db.IUserAccountDAO;
import net.goodtwist.dev.grunt.db.cassandra.UserAccountDAOCassandra;
import net.goodtwist.dev.grunt.db.cassandra.ChallengeDAOCassandra;
import net.goodtwist.dev.grunt.resources.SecurityResource;
import net.goodtwist.dev.grunt.resources.UserAccountResource;
import net.goodtwist.dev.grunt.resources.ChallengeResource;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;

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
		environment.jersey().register(new AbstractBinder() {
			@Override
			protected void configure() {
				bind(CassandraManager.class).to(CassandraManager.class).in(Singleton.class);
				bind(UserAccountDAOCassandra.class).to(IUserAccountDAO.class).in(Singleton.class);
				bind(ChallengeDAOCassandra.class).to(IChallengeDAO.class).in(Singleton.class);
			}
		});

		environment.jersey().register(new UserAccountResource());
		environment.jersey().register(new ChallengeResource());
		environment.jersey().register(new SecurityResource());
	}
}