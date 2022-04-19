package com.cody.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cody.domain.Role;
import com.cody.domain.User;
import com.cody.rest.RestClient;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	RestClient restClient;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = restClient.findUserByName(username);
		System.out.println("detailservice " + user);
		Set<GrantedAuthority> authorities = new HashSet<>();
		if(user == null) {
			return new org.springframework.security.core.userdetails.User(username, "", authorities);
		}
		for(Role role : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		};

		return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),  authorities);
	}
}
