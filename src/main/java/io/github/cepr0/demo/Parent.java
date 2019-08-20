package io.github.cepr0.demo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "parents")
public class Parent extends BaseEntity {

	@Column(length = 32)
	private String name;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Child> children = new HashSet<>();

	public Parent setChildren(Set<Child> children) {
		this.children.forEach(child -> child.setParent(null));
		this.children.clear();
		children.forEach(child -> child.setParent(this));
		this.children.addAll(children);
		return this;
	}

	public Parent addChild(Child child) {
		children.add(child.setParent(this));
		return this;
	}

	public Parent removeChild(Child child) {
		children.remove(child.setParent(null));
		return this;
	}
}
