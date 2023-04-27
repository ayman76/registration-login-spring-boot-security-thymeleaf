package com.global.regLog.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.global.regLog.dto.UserRegistrationDto;
import com.global.regLog.entity.Role;
import com.global.regLog.entity.User;
import com.global.regLog.repository.UserRepo;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

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
		User user = new User(userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(),
				passwordEncoder.encode(userDto.getPassword()), Arrays.asList(new Role("ROLE_USER")));

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
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(email);

		if (user == null) {
			throw new UsernameNotFoundException("Invalid email or password");
		}

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}
}
