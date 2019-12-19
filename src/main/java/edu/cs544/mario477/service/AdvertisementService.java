package edu.cs544.mario477.service;

import edu.cs544.mario477.dto.AdvertisementDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdvertisementService {
    AdvertisementDTO add(AdvertisementDTO inputDTO);

    AdvertisementDTO update(Long id, AdvertisementDTO inputDTO);

    void delete(Long id);

    AdvertisementDTO finAdvertisement(Long id);

    List<AdvertisementDTO> findAdvertisementMatchWithUser(Long userId);

    AdvertisementDTO findAdvertisementByName(String name);

    Page<AdvertisementDTO> findAll(Pageable pageable);

    void setStatusAdvertisement(Long id, boolean status);


}
