package net.goodtwist.dev.grunt.services;

import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.db.IUserAccountDAO;

public class UserAccountService {

    private IUserAccountDAO userAccountDAO;

    public UserAccountService(IUserAccountDAO userAccountDAO){
        this.userAccountDAO = userAccountDAO;
    }

    public boolean isNewUserAccountValid(UserAccount userAccount){
        return true;
    }
}
