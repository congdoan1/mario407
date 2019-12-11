package edu.cs544.mario477.controller;
import edu.cs544.mario477.common.Response;
import edu.cs544.mario477.common.ResponseBuilder;
import edu.cs544.mario477.dto.AdvertisementDTO;
import edu.cs544.mario477.dto.AdvertisementInputDTO;
import edu.cs544.mario477.service.AdvertisementService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Advertisement")
public class AdvertisementController {

    @Autowired
    AdvertisementService advertisementService;

    //    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @PostMapping(name = "/add")
    public Response<AdvertisementDTO> add(@RequestBody AdvertisementInputDTO inputDTO) {

        return ResponseBuilder.buildSuccess(advertisementService.add(inputDTO));
    }

    @DeleteMapping(name = "/delete/{id}")
    public Response delete(@PathVariable Long id) {

        advertisementService.delete(id);
        return ResponseBuilder.buildSuccess();

    }

    @PutMapping(value = "/update/{id}")
    public Response<AdvertisementDTO> update(@PathVariable Long id, @RequestBody AdvertisementInputDTO inputDTO) throws NotFoundException {
        return ResponseBuilder.buildSuccess((advertisementService.update(id, inputDTO)));
    }

    @GetMapping(value = "/get/{id}")
    public Response<AdvertisementDTO> get(@PathVariable Long id) throws NotFoundException {
        return ResponseBuilder.buildSuccess(advertisementService.finAdvertisement(id));
    }

    @GetMapping(value = "/list")
    public Response<List<AdvertisementDTO>> list() {
        return ResponseBuilder.buildSuccess(advertisementService.findAll());
    }

}
