package com.example.demo.mongodb;

import com.example.demo.mongodb.controller.UserController;
import com.example.demo.mongodb.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongodbDemoApplicationTests {

	@Autowired
	private UserController userController;

	@Test
	public void save() {
		User user = new User();
		user.setName("AAA");
		user.setAge(21);
		userController.save(user);
	}

	@Test
	public void getByName() {
		User user = userController.getByName("AAA");
		log.info(user.toString());
	}

	@Test
	public void getById() {
		Optional<User> user = userController.getById("5bbad429e54ff22fecae8fc7");
		log.info("==========="+user.get().getName());
	}

}
