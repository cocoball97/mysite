package mysite.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import mysite.repository.UserRepository;
import mysite.vo.UserVo;

public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;
	
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	
	// uservo, userdetaillsimpl 에서 가져오는 이유 : 값을 다 구현하기 싫어서?
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 같은 함수인데 리턴값이 다른 경우
		// 리턴을 UserDetailsImpl 클래스 값으로?
		return userRepository.findByEmail(username, UserDetailsImpl.class);
	}

}
