package com.frg.autocare.entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Admin extends UserAccount {
  private boolean isOwner;
}
