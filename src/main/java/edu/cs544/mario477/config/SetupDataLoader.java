package edu.cs544.mario477.config;

import edu.cs544.mario477.domain.Privilege;
import edu.cs544.mario477.domain.Role;
import edu.cs544.mario477.domain.User;
import edu.cs544.mario477.repository.PrivilegeRepository;
import edu.cs544.mario477.repository.RoleRepository;
import edu.cs544.mario477.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // API

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        // == create initial privileges
        final Privilege readPrivilege = createPrivilegeIfNotFound("READ");
        final Privilege writePrivilege = createPrivilegeIfNotFound("WRITE");
        final Privilege editPrivilege = createPrivilegeIfNotFound("EDIT");

        // == create initial roles
        final List<Privilege> adminPrivileges = new ArrayList<Privilege>(Arrays.asList(readPrivilege, writePrivilege, editPrivilege));
        final List<Privilege> systemAdminPrivileges = new ArrayList<Privilege>(Arrays.asList(readPrivilege, editPrivilege));
        final List<Privilege> userPrivileges = new ArrayList<Privilege>(Arrays.asList(readPrivilege, editPrivilege));
        final Role adminRole = createRoleIfNotFound("ADMIN", adminPrivileges);
        final Role systemAdminRole = createRoleIfNotFound("SYSTEM", systemAdminPrivileges);
        final Role userRole = createRoleIfNotFound("USER", userPrivileges);

        // == create initial user
        createUserIfNotFound("admin", "admin@test.com", "Admin", "Test", "admin", adminRole);

        createUserIfNotFound("system", "systemadmin@test.com", "system Admin", "Test", "system", systemAdminRole);

        createUserIfNotFound("user", "pduythong@gmail.com", "user", "last name", "user", userRole);
        alreadySetup = true;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(final String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilege = privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(final String name, final Collection<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
        }
        role.setPrivileges(privileges);
        role = roleRepository.save(role);
        return role;
    }

    @Transactional
    User createUserIfNotFound(final String username, final String email, final String firstName, final String lastName, final String password, final Role role) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            user = new User();
            user.setUsername(username);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPassword(passwordEncoder.encode(password));
            user.setEmail(email);
            user.setEnabled(true);
        }
        user.addRole(role);
        user = userRepository.save(user);
        return user;
    }

}