package net.goodtwist.dev.grunt.db.cassandra;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.BuiltStatement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import net.goodtwist.dev.grunt.cassandra.CassandraManager;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.db.IUserAccountDAO;

import javax.inject.Inject;

import static com.datastax.driver.core.querybuilder.QueryBuilder.in;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;

import java.util.*;

public class UserAccountDAOCassandra implements IUserAccountDAO{

    @Inject private CassandraManager cassandraManager;

    public UserAccountDAOCassandra(){
    }

    @Override
    public Optional<UserAccount> findByUsername(String username) {
        Optional result = Optional.absent();
        try{
            BuiltStatement query = select().all()
                                           .from("goodtwist", "useraccount")
                                           .where(eq("username", username));

            ResultSet resultSet = cassandraManager.executeQuery(query);
            List<Row> resultList = resultSet.all();

            if (resultList.size() == 1){
                Row row = resultList.get(0);
                result = Optional.of(handleRow(row, false));
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return result;
    }

    @Override
    public Map<String, UserAccount> findMultipleByUsernames(String[] usernames)
    {
        Map<String, UserAccount> result = new HashMap<String, UserAccount>();

        try {
            BuiltStatement query  = select().all()
                                            .from("goodtwist", "useraccount")
                                            .where(in("username", usernames));
            ResultSet resultSet = cassandraManager.executeQuery(query);
            List<Row> resultList = resultSet.all();

            for (Row row:resultList){
                result.put(row.getString("username"), this.handleRow(row, false));
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return result;
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
                result.add(this.handleRow(row, true));
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
                                                       this.getValuesAsArrayForUserAccountTable(userAccount))
                                               .ifNotExists();
            ResultSet resultSet = cassandraManager.executeQuery(query);

            List<Row> resultList = resultSet.all();

            if (resultList.size() == 1) {
                if (resultList.get(0) != null && (resultList.get(0).getBool("[applied]") == true)) {
                    return this.findByUsername(userAccount.getUsername());
                }
            }
        }catch(Exception e){
        }
        return Optional.absent();
    }

    public UserAccount handleRow(Row row, boolean isPartial){
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(row.getString("username"));
        userAccount.setOnlineStatus(row.getBool("onlinestatus"));
        if (!isPartial)
        {
            userAccount.setEmail(row.getString("email"));
            userAccount.setPassword(row.getString("password"));
            userAccount.setFriends(getFriends(row.getSet("friends", String.class)));
            userAccount.setMembershipStatus(row.getInt("membershipstatus"));
        }
        return userAccount;
    }

    public Object[] getValuesAsArrayForUserAccountTable(UserAccount userAccount){
        Object[] result = new Object[7];
        result[0] = userAccount.getUsername().toLowerCase();
        result[1] = userAccount.getPassword();

        if (!Strings.isNullOrEmpty(userAccount.getEmail())) {
            result[2] = userAccount.getEmail().toLowerCase();
        }

        Set<UserAccount> friends = userAccount.getFriends();
        Set<String> friendsString = new HashSet<String>();
        for(UserAccount userAccountFor:friends) {
            friendsString.add(userAccountFor.getUsername());
        }
        result[3] = friendsString;

        result[4] = userAccount.getOnlineStatus();
        result[5] = userAccount.getMembershipStatus();
        result[6] = userAccount.getConfirmationKey();

        return result;
    }

    public String[] getFieldsAsArrayForUserAccountTable(){
        String[] result = new String[7];
        result[0] = "username";
        result[1] = "password";
        result[2] = "email";
        result[3] = "friends";
        result[4] = "onlinestatus";
        result[5] = "membershipstatus";
        result[6] = "confirmationkey";

        return result;
    }
}
