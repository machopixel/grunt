package net.goodtwist.dev.grunt.db.mock;

import net.goodtwist.dev.grunt.core.GameLink;
import net.goodtwist.dev.grunt.db.IGameLinkDAO;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Diego on 1/10/2016.
 */
public class GameLinkDAOMock implements IGameLinkDAO {

    GameLink gameLink;

    public GameLinkDAOMock(){
        this.gameLink = new GameLink();

        this.gameLink.setUsername("machopixel");
        this.gameLink.setGame(1);
        this.gameLink.setRegion("GLOBAL");
    }

    @Override
    public Set<GameLink> getGameLinks(String username) {
        Set<GameLink> result =  new HashSet<>();
        result.add(this.gameLink);
        return result;
    }

    @Override
    public Set<GameLink> setGameLinks(String username, Set<GameLink> gameLink) {
        Set<GameLink> result =  new HashSet<>();
        result.add(this.gameLink);
        return result;
    }
}
