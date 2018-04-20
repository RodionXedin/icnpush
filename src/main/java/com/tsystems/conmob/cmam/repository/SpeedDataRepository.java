package com.tsystems.conmob.cmam.repository;

import com.tsystems.conmob.cmam.domain.SpeedData;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SpeedData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpeedDataRepository extends JpaRepository<SpeedData, Long> {

}
