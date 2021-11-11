package com.data.batch.repository.center;

import com.data.batch.domain.center.CovidCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CovidCenterRepository extends JpaRepository<CovidCenter, Long> {

    List<CovidCenter> findAllByOrderById();
}
