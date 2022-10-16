package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.example.assolutoRacing.DemoApplication;


class DemoApplicationTests {
	
	@Test
	void contextLoads() {
		try {
			DemoApplication.main(new String[] {});
			assertThat(true);
		} catch(Exception e) {
			assertThat(false);
		}
	}
}
