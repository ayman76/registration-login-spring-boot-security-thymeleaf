package com.global.regLog.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.regLog.dto.UserRegistrationDto;
import com.global.regLog.entity.Role;
import com.global.regLog.entity.User;
import com.global.regLog.repository.UserRepo;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepo;

	private User findById(Long id) {
		return userRepo.findById(id).orElseThrow();
	}

	private User getById(Long id) {
		return userRepo.getReferenceById(id);
	}

	private List<User> findAll() {
		return userRepo.findAll();
	}

	private Long count() {
		return userRepo.count();
	}

	private User insert(UserRegistrationDto userDto) {
		Set<Role> defaultRole = new HashSet<>();
		defaultRole.add(new Role("ROLE_USER"));
		User user = new User(userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(), userDto.getPassword(),
				defaultRole);

		return userRepo.save(user);
	}

	private User update(User user) {
		User updated = getById(user.getId());

		updated.setFirstName(user.getFirstName());
		updated.setLastName(user.getLastName());
		updated.setEmail(user.getEmail());
		updated.setPassword(user.getPassword());
		updated.setRoles(user.getRoles());

		return userRepo.save(updated);
	}

	private void deleteById(Long id) {
		userRepo.deleteById(id);
	}

}
