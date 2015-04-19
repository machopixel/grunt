package net.goodtwist.dev.grunt;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.goodtwist.dev.grunt.core.Challenge;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.core.UserAuthentication;
import net.goodtwist.dev.grunt.core.UserFriends;
import net.goodtwist.dev.grunt.db.IChallengeDAO;
import net.goodtwist.dev.grunt.db.IUserAccountDAO;
import net.goodtwist.dev.grunt.db.IUserAuthenticationDAO;
import net.goodtwist.dev.grunt.db.IUserFriendsDAO;
import net.goodtwist.dev.grunt.db.h2.ChallengeDAOH2;
import net.goodtwist.dev.grunt.db.h2.UserAccountDAOH2;
import net.goodtwist.dev.grunt.db.h2.UserAuthenticationDAOH2;
import net.goodtwist.dev.grunt.db.h2.UserFriendsDAOH2;
import net.goodtwist.dev.grunt.resources.AccountListFriendsResource;
import net.goodtwist.dev.grunt.resources.AccountSignInResource;
import net.goodtwist.dev.grunt.resources.AccountSignUpResource;
import net.goodtwist.dev.grunt.resources.ChallengeCreateResource;

public class GruntApplication extends Application<GruntConfiguration> {

	public static void main(String[] args) throws Exception {
		new GruntApplication().run(args);
	}

	private final HibernateBundle<GruntConfiguration> hibernateBundle = 
		new HibernateBundle<GruntConfiguration>(UserAccount.class, 
												UserAuthentication.class, 
												UserFriends.class, 
												Challenge.class) {
			@Override
			public DataSourceFactory getDataSourceFactory(GruntConfiguration configuration) {
				return configuration.getDataSourceFactory();
			}
	};

	@Override
	public String getName() {
		return "grunt";
	}

	@Override
	public void initialize(Bootstrap<GruntConfiguration> bootstrap) {
		bootstrap.addBundle(new MigrationsBundle<GruntConfiguration>() {
			@Override
			public DataSourceFactory getDataSourceFactory(
					GruntConfiguration configuration) {
				return configuration.getDataSourceFactory();
			}
		});
		bootstrap.addBundle(hibernateBundle);
	}

	@Override
	public void run(GruntConfiguration configuration, Environment environment) {
		final IUserAccountDAO userAccountDAO = new UserAccountDAOH2(hibernateBundle.getSessionFactory());
		final IChallengeDAO challengeDAO = new ChallengeDAOH2(hibernateBundle.getSessionFactory());
		final IUserAuthenticationDAO userAuthenticationDAO = new UserAuthenticationDAOH2(hibernateBundle.getSessionFactory());
		final IUserFriendsDAO userFriendsDAO = new UserFriendsDAOH2(hibernateBundle.getSessionFactory());

		environment.jersey().register(new AccountSignInResource(userAccountDAO));
		environment.jersey().register(new AccountSignUpResource(userAccountDAO));
		environment.jersey().register(new AccountListFriendsResource(userAccountDAO, userAuthenticationDAO, userFriendsDAO));
		environment.jersey().register(new ChallengeCreateResource(userAccountDAO, challengeDAO));
	}
}