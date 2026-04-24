//package org.turnbasedtitans.project2.impl;
//
//import org.turnbasedtitans.project2.dao.BattleDAO;
//import org.turnbasedtitans.project2.model.Battle;
//import org.turnbasedtitans.project2.database.DatabaseManager;
//
///**
// * [Brief one-sentence description of what this class does.]
// *
// * @author Sebastian Guillen
// * @since 4/20/26
// * @version 0.1.0
// */
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class BattleDAOImpl implements BattleDAO {
//
//    @Override
//    public void insertBattle(Battle battle) {
//        String sql = "INSERT INTO battle (battle_id, npc_name, result, reward) VALUES (?, ?, ?, ?)";
//        try (Connection conn = DatabaseManager.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setInt(1, battle.getBattleId());
//            stmt.setString(2, battle.getNpcName());
//            stmt.setString(3, battle.getResult());
//            stmt.setInt(4, battle.getReward());
//            stmt.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void updateBattle(Battle battle) {
//        String sql = "UPDATE battle SET npc_name=?, result=?, reward=? WHERE battle_id=?";
//        try (Connection conn = DatabaseManager.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setString(1, battle.getNpcName());
//            stmt.setString(2, battle.getResult());
//            stmt.setInt(3, battle.getReward());
//            stmt.setInt(4, battle.getBattleId());
//            stmt.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void deleteBattle(int battleId) {
//        String sql = "DELETE FROM battle WHERE battle_id=?";
//        try (Connection conn = DatabaseManager.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setInt(1, battleId);
//            stmt.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public Battle getBattleById(int battleId) {
//        String sql = "SELECT * FROM battle WHERE battle_id=?";
//        try (Connection conn = DatabaseManager.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setInt(1, battleId);
//            ResultSet rs = stmt.executeQuery();
//
//            if (rs.next()) {
//                return new Battle(
//                        rs.getInt("battle_id"),
//                        rs.getString("npc_name"),
//                        rs.getString("result"),
//                        rs.getInt("reward"),
//                        ""
//                );
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    public List<Battle> getBattlesByNpcName(String npcName) {
//        List<Battle> battles = new ArrayList<>();
//        String sql = "SELECT * FROM battle WHERE npc_name=?";
//
//        try (Connection conn = DatabaseManager.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setString(1, npcName);
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                battles.add(new Battle(
//                        rs.getInt("battle_id"),
//                        rs.getString("npc_name"),
//                        rs.getString("result"),
//                        rs.getInt("reward"),
//                        ""
//                ));
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return battles;
//    }
//}
