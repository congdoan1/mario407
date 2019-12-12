package edu.cs544.mario477.service.impl;

import edu.cs544.mario477.domain.Keyword;
import edu.cs544.mario477.dto.KeywordDTO;
import edu.cs544.mario477.exception.ResourceNotFoundException;
import edu.cs544.mario477.repository.KeywordRespository;
import edu.cs544.mario477.service.KeywordService;
import edu.cs544.mario477.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class KeywordImpl implements KeywordService {

    private KeywordRespository keywordRespository;

    @Autowired
    public KeywordImpl(KeywordRespository keywordRespository) {
        this.keywordRespository = keywordRespository;
    }

    @Override
    public KeywordDTO add(KeywordDTO inputDTO) {
        Keyword keyword = Mapper.map(inputDTO, Keyword.class);
        return Mapper.map(keywordRespository.saveAndFlush(keyword), KeywordDTO.class);
    }

    @Override
    public KeywordDTO update(Long id, KeywordDTO inputDTO) {
        Keyword keyword = keywordRespository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Keyword", "id", id));

        keyword.setDefinition(inputDTO.getDefinition());
        keyword.setEnabled(inputDTO.isEnabled());
        return Mapper.map(keywordRespository.save(keyword), KeywordDTO.class);
    }

    @Override
    public void delete(Long id) {
        keywordRespository.deleteById(id);

    }

    @Override
    public KeywordDTO finKeyword(Long id) {
        Keyword keyword = keywordRespository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Keyword", "id", id));

        return Mapper.map(keyword, KeywordDTO.class);
    }

    @Override
    public List<KeywordDTO> findAll() {
        List<Keyword> list = keywordRespository.findAll();
        return Mapper.mapList(list, KeywordDTO.class);
    }
}
