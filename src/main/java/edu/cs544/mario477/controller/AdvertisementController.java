package edu.cs544.mario477.controller;

import edu.cs544.mario477.common.Response;
import edu.cs544.mario477.common.ResponseBuilder;
import edu.cs544.mario477.domain.Advertisement;
import edu.cs544.mario477.dto.AdvertisementDTO;
import edu.cs544.mario477.service.AdvertisementService;
import edu.cs544.mario477.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ads")
public class AdvertisementController {

    private AdvertisementService advertisementService;

    @Autowired
    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @PostMapping
    public Response<AdvertisementDTO> add(@RequestBody @Valid AdvertisementDTO inputDTO) {

        return ResponseBuilder.buildSuccess(advertisementService.add(inputDTO));
    }

    @DeleteMapping("/{id}")
    public Response delete(@PathVariable Long id) {

        advertisementService.delete(id);
        return ResponseBuilder.buildSuccess();
    }

    @PutMapping("/{id}")
    public Response<AdvertisementDTO> update(@PathVariable Long id, @RequestBody @Valid AdvertisementDTO inputDTO) {
        return ResponseBuilder.buildSuccess((advertisementService.update(id, inputDTO)));
    }

    @GetMapping(value = "/{id}")
    public Response<AdvertisementDTO> get(@PathVariable Long id) {
        return ResponseBuilder.buildSuccess(advertisementService.finAdvertisement(id));
    }

    @GetMapping
    public Response list(@RequestParam(value = "page", required = false) Integer page,
                         @RequestParam(value = "size", required = false) Integer size) {
        Page<AdvertisementDTO> ads = advertisementService.findAll(PageUtil.initPage(page, size));

        return ResponseBuilder.buildSuccess(ads);
    }

//    @GetMapping(value = "/match-user/{userId}")
//    public Response listMatchUser(@PathVariable Long userId) {
//
//        return ResponseBuilder.buildSuccess(advertisementService.findAdvertisementMatchWithUser(userId));
//    }

}
