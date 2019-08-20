package io.github.cepr0.demo;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface ParentRepo extends JpaRepository<Parent, Integer> {
	@Lock(LockModeType.PESSIMISTIC_READ)
	@EntityGraph(attributePaths = "children")
	Optional<Parent> getForUpdateById(int parentId);

	@EntityGraph(attributePaths = "children")
	Optional<Parent> getById(int parentId);

}
