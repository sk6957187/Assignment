package com.game.project.GameV1;

import com.game.project.GameV1.controller.BattleController;
import com.game.project.GameV1.service.BattleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
;

@SpringBootTest
@AutoConfigureMockMvc
class GameV1ApplicationTests {

	@Autowired
	private MockMvc mockMvc;


	private BattleController b = new BattleController();
	private BattleService b1 = new BattleService();


	@Test
	void contextLoads() {
		String str = b.test();
		System.out.println(str);
	}

	@Test
	void test() {
		int n = b.square(10); // Assuming `square` method exists in BattleController
		assertEquals(100, n, "Square of 10 should be 100");
	}

	@Test
	void testStartBattle() throws Exception {
		when(b1.battle(Mockito.any(), Mockito.any())).thenReturn("🏆 Winner: Car A");
		mockMvc.perform(post("/battle")
						.contentType("application/json")
						.content("[{\"name\":\"Car A\",\"health\":50,\"attackPower\":10,\"defenseStrength\":5}, " +
								"{\"name\":\"Car B\",\"health\":100,\"attackPower\":5,\"defenseStrength\":10}]"))
				.andExpect(status().isOk())
				.andExpect(content().string("🏆 Winner: Car A"));

	}

	@Test
	void testNameEndpoint () throws Exception {
		mockMvc.perform(get("/name"))  // Calls the `/name` endpoint in BattleController
				.andExpect(status().isOk())
				.andExpect(content().string("My name is: Sumit")); // Ensures the expected response
	}
}
