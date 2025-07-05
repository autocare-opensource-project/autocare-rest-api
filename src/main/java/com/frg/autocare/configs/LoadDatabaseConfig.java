/**
 * AutoCare REST API - Load database configuration component.
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
package com.frg.autocare.configs;

import com.frg.autocare.entities.Admin;
import com.frg.autocare.entities.Car;
import com.frg.autocare.entities.Customer;
import com.frg.autocare.entities.Maintainer;
import com.frg.autocare.entities.UserAccount;
import com.frg.autocare.enums.Role;
import com.frg.autocare.repository.CarRepository;
import com.frg.autocare.repository.CustomerRepository;
import com.frg.autocare.repository.MaintainerRepository;
import com.frg.autocare.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("demo")
@RequiredArgsConstructor
public class LoadDatabaseConfig {

  private final PasswordEncoder passwordEncoder;

  @Bean
  CommandLineRunner initDatabase(
      UserAccountRepository userAccountRepository,
      CarRepository carRepository,
      CustomerRepository customerRepository,
      MaintainerRepository maintainerRepository) {
    return args -> {
      UserAccount admin = new Admin();
      admin.setName("Admin Admin");
      admin.setEmail("admin@test.com");
      //    WARNING: the next line must be commented to avoid security issues in code
      admin.setPassword(passwordEncoder.encode("password123"));
      admin.setRole(Role.ADMIN);
      userAccountRepository.save(admin);

      Customer customer1 = new Customer();
      customer1.setName("Customer 1");
      customer1.setEmail("customer1@test.com");
      customer1.setPassword(passwordEncoder.encode("password123"));
      customerRepository.save(customer1);

      Maintainer maintainer1 = new Maintainer();
      maintainer1.setName("Technician 1");
      maintainer1.setPassword(passwordEncoder.encode("password123"));
      maintainer1.setEmail("maintainer1@test.com");
      maintainerRepository.save(maintainer1);

      Car car1 = new Car();
      car1.setBrand("BMW");
      car1.setModel("XA123");
      car1.setCustomer(customer1);
      car1.setMaintainer(maintainer1);
      carRepository.save(car1);
    };
  }
}
