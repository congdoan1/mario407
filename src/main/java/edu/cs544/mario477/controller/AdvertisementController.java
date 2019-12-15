package edu.cs544.mario477.controller;

import edu.cs544.mario477.common.Response;
import edu.cs544.mario477.common.ResponseBuilder;
import edu.cs544.mario477.dto.AdvertisementDTO;
import edu.cs544.mario477.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/advertisements")
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
    public Response<List<AdvertisementDTO>> list(@RequestParam(defaultValue = "0") Integer page) {
        return ResponseBuilder.buildSuccess(advertisementService.findAll(page));
    }

    @GetMapping(value = "/match-user/{userId}")
    public Response<List<AdvertisementDTO>> listMatchUser(@PathVariable Long userId) {

        return ResponseBuilder.buildSuccess(advertisementService.findAdvertisementMatchWithUser(userId));
    }

}
