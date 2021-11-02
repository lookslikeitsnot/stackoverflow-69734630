package com.stackoverflow.questions.repositories;

import java.util.Collection;
import java.util.List;
import javax.transaction.Transactional;
import com.stackoverflow.questions.entities.GameData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GameDataRepository extends JpaRepository<GameData, Integer> {
  @Modifying
  @Query(value = "CREATE TABLE orders", nativeQuery = true)
  void createGameDataTable();

  @Modifying
  @Transactional
  default GameData saveInPartition(GameData gameData) {
    createGameDataTable();
    return save(gameData);
  }

  @Modifying
  @Transactional
  default List<GameData> saveAllInPartition(Collection<GameData> gameDatas) {
    createGameDataTable();
    return saveAll(gameDatas);
  }
}
