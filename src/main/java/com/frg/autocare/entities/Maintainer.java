/**
 * AutoCare REST API - Maintainer entity class.
 * Copyright (C) 2024  AutoCare REST API original author or authors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this application.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.frg.autocare.entities;

import com.frg.autocare.constants.IDEs;
import com.frg.autocare.enums.Role;
import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

@Entity
@Table(name = "maintainer")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Maintainer extends UserAccount {

  @ToString.Exclude
  @OneToMany(mappedBy = "maintainer")
  private Set<Car> cars = new HashSet<>();

  public Maintainer(String name, String email, String password, Role role) {
    super(name, email, password, role);
  }

  @Override
  @Generated(IDEs.INTELLIJ_IDEA)
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    Class<?> oEffectiveClass =
        o instanceof HibernateProxy
            ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
            : o.getClass();
    Class<?> thisEffectiveClass =
        this instanceof HibernateProxy
            ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
            : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) return false;
    Maintainer that = (Maintainer) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }
}
