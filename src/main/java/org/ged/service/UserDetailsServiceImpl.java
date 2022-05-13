package org.ged.service;

import java.util.ArrayList;
import java.util.Collection;

import org.ged.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	AccountService accountservice;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser u = accountservice.findByUsername(username);

		if (u == null)
			throw new UsernameNotFoundException(username);
		Collection<GrantedAuthority> authorities = new ArrayList<>();

		u.getRoles().forEach(r -> {
			authorities.add(new SimpleGrantedAuthority(r.getRole()));
		});
		return new User(u.getUsername(), u.getPassword(), authorities);
	}

}
