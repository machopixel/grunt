package net.goodtwist.dev.grunt;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.db.IChallengeDAO;
import net.goodtwist.dev.grunt.db.IGameLinkDAO;
import net.goodtwist.dev.grunt.db.ITransactionDAO;
import net.goodtwist.dev.grunt.db.IUserAccountDAO;
import net.goodtwist.dev.grunt.cassandra.CassandraManager;
import net.goodtwist.dev.grunt.db.cassandra.ChallengeDAOCassandra;
import net.goodtwist.dev.grunt.db.cassandra.GameLinkDAOCassandra;
import net.goodtwist.dev.grunt.db.cassandra.TransactionDAOCassandra;
import net.goodtwist.dev.grunt.db.cassandra.UserAccountDAOCassandra;
/*
import net.goodtwist.dev.grunt.db.mock.ChallengeDAOMock;
import net.goodtwist.dev.grunt.db.mock.GameLinkDAOMock;
import net.goodtwist.dev.grunt.db.mock.TransactionDAOMock;
import net.goodtwist.dev.grunt.db.mock.UserAccountDAOMock;
*/
import net.goodtwist.dev.grunt.external.IEmailService;
import net.goodtwist.dev.grunt.external.mandrill.EmailServiceMandrill;
import net.goodtwist.dev.grunt.health.TestHealthCheck;
import net.goodtwist.dev.grunt.resources.*;
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
				// Only required by Cassandra.
				bind(CassandraManager.class).to(CassandraManager.class).in(Singleton.class);

				// Cassandra DAO
				bind(UserAccountDAOCassandra.class).to(IUserAccountDAO.class).in(Singleton.class);
				bind(ChallengeDAOCassandra.class).to(IChallengeDAO.class).in(Singleton.class);
				bind(TransactionDAOCassandra.class).to(ITransactionDAO.class).in(Singleton.class);
				bind(GameLinkDAOCassandra.class).to(IGameLinkDAO.class).in(Singleton.class);

				// MOCK DAO
				/*
				bind(UserAccountDAOMock.class).to(IUserAccountDAO.class).in(Singleton.class);
				bind(ChallengeDAOMock.class).to(IChallengeDAO.class).in(Singleton.class);
				bind(TransactionDAOMock.class).to(ITransactionDAO.class).in(Singleton.class);
				bind(IGameLinkDAO.class).to(GameLinkDAOMock.class).in(Singleton.class);
				*/
				bind(EmailServiceMandrill.class).to(IEmailService.class).in(Singleton.class);
			}
		});

		environment.jersey().register(new UserAccountResource());
		environment.jersey().register(new ChallengeResource());
		environment.jersey().register(new SecurityResource());
		environment.jersey().register(new FriendsResource());
		environment.jersey().register(new SearchResource());
		environment.jersey().register(new TransactionResource());
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