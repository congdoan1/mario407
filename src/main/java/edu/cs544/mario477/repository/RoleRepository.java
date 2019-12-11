package edu.cs544.mario477.repository;

import edu.cs544.mario477.base.BaseRepository;
import edu.cs544.mario477.domain.Role;
import edu.cs544.mario477.domain.User;

public interface RoleRepository extends BaseRepository<Role, Long> {

    Role findByName(String name);
}
