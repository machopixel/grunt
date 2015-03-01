package net.goodtwist.dev.grunt;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.db.UserAccountDAO;
import net.goodtwist.dev.grunt.resources.SignInResource;
import net.goodtwist.dev.grunt.resources.SignUpResource;

public class GruntApplication extends Application<GruntConfiguration> {

	public static void main(String[] args) throws Exception {
		new GruntApplication().run(args);
	}

	private final HibernateBundle<GruntConfiguration> hibernateBundle = 
		new HibernateBundle<GruntConfiguration>(UserAccount.class) {
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
		final UserAccountDAO userAccountDAO = new UserAccountDAO(hibernateBundle.getSessionFactory());

		environment.jersey().register(new SignInResource(userAccountDAO));
		environment.jersey().register(new SignUpResource(userAccountDAO));
	}
}