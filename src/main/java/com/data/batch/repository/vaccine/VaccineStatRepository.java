package com.data.batch.repository.vaccine;

import com.data.batch.domain.vaccine.VaccineStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineStatRepository  extends JpaRepository<VaccineStat, Long> {
}
