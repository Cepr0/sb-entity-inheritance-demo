package io.github.cepr0.demo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "GIRL")
public class Girl extends Child {
}
