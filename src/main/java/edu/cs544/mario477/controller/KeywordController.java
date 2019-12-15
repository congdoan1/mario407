package edu.cs544.mario477.controller;

import edu.cs544.mario477.common.Response;
import edu.cs544.mario477.common.ResponseBuilder;
import edu.cs544.mario477.dto.KeywordDTO;
import edu.cs544.mario477.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/keywords")
public class KeywordController {

    private KeywordService keywordService;

    @Autowired
    public KeywordController(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    @PostMapping
    public Response<KeywordDTO> add(@RequestBody KeywordDTO inputDTO) {
        return ResponseBuilder.buildSuccess(keywordService.add(inputDTO));
    }

    @DeleteMapping("/{id}")
    public Response<KeywordDTO> delete(@PathVariable Long id) {
        keywordService.delete(id);
        return ResponseBuilder.buildSuccess();
    }

    @PutMapping("/{id}")
    public Response<KeywordDTO> update(@PathVariable Long id, @RequestBody KeywordDTO inputDTO) {

        return ResponseBuilder.buildSuccess(keywordService.update(id, inputDTO));
    }

    @GetMapping("/{id}")
    public Response<KeywordDTO> get(@PathVariable Long id) {

        return ResponseBuilder.buildSuccess(keywordService.finKeyword(id));
    }

    @GetMapping
    public Response<List<KeywordDTO>> list(@RequestParam(defaultValue = "0") Integer page) {

        return ResponseBuilder.buildSuccess(keywordService.findAll(page));
    }

}
