package com.example.cacvasbe.security;

import com.example.cacvasbe.entities.PortalUsers;
import com.example.cacvasbe.enums.GenericStatusConstant;
import com.example.cacvasbe.repository.PortalUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SystemUserDetailsService implements UserDetailsService {
  @Autowired
  private final PortalUserRepository portalUserRepository;


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Optional<PortalUsers> user = portalUserRepository.findByUsernameIgnoreCaseAndStatus(username, GenericStatusConstant.ACTIVE);
    if (!user.isPresent()) {
      throw new UsernameNotFoundException("Could not find user");
    }

    return new VASSUserDetails(user.get());

  }
}
