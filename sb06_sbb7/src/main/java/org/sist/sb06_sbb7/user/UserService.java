package org.sist.sb06_sbb7.user;

import java.util.Optional;

import org.sist.sb06_sbb7.exception.DataNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public SiteUser create(String username, String email, String password) {
		
		SiteUser user = new SiteUser();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		user.setEmail(email);
		
		return this.userRepository.save(user);
	}

	public SiteUser getUser(String username) {
		Optional<SiteUser> siteUser =  this.userRepository.findByUsername(username);
		if(siteUser.isPresent()) {
			return siteUser.get();	//	SiteUser
		}else {
			throw new DataNotFoundException("siteuser not found");
		}
	}
	
}// class
