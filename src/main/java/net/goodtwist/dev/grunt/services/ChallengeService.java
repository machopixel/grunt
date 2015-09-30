package net.goodtwist.dev.grunt.services;

import com.google.common.base.Optional;
import net.goodtwist.dev.grunt.core.Challenge;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.db.IUserAccountDAO;

import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

public class ChallengeService {

    private IUserAccountDAO userAccountDAO;

    public ChallengeService(IUserAccountDAO newUserAccountDAO){
        this.userAccountDAO = newUserAccountDAO;
    }

    public Challenge createNewChallenge(Challenge challenge){
        challenge.setId(UUID.randomUUID());

        return challenge;
    }

    public LinkedList<String> isChallengeValid(Challenge challenge){
        LinkedList<String> result = new LinkedList<String>();

        Optional<UserAccount> creator = this.userAccountDAO.findByUsername(challenge.getCreator());
        Optional<UserAccount> participantA;
        Optional<UserAccount> participantB;

        if (creator.isPresent() && (challenge.getCreator().equals(challenge.getParticipantA()))) {
            participantA = creator;
        } else {
            participantA = this.userAccountDAO.findByUsername(challenge.getParticipantA());
        }

        if (creator.isPresent() && (challenge.getCreator().equals(challenge.getParticipantB()))){
            participantB = creator;
        }else{
            participantB = this.userAccountDAO.findByUsername(challenge.getParticipantB());
        }

        if (!creator.isPresent()){
            result.add("INVALID CREATOR");
        }
        if (!participantA.isPresent()){
            result.add("INVALID PARTICIPANT A");
        }
        if (!participantB.isPresent()){
            result.add("INVALID PARTICIPANT B");
        }

        return result;
    }

    public boolean isParticipant(Challenge challenge, UserAccount userAccount){
        if (challenge.getParticipantA().equals(userAccount.getUsername())){
            return true;
        }
        if (challenge.getParticipantB().equals(userAccount.getUsername())){
            return true;
        }

        return false;
    }

    public void accept(Challenge challenge, UserAccount userAccount){
        if (challenge.getParticipantA().equals(userAccount.getUsername())){
           challenge.setJoinDateA((int)(new Date().getTime() / 1000));
        }
        if (challenge.getParticipantB().equals(userAccount.getUsername())){
            challenge.setJoinDateB((int)(new Date().getTime() / 1000));
        }
    }
}
