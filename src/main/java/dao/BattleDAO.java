package dao;

import java.util.List;

/**
 * Data access operations for dao.Battle entities.
 *
 * @author Sebastian Guillen
 * @since 4/20/26
 * @version 0.1.0
 */
public interface BattleDAO {

    void insertBattle(dao.Battle battle);

    void updateBattle(dao.Battle battle);

    void deleteBattle(int battleId);

    Battle getBattleById(int battleId);

    List<dao.Battle> getBattlesByNpcName(String npcName);
}
