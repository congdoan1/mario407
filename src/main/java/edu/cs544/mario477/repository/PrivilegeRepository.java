package edu.cs544.mario477.repository;

import edu.cs544.mario477.base.BaseRepository;
import edu.cs544.mario477.domain.Privilege;

public interface PrivilegeRepository extends BaseRepository<Privilege,Long> {

    Privilege findByName(String name);

}
