package com.stackoverflow.questions;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;
import javax.transaction.Transactional;
import com.stackoverflow.questions.entities.GameData;
import com.stackoverflow.questions.repositories.GameDataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QuestionsApplicationTests {

	@Autowired
	private GameDataRepository gameDataRepository;

	@Test
	void contextLoads() {}


	@Test
	@Transactional
	void saveInPartition_savedInPartition() {
		GameData e = new GameData();

		gameDataRepository.saveInPartition(e);

		assertNotNull(e.getCid());
	}

	@Test
	@Transactional
	void findById_gameDataFound() {
		GameData e = new GameData();
		gameDataRepository.saveInPartition(e);

		assertNotNull(gameDataRepository.findById(e.getCid()));
	}

	@Test
	@Transactional
	void saveInPartition_multipleEntities_savedInPartition() {
		GameData e1 = new GameData();
		GameData e2 = new GameData();
		GameData e3 = new GameData();
		GameData e4 = new GameData();

		gameDataRepository.saveInPartition(e1);
		gameDataRepository.saveInPartition(e2);
		gameDataRepository.saveInPartition(e3);
		gameDataRepository.saveInPartition(e4);

		assertEquals(4, gameDataRepository.findAll().size());
	}

	@Test
	@Transactional
	void saveAllInPartition_allSaved() {
		GameData e1 = new GameData();
		GameData e2 = new GameData();
		GameData e3 = new GameData();
		GameData e4 = new GameData();

		assertDoesNotThrow(() -> gameDataRepository.saveAllInPartition(List.of(e1, e2, e3, e4)));

		assertEquals(4, gameDataRepository.findAll().size());
	}
}
