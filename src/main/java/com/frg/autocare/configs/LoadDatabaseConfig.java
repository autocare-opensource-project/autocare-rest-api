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

import com.frg.autocare.entities.Car;
import com.frg.autocare.entities.Customer;
import com.frg.autocare.entities.Maintainer;
import com.frg.autocare.entities.Tool;
import com.frg.autocare.entities.User;
import com.frg.autocare.enums.Role;
import com.frg.autocare.repository.CarRepository;
import com.frg.autocare.repository.CustomerRepository;
import com.frg.autocare.repository.MaintainerRepository;
import com.frg.autocare.repository.ToolRepository;
import com.frg.autocare.repository.UserRepository;
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
      UserRepository userRepository,
      CarRepository carRepository,
      CustomerRepository customerRepository,
      MaintainerRepository maintainerRepository,
      ToolRepository toolRepository) {
    return args -> {
      User user1 = new User();
      user1.setName("John Doe");
      user1.setEmail("john.doe@example.com");
      user1.setPassword(passwordEncoder.encode("password123"));
      user1.setRole(Role.ADMIN);
      userRepository.save(user1);

      Customer customer1 = new Customer();
      customer1.setName("Client One");
      customerRepository.save(customer1);

      Maintainer maintainer1 = new Maintainer();
      maintainer1.setName("Technician One");
      maintainerRepository.save(maintainer1);

      Car car1 = new Car();
      car1.setModel("Model S");
      car1.setMake("Tesla");
      car1.setCustomer(customer1);
      car1.setMaintainer(maintainer1);
      carRepository.save(car1);

      Tool tool1 = new Tool();
      tool1.setName("Wrench");
      tool1.setMaintainer(maintainer1);
      toolRepository.save(tool1);

      Tool tool2 = new Tool();
      tool2.setName("Screwdriver");
      tool2.setMaintainer(maintainer1);
      toolRepository.save(tool2);
    };
  }
}
