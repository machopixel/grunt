package net.goodtwist.dev.grunt.services;

import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.db.IUserAccountDAO;

import java.util.LinkedList;
import java.util.UUID;

public class UserAccountService {

    private IUserAccountDAO userAccountDAO;

    public UserAccountService(IUserAccountDAO newUserAccountDAO){
        this.userAccountDAO = newUserAccountDAO;
    }

    public UserAccount createNewUserAccount(UserAccount userAccount){
        userAccount.setMembershipStatus(5);
        userAccount.addConfirmationKey(UUID.randomUUID());

        return userAccount;
    }

    private boolean isUsernameValid(String username){
        if (!TextService.isCharOnly(username)){
            return false;
        }
        return true;
    }

    private boolean isPasswordValid(String password){
        if (password.length() < 8){
            return false;
        }
        return true;
    }

    public LinkedList<String> isUserAccountValid(UserAccount userAccount){
        LinkedList<String> result = new LinkedList<String>();
        if (!isUsernameValid(userAccount.getUsername())){
            result.add("INVALID USERNAME");
        }
        if (!isPasswordValid(userAccount.getPassword())){
            result.add("INVALID PASSWORD");
        }

        return result;
    }
}
