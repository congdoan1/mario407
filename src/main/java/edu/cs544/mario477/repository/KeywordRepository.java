package edu.cs544.mario477.repository;

import edu.cs544.mario477.base.BaseRepository;
import edu.cs544.mario477.domain.Keyword;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface KeywordRepository extends BaseRepository<Keyword, Long> {

//    List<Keyword> findAllKeyword(Pageable pageable);
}
