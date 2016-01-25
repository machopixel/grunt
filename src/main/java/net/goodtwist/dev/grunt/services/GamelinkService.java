package net.goodtwist.dev.grunt.services;

import net.goodtwist.dev.grunt.core.Gamelink;

import java.util.LinkedList;

/**
 * Created by Diego on 1/23/2016.
 */
public class GamelinkService {

    private boolean isUsernameValid(String username){
        if (!TextService.isCharOnly(username)){
            return false;
        }
        return true;
    }

    private boolean isGameValid(int game){
        switch(game){
            case 1001: return true;
            case 1002: return true;
        }
        return false;
    }

    private boolean isRegionValid(int game, String region){
        switch(game){
            case 1001:
                        if ("EUW".equals(region) || "NA".equals(region)){
                            return true;
                        }
            case 1002:
                        if ("GLOBAL".equals(region)){
                            return true;
                        }
        }
        return  false;
    }

    public LinkedList<String> isGamelinkValid(Gamelink gamelink){
        LinkedList<String> result = new LinkedList<>();
        if (!isUsernameValid(gamelink.getUsername())){
            result.add(ErrorService.INVALID_USERNAME);
        }
        if (!isGameValid(gamelink.getGame())){
            result.add(ErrorService.INVALID_GAME);
        }
        if (!isRegionValid(gamelink.getGame(), gamelink.getRegion())){
            result.add(ErrorService.INVALID_REGION);
        }

        return result;
    }
}
