package edu.cs544.mario477.service;

import edu.cs544.mario477.domain.Advertisement;
import edu.cs544.mario477.dto.AdvertisementDTO;
import edu.cs544.mario477.dto.AdvertisementInputDTO;
import javassist.NotFoundException;

import java.util.List;

public interface AdvertisementService {
    AdvertisementDTO add(AdvertisementInputDTO inputDTO);

    AdvertisementDTO update(Long id, AdvertisementInputDTO inputDTO) throws NotFoundException;

    void delete(Long id);

    AdvertisementDTO finAdvertisement(Long id) throws NotFoundException;

    AdvertisementDTO findAdvertisementByName(String name);

    List<AdvertisementDTO> findAll();

    void activeAdvertisement(Long id);

    void deactiveAvertisement(Long id);


}
