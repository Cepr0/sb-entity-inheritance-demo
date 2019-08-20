package io.github.cepr0.demo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "BOY")
public class Boy extends Child {
}
