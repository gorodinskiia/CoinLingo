package com.cointrade.terminal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

	@SpringBootTest
	@TestPropertySource(properties = {
		"spring.datasource.url=jdbc:postgresql://localhost:5433/cointrade",
		"spring.datasource.username=coinuser",
		"spring.datasource.password=your_password",
		"spring.jpa.hibernate.ddl-auto=validate"
	})
	
	class CoinTradeTerminalApplicationTests {

		@Test
		void contextLoads() {
		}

	}
