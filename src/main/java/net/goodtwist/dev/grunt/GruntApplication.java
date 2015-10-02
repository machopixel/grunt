package net.goodtwist.dev.grunt;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.goodtwist.dev.grunt.cassandra.CassandraManager;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.db.IChallengeDAO;
import net.goodtwist.dev.grunt.db.ITransactionDAO;
import net.goodtwist.dev.grunt.db.IUserAccountDAO;
import net.goodtwist.dev.grunt.db.cassandra.TransactionDAOCassandra;
import net.goodtwist.dev.grunt.db.cassandra.UserAccountDAOCassandra;
import net.goodtwist.dev.grunt.db.cassandra.ChallengeDAOCassandra;
import net.goodtwist.dev.grunt.health.TestHealthCheck;
import net.goodtwist.dev.grunt.resources.SecurityResource;
import net.goodtwist.dev.grunt.resources.UserAccountResource;
import net.goodtwist.dev.grunt.resources.ChallengeResource;
import net.goodtwist.dev.grunt.resources.factories.UserAccountFactory;
import net.goodtwist.dev.grunt.resources.filters.RegistrationRequiredFilter;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;

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
				bind(TransactionDAOCassandra.class).to(ITransactionDAO.class).in(Singleton.class);
			}
		});

		environment.jersey().register(new UserAccountResource());
		environment.jersey().register(new ChallengeResource());
		environment.jersey().register(new SecurityResource());
		environment.jersey().register(new RegistrationRequiredFilter());
		environment.healthChecks().register("test", new TestHealthCheck());
		environment.jersey().register(new AbstractBinder() {
			@Override
			protected void configure() {
				bindFactory(UserAccountFactory.class)
						.to(UserAccount.class)
						.in(RequestScoped.class);
			}
		});
	}
}