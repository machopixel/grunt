package net.goodtwist.dev.grunt.db.cassandra;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.BuiltStatement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.google.common.base.Optional;
import net.goodtwist.dev.grunt.cassandra.CassandraManager;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.db.IUserAccountDAO;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Diego on 8/6/2015.
 */
public class UserAccountDAOCassandra implements IUserAccountDAO{

    private CassandraManager cassandraManager;

    public UserAccountDAOCassandra(CassandraManager newCassandraManager){
        this.cassandraManager = newCassandraManager;
    }

    @Override
    public Optional<UserAccount> findByUsername(String username) {
        try{
            BuiltStatement query = QueryBuilder.select()
                                               .all()
                                               .from("goodtwist", "useraccount")
                                               .where(QueryBuilder.eq("username", username));

            ResultSet resultSet = cassandraManager.executeQuery(query);
        }catch(Exception e){
            System.out.println(e);
        }
        return Optional.of(new UserAccount());
    }

    @Override
    public Optional<UserAccount> create(UserAccount userAccount) {
        try{
            BuiltStatement query = QueryBuilder.insertInto("goodtwist", "useraccount")
                                               .values(this.getFieldsAsArrayForUserAccountTable(),
                                                       this.getValuesAsArrayForUserAccountTable(userAccount));

            ResultSet resultSet = cassandraManager.executeQuery(query);
        }catch(Exception e){
            System.out.println(e);
        }
        return Optional.of(new UserAccount());
    }


    public Object[] getValuesAsArrayForUserAccountTable(UserAccount userAccount){
        Object[] result = new Object[4];
        result[0] = userAccount.getUsername();
        result[1] = userAccount.getPassword();
        result[2] = userAccount.getEmail();

        Set<UserAccount> friends = userAccount.getFriends();
        Set<String> friendsString = new HashSet<String>();
        for(UserAccount userAccountFor:friends) {
            friendsString.add(userAccountFor.getUsername());
        }

        result[3] = friendsString;

        return result;
    }

    public String[] getFieldsAsArrayForUserAccountTable(){
        String[] result = new String[4];
        result[0] = "username";
        result[1] = "password";
        result[2] = "email";
        result[3] = "friends";

        return result;
    }
}
