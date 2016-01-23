package net.goodtwist.dev.grunt.db.mock;

import net.goodtwist.dev.grunt.core.Gamelink;
import net.goodtwist.dev.grunt.db.IGamelinkDAO;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Diego on 1/10/2016.
 */
public class GamelinkDAOMock implements IGamelinkDAO {

    Gamelink gameLink;

    public GamelinkDAOMock(){
        this.gameLink = new Gamelink();

        this.gameLink.setUsername("machopixel");
        this.gameLink.setGame(1);
        this.gameLink.setRegion("GLOBAL");
    }

    @Override
    public Set<Gamelink> getGamelinks(String username) {
        Set<Gamelink> result =  new HashSet<>();
        result.add(this.gameLink);
        return result;
    }

    @Override
    public Set<Gamelink> setGamelinks(String username, Set<Gamelink> gameLink) {
        Set<Gamelink> result =  new HashSet<>();
        result.add(this.gameLink);
        return result;
    }
}
