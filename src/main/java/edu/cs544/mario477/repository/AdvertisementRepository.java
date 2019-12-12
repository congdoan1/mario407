package edu.cs544.mario477.repository;

import com.sun.org.apache.xpath.internal.functions.FuncSubstring;
import edu.cs544.mario477.base.BaseRepository;
import edu.cs544.mario477.domain.Advertisement;

import java.util.List;

public interface AdvertisementRepository extends BaseRepository<Advertisement, Long> {

    List<Advertisement> findByAgeAfterOrCountryIsLikeOrStateIsLikeOrCityIsLikeOrZipCode
            (int age, String country, String state, String city, String zipCode);

    List<Advertisement> findByAgeAfterOrCountryLikeOrStateLikeOrCityLikeOrZipCode
            (int age, String country, String state, String city, String zipCode);

    List<Advertisement> findByAgeBeforeAndCountryOrStateOrCity
            (int age, String country, String state, String city);

}
