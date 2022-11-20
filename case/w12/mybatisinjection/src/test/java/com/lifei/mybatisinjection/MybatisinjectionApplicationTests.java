package com.lifei.mybatisinjection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MybatisinjectionApplicationTests {
	@Autowired
	private TabUserMapper userMapper;

	@Test
	void testSelect() {
		System.out.println("Before sql injection");
		List<TabUser> u = userMapper.getUserById("1");
		u.forEach(tu -> System.out.println(tu.getName()));

		System.out.println("\nAfter sql injection");
		u = userMapper.getUserById("1 or 1 = 1");
		u.forEach(tu -> System.out.println(tu.getName()));
	}

}
