package edu.cs544.mario477.service.impl;

import edu.cs544.mario477.common.Constants;
import edu.cs544.mario477.domain.Keyword;
import edu.cs544.mario477.dto.KeywordDTO;
import edu.cs544.mario477.exception.ResourceNotFoundException;
import edu.cs544.mario477.repository.KeywordRepository;
import edu.cs544.mario477.service.KeywordService;
import edu.cs544.mario477.util.Mapper;
import edu.cs544.mario477.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class KeywordServiceImpl implements KeywordService {

    private KeywordRepository keywordRepository;

    @Autowired
    public KeywordServiceImpl(KeywordRepository keywordRepository) {
        this.keywordRepository = keywordRepository;
    }

    @Override
    public KeywordDTO add(KeywordDTO inputDTO) {
        Keyword keyword = Mapper.map(inputDTO, Keyword.class);
        return Mapper.map(keywordRepository.saveAndFlush(keyword), KeywordDTO.class);
    }

    @Override
    public KeywordDTO update(Long id, KeywordDTO inputDTO) {
        Keyword keyword = keywordRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Keyword", "id", id));

        keyword.setDefinition(inputDTO.getDefinition());
        keyword.setEnabled(inputDTO.isEnabled());
        return Mapper.map(keywordRepository.save(keyword), KeywordDTO.class);
    }

    @Override
    public void delete(Long id) {
        keywordRepository.deleteById(id);

    }

    @Override
    public KeywordDTO finKeyword(Long id) {
        Keyword keyword = keywordRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Keyword", "id", id));

        return Mapper.map(keyword, KeywordDTO.class);
    }

    @Override
    public List<KeywordDTO> findAll(int page) {

        Sort sort = Sort.by("title").ascending();
        Pageable pageable = PageUtil.initPage(page, Constants.DEFAULT_SIZE, sort);
        List<Keyword> list = keywordRepository.findAll(pageable).getContent();
        return Mapper.mapList(list, KeywordDTO.class);
    }
}
