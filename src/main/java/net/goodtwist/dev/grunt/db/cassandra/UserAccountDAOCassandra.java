package net.goodtwist.dev.grunt.db.cassandra;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.BuiltStatement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.google.common.base.Optional;
import net.goodtwist.dev.grunt.cassandra.CassandraManager;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.db.IUserAccountDAO;
import org.eclipse.jetty.server.Authentication;

import static com.datastax.driver.core.querybuilder.QueryBuilder.in;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
            BuiltStatement query = select().all()
                                           .from("goodtwist", "useraccount")
                                           .where(eq("username", username));

            ResultSet resultSet = cassandraManager.executeQuery(query);
            List<Row> resultList = resultSet.all();

            if (resultList.size() == 1){
                Row row = resultList.get(0);
                UserAccount userAccount = new UserAccount();
                userAccount.setUsername(row.getString("username"));
                userAccount.setEmail(row.getString("email"));
                userAccount.setPassword(row.getString("password"));
                userAccount.setFriends(getFriends(row.getSet("friends", String.class)));
                return Optional.of(userAccount);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return Optional.absent();
    }

    @Override
    public Set<UserAccount> getFriends(Set<String> friends) {
        Set<UserAccount> result = new HashSet<UserAccount>();

        try {
            BuiltStatement query  = select().all()
                                            .from("goodtwist", "useraccount")
                                            .where(in("username", friends.toArray()));

            ResultSet resultSet = cassandraManager.executeQuery(query);
            List<Row> resultList = resultSet.all();

            for (Row row:resultList){
                UserAccount userAccount = new UserAccount();
                userAccount.setUsername(row.getString("username"));
                userAccount.setOnlineStatus(row.getBool("username"));
                result.add(userAccount);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return result;
    }

    @Override
    public Optional<UserAccount> create(UserAccount userAccount) {
        try{
            BuiltStatement query = QueryBuilder.insertInto("goodtwist", "useraccount")
                                               .values(this.getFieldsAsArrayForUserAccountTable(),
                                                       this.getValuesAsArrayForUserAccountTable(userAccount));

            ResultSet resultSet = cassandraManager.executeQuery(query);
        }catch(Exception e){
            return Optional.absent();
        }
        return this.findByUsername(userAccount.getUsername());
    }


    public Object[] getValuesAsArrayForUserAccountTable(UserAccount userAccount){
        Object[] result = new Object[5];
        result[0] = userAccount.getUsername();
        result[1] = userAccount.getPassword();
        result[2] = userAccount.getEmail();

        Set<UserAccount> friends = userAccount.getFriends();
        Set<String> friendsString = new HashSet<String>();
        for(UserAccount userAccountFor:friends) {
            friendsString.add(userAccountFor.getUsername());
        }

        result[3] = friendsString;
        result[4] = userAccount.getOnlineStatus();

        return result;
    }

    public String[] getFieldsAsArrayForUserAccountTable(){
        String[] result = new String[5];
        result[0] = "username";
        result[1] = "password";
        result[2] = "email";
        result[3] = "friends";
        result[4] = "onlinestatus";

        return result;
    }
}
