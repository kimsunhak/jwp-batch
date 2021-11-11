package com.data.batch.repository.city;

import com.data.batch.domain.city.CovidCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CovidCityRepository extends JpaRepository<CovidCity, Long> {
}
