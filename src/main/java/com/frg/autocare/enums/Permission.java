/**
 * AutoCare REST API - Permission enumeration.
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

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Permission {
  ADMIN_READ("admin:read"),
  ADMIN_UPDATE("admin:update"),
  ADMIN_CREATE("admin:create"),
  ADMIN_DELETE("admin:delete"),
  MANAGER_READ("manager:read"),
  MANAGER_UPDATE("manager:update"),
  MANAGER_CREATE("manager:create"),
  MANAGER_DELETE("manager:delete"),
  TECHNICIAN_READ("technician:read"),
  TECHNICIAN_UPDATE("technician:update"),
  TECHNICIAN_CREATE("technician:create"),
  CUSTOMER_READ("customer:read"),
  CUSTOMER_UPDATE("customer:update");

  private final String permission;
}
