package io.github.cepr0.demo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

	@Id
	@GeneratedValue(generator = "global_sequence")
	@SequenceGenerator(name = "global_sequence", allocationSize = 10)
	private Integer id;

	@Version
	private Integer version;

	@Override
	public String toString() {
		return getClass().getSimpleName() + "{id=" + getId() + "}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!getClass().isInstance(o)) return false;
		return getId() != null && getId().equals(((BaseEntity) o).getId());
	}

	@Override
	public int hashCode() {
		return 31;
	}
}
