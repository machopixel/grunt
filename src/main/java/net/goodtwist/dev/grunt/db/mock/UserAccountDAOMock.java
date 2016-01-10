package net.goodtwist.dev.grunt.db.mock;

import com.google.common.base.Optional;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.db.IUserAccountDAO;

import java.util.*;

/**
 * Created by USUARIO on 15/12/2015.
 */
public class UserAccountDAOMock implements IUserAccountDAO {

    UserAccount newUserAccount;

    public UserAccountDAOMock(){

        newUserAccount = new UserAccount();

        newUserAccount.setUsername("machopixel");
        newUserAccount.setConfirmationKey(UUID.randomUUID());
        newUserAccount.setEmail("dila@itu.dk");
        List<String> friends = new ArrayList<>();
        friends.add("sup3rs0pas");
        newUserAccount.setFriends(friends);
        newUserAccount.setMembershipStatus(4);
        newUserAccount.setOnlineStatus(true);
        newUserAccount.setPassword("password");
    }

    @Override
    public Optional<UserAccount> findByUsername(String username) {
        return Optional.of(newUserAccount);
    }

    @Override
    public List<UserAccount> searchByUsername(String username, int limit) {
        List<UserAccount> result = new LinkedList<>();
        result.add(newUserAccount);

        return result;
    }

    @Override
    public Optional<UserAccount> findByEmail(String email) {
        return Optional.of(newUserAccount);
    }

    @Override
    public Optional<UserAccount> create(UserAccount userAccount) {
        return Optional.of(newUserAccount);
    }

    @Override
    public Optional<UserAccount> update(UserAccount userAccount) {
        return Optional.of(newUserAccount);
    }

    @Override
    public Optional<UserAccount> updateFriends(UserAccount userAccount) {
        return Optional.of(newUserAccount);
    }

    @Override
    public List<UserAccount> getFriends(List<String> friends) {
        List<UserAccount> result = new LinkedList<>();
        result.add(newUserAccount);

        return result;
    }
}
