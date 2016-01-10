package net.goodtwist.dev.grunt.db;

import net.goodtwist.dev.grunt.core.GameLink;

import java.util.Set;

/**
 * Created by Diego on 1/10/2016.
 */
public interface IGameLinkDAO {
    Set<GameLink> getGameLinks(String username);
    Set<GameLink> setGameLinks(String username, Set<GameLink> gameLink);
}
