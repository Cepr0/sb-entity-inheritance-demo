package io.github.cepr0.demo;

import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@SpringBootApplication
public class Application {

	private Parent parent;
	private List<Child> children;

	private final ParentRepo parentRepo;
	private final ChildRepo childRepo;

	public Application(ParentRepo parentRepo, ChildRepo childRepo) {
		this.parentRepo = parentRepo;
		this.childRepo = childRepo;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean(initMethod = "start", destroyMethod = "stop")
	public Server h2Server() throws SQLException {
		return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
	}

	@Transactional
	@Order(1)
	@EventListener(ApplicationReadyEvent.class)
	public void populate() {
		log.info("[i] Populate data");

		parent = parentRepo.save(new Parent().setName("parent"));
		children = parentRepo.save(this.parent.setChildren(
				Set.of(
						new Boy().setName("Boy1").setParent(this.parent),
						new Boy().setName("Boy2").setParent(this.parent)
				)
		)).getChildren()
				.stream()
				.sorted(Comparator.comparing(Child::getName))
				.collect(Collectors.toList());
	}

	@Transactional
	@Order(2)
	@EventListener(ApplicationReadyEvent.class)
	public void replaceChild() {
		log.info("[i] Updating children...");
		parentRepo.getForUpdateById(parent.getId()).ifPresent(parent -> parent
				.removeChild(childRepo.getOne(children.get(1).getId())) // -> Boy2
				.addChild(new Girl().setName("Girl1"))
		);
	}

	@Order(3)
	@EventListener(ApplicationReadyEvent.class)
	public void check() {
		log.info("[i] Checking data...");
		parentRepo.getById(1);
	}
}
