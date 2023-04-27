package com.global.regLog.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.global.regLog.dto.UserRegistrationDto;
import com.global.regLog.entity.Role;
import com.global.regLog.entity.User;
import com.global.regLog.repository.UserRepo;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	public User findById(Long id) {
		return userRepo.findById(id).orElseThrow();
	}

	public User getById(Long id) {
		return userRepo.getReferenceById(id);
	}

	public List<User> findAll() {
		return userRepo.findAll();
	}

	public Long count() {
		return userRepo.count();
	}

	public User insert(UserRegistrationDto userDto) {
		User user = new User(userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(), userDto.getPassword(),
				Arrays.asList(new Role("ROLE_USER")));

		return userRepo.save(user);
	}

	public User update(User user) {
		User updated = getById(user.getId());

		updated.setFirstName(user.getFirstName());
		updated.setLastName(user.getLastName());
		updated.setEmail(user.getEmail());
		updated.setPassword(user.getPassword());
		updated.setRoles(user.getRoles());

		return userRepo.save(updated); 
	}

	public void deleteById(Long id) {
		userRepo.deleteById(id);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
