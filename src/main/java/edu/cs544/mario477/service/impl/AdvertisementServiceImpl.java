package edu.cs544.mario477.service.impl;

import edu.cs544.mario477.domain.Advertisement;
import edu.cs544.mario477.dto.AdvertisementDTO;
import edu.cs544.mario477.dto.AdvertisementInputDTO;
import edu.cs544.mario477.repository.AdvertisementRepository;
import edu.cs544.mario477.service.AdvertisementService;
import edu.cs544.mario477.util.Mapper;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    private AdvertisementRepository advertisementRepository;

    @Autowired
    public AdvertisementServiceImpl(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    @Override
    public AdvertisementDTO add(AdvertisementInputDTO inputDTO) {

        return convertToDTO(advertisementRepository.saveAndFlush(convertToEntity(inputDTO)));
    }

    @Override
    public AdvertisementDTO update(Long id, AdvertisementInputDTO inputDTO) throws NotFoundException {
        Advertisement advertisement = advertisementRepository.findById(id).orElseThrow(() -> new NotFoundException("not found"));
        advertisement = convertToEntity(inputDTO);
        return convertToDTO(advertisementRepository.saveAndFlush(advertisement));
    }

    @Override
    public void delete(Long id) {
        advertisementRepository.deleteById(id);
    }

    @Override
    public AdvertisementDTO finAdvertisement(Long id) throws NotFoundException {
        Advertisement advertisement = advertisementRepository.findById(id).orElseThrow(() -> new NotFoundException("not found"));
        return convertToDTO(advertisement);
    }

    @Override
    public AdvertisementDTO findAdvertisementByName(String name) {
        return null;
    }

    @Override
    public List<AdvertisementDTO> findAll() {
        List<Advertisement> list = advertisementRepository.findAll();

        return Mapper.mapList(list, AdvertisementDTO.class);
    }

    @Override
    public void activeAdvertisement(Long id) {
        Advertisement advertisement = advertisementRepository.findById(id).orElse(null);

        if (advertisement != null) {
//    advertisement.set
        }
    }

    @Override
    public void deactiveAvertisement(Long id) {

    }

    public Advertisement convertToEntity(AdvertisementInputDTO inputDTO) {
        return Mapper.map(inputDTO, Advertisement.class);
    }

    public AdvertisementDTO convertToDTO(Advertisement advertisement) {
        return Mapper.map(advertisement, AdvertisementDTO.class);
    }
}
