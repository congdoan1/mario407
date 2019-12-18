package edu.cs544.mario477.service.impl;

import edu.cs544.mario477.common.Constants;
import edu.cs544.mario477.domain.Address;
import edu.cs544.mario477.domain.Advertisement;
import edu.cs544.mario477.domain.User;
import edu.cs544.mario477.dto.AdvertisementDTO;
import edu.cs544.mario477.exception.ResourceNotFoundException;
import edu.cs544.mario477.repository.AdvertisementRepository;
import edu.cs544.mario477.repository.UserRepository;
import edu.cs544.mario477.service.AdvertisementService;
import edu.cs544.mario477.util.Mapper;
import edu.cs544.mario477.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class AdvertisementServiceImpl implements AdvertisementService {

    private AdvertisementRepository advertisementRepository;

    private UserRepository userRepository;

    @Autowired
    public AdvertisementServiceImpl(UserRepository userRepository, AdvertisementRepository advertisementRepository) {
        this.userRepository = userRepository;
        this.advertisementRepository = advertisementRepository;
    }

    @Override
    public AdvertisementDTO add(AdvertisementDTO inputDTO) {

        Advertisement advertisement = Mapper.map(inputDTO, Advertisement.class);
        return Mapper.map(advertisementRepository.saveAndFlush(advertisement), AdvertisementDTO.class);

    }

    @Override
    public AdvertisementDTO update(Long id, AdvertisementDTO inputDTO) throws ResourceNotFoundException {

        Advertisement advertisement = advertisementRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Advertisement", "id", id));
        advertisement = Mapper.map(inputDTO, Advertisement.class);

        return Mapper.map(advertisementRepository.save(advertisement), AdvertisementDTO.class);
    }

    @Override
    public void delete(Long id) {
        advertisementRepository.deleteById(id);
    }

    @Override
    public AdvertisementDTO finAdvertisement(Long id) throws ResourceNotFoundException {

        Advertisement advertisement = advertisementRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Advertisement", "id", id));
        return Mapper.map(advertisement, AdvertisementDTO.class);

    }

    @Override
    public List<AdvertisementDTO> findAdvertisementMatchWithUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        LocalDate birthDay = user.getBirthday();
        int age = LocalDate.now().getYear() - birthDay.getYear();
        Address address = user.getAddress();

        List<Advertisement> list = advertisementRepository.findByAgeBeforeAndCountryOrStateOrCity
                (age, address.getCountry(), address.getState(), address.getCity());

        if (list.size() <= 0)
            list = advertisementRepository.findByAgeAfter(age);

        if (list.size() <= 0)
            list = advertisementRepository.findByCountryIgnoreCase(address.getCountry());

        if (list.size() <= 0)
            list = advertisementRepository.findByCityContains(address.getCity());

        if (list.size() <= 0)
            list = advertisementRepository.findByZipCode(address.getZipcode());

        return Mapper.mapList(list, AdvertisementDTO.class);
    }

    @Override
    public AdvertisementDTO findAdvertisementByName(String name) {
        return null;
    }

    @Override
    public List<AdvertisementDTO> findAll(int page) {

        Sort sort = Sort.by("title").ascending();
        Pageable pageable = PageUtil.initPage(page, Constants.DEFAULT_SIZE, sort);

        List<Advertisement> list = advertisementRepository.findAllAd(pageable);
        return Mapper.mapList(list, AdvertisementDTO.class);
    }

    @Override
    public void setStatusAdvertisement(Long id, boolean status) {
        Advertisement advertisement = advertisementRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Advertisement", "id", id));

        if (advertisement != null) {
            advertisement.setEnabled(status);
        }
    }


}
