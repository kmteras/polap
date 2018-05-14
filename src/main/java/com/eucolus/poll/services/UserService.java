package com.eucolus.poll.services;

import com.eucolus.poll.entities.PollUser;
import com.eucolus.poll.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public PollUser updateUserStatus(Principal principal) {
        Map<String, Object> details = (Map<String, Object>) ((OAuth2Authentication) principal).getUserAuthentication().getDetails();

        String uid = (String)details.get("sub");
        String firstName = (String)details.get("given_name");
        String lastName = (String)details.get("family_name");
        String name = (String)details.get("name");
        String email = (String)details.get("email");

        for(String key : details.keySet()) {
            System.out.println(key + " " + details.get(key));
        }

        PollUser user = userRepository.findByEmail(email);

        if(user == null) {
            user = new PollUser();
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setGoogleUid(uid);
            userRepository.save(user);
        }

        return user;
    }

    public PollUser getUser(Principal principal) {
        Map<String, Object> details = (Map<String, Object>) ((OAuth2Authentication) principal).getUserAuthentication().getDetails();

        PollUser pollUser = userRepository.findByEmail((String)details.get("email"));

        if(pollUser == null) {
            return updateUserStatus(principal);
        }

        return pollUser;
    }
}
