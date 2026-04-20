package org.turnbasedtitans.project2.dao;

import org.turnbasedtitans.project2.model.Battle;

import java.util.List;

/**
 * Data access operations for Battle entities.
 *
 * @author Sebastian Guillen
 * @since 4/20/26
 * @version 0.1.0
 */
public interface BattleDAO {

    void insertBattle(Battle battle);

    void updateBattle(Battle battle);

    void deleteBattle(int battleId);

    Battle getBattleById(int battleId);

    List<Battle> getBattlesByNpcName(String npcName);
}
