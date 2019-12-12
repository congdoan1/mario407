package edu.cs544.mario477.service;

import edu.cs544.mario477.domain.Keyword;
import edu.cs544.mario477.dto.KeywordDTO;

import java.util.List;


public interface KeywordService {


    KeywordDTO add(KeywordDTO inputDTO);

    KeywordDTO update(Long id, KeywordDTO inputDTO);

    void delete(Long id);

    KeywordDTO finKeyword(Long id);

    List<KeywordDTO> findAll();

}
