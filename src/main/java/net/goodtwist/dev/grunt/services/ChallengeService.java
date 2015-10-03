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

    public Challenge createNewChallenge(Challenge challenge, UserAccount creatorUserAccount){
        challenge.setId(UUID.randomUUID());
        challenge.setCreator(creatorUserAccount.getUsername());

        return challenge;
    }

    public LinkedList<String> isChallengeValid(Challenge challenge){
        LinkedList<String> result = new LinkedList<String>();

        Optional<UserAccount> participantA;
        Optional<UserAccount> participantB;

        if (!challenge.getCreator().equals(challenge.getParticipantA())) {
            participantA = this.userAccountDAO.findByUsername(challenge.getParticipantA());

            if (!participantA.isPresent()){
                result.add("INVALID PARTICIPANT A");
            }
        }

        if (!challenge.getCreator().equals(challenge.getParticipantB())){
            participantB = this.userAccountDAO.findByUsername(challenge.getParticipantB());

            if (!participantB.isPresent()){
                result.add("INVALID PARTICIPANT B");
            }
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

    public int accept(Challenge challenge, UserAccount userAccount){
        if (challenge.getParticipantA().equals(userAccount.getUsername())){
           challenge.setJoinDateA((int)(new Date().getTime() / 1000));
            return 0;
        }
        if (challenge.getParticipantB().equals(userAccount.getUsername())){
            challenge.setJoinDateB((int)(new Date().getTime() / 1000));
            return 1;
        }

        return -1;
    }
}
