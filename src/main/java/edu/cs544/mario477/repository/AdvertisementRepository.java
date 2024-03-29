package edu.cs544.mario477.repository;

import edu.cs544.mario477.base.BaseRepository;
import edu.cs544.mario477.domain.Advertisement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdvertisementRepository extends BaseRepository<Advertisement, Long> {

    @Query("from Advertisement ")
    List<Advertisement> findAllAd(Pageable pageable);

    List<Advertisement> findByAgeAfterOrCountryIsLikeOrStateIsLikeOrCityIsLikeOrZipCode
            (int age, String country, String state, String city, String zipCode);

    List<Advertisement> findByAgeAfterOrCountryLikeOrStateLikeOrCityLikeOrZipCode
            (int age, String country, String state, String city, String zipCode);

    List<Advertisement> findByAgeBeforeAndCountryOrStateOrCity
            (int age, String country, String state, String city);


}

