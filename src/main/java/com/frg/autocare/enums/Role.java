/**
 * AutoCare REST API - User role enumeration.
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
package com.frg.autocare.enums;

import static com.frg.autocare.enums.Permission.ADMIN_CREATE;
import static com.frg.autocare.enums.Permission.ADMIN_DELETE;
import static com.frg.autocare.enums.Permission.ADMIN_READ;
import static com.frg.autocare.enums.Permission.ADMIN_UPDATE;
import static com.frg.autocare.enums.Permission.CUSTOMER_READ;
import static com.frg.autocare.enums.Permission.CUSTOMER_UPDATE;
import static com.frg.autocare.enums.Permission.MANAGER_CREATE;
import static com.frg.autocare.enums.Permission.MANAGER_DELETE;
import static com.frg.autocare.enums.Permission.MANAGER_READ;
import static com.frg.autocare.enums.Permission.MANAGER_UPDATE;
import static com.frg.autocare.enums.Permission.TECHNICIAN_CREATE;
import static com.frg.autocare.enums.Permission.TECHNICIAN_READ;
import static com.frg.autocare.enums.Permission.TECHNICIAN_UPDATE;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@RequiredArgsConstructor
@Getter
public enum Role {
  ADMIN(
      Set.of(
          ADMIN_READ,
          ADMIN_UPDATE,
          ADMIN_DELETE,
          ADMIN_CREATE,
          MANAGER_READ,
          MANAGER_UPDATE,
          MANAGER_DELETE,
          MANAGER_CREATE,
          TECHNICIAN_READ,
          TECHNICIAN_UPDATE,
          TECHNICIAN_CREATE,
          CUSTOMER_READ)),
  MANAGER(
      Set.of(
          MANAGER_READ,
          MANAGER_UPDATE,
          MANAGER_DELETE,
          MANAGER_CREATE,
          TECHNICIAN_READ,
          TECHNICIAN_UPDATE,
          TECHNICIAN_CREATE,
          CUSTOMER_READ)),
  TECHNICIAN(Set.of(TECHNICIAN_READ, TECHNICIAN_UPDATE, TECHNICIAN_CREATE, CUSTOMER_READ)),
  CUSTOMER(Set.of(CUSTOMER_READ, CUSTOMER_UPDATE));

  private final Set<Permission> permissions;

  public List<SimpleGrantedAuthority> getAuthorities() {
    var authorities =
        getPermissions().stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toList());
    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return authorities;
  }
}
