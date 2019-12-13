package edu.cs544.mario477.repository;

import edu.cs544.mario477.base.BaseRepository;
import edu.cs544.mario477.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {

}
