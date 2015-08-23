package net.goodtwist.dev.grunt.services;

import com.google.common.base.Optional;
import net.goodtwist.dev.grunt.core.Challenge;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.db.IUserAccountDAO;

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
}
