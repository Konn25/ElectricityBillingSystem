package com.billing.ElectricityBillingSystem.dao;

import com.billing.ElectricityBillingSystem.jpa.Client;
import com.billing.ElectricityBillingSystem.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.billing.ElectricityBillingSystem.config.Roles.ROLE_CLIENT;


@Repository
@RequiredArgsConstructor
public class UserDAO {

    private final ClientService clientService;

    public UserDetails findUserByEmail(String email) {

        List<UserDetails> APP_USERS = getAllUsersFromDatabase();

        return APP_USERS.stream().filter(u -> u.getUsername().equals(email)).findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("No user was found"));
    }

    public List<UserDetails> getAllUsersFromDatabase() {

        List<UserDetails> userList = new ArrayList<>();

        List<Client> clientList = clientService.getAllClient();

        for (Client client : clientList) {
            userList.add(new User(client.getEmail(), client.getPassword(), Collections.singleton(new SimpleGrantedAuthority(ROLE_CLIENT.name()))));
        }

        return userList;

    }

}

