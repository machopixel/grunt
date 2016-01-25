package net.goodtwist.dev.grunt.db;

import com.google.common.base.Optional;
import net.goodtwist.dev.grunt.core.Gamelink;

import java.util.Set;

/**
 * Created by Diego on 1/10/2016.
 */
public interface IGamelinkDAO {
    Set<Gamelink> getGamelinks(String username);
    Optional<Gamelink> create(Gamelink gameLink);
    boolean delete(Gamelink gameLink);
}
