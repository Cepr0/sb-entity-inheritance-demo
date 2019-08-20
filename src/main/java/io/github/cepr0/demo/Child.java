package io.github.cepr0.demo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "children")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class Child extends BaseEntity {

	@Column(length = 32)
	private String name;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Parent parent;
}
