package com.tsystems.conmob.cmam.repository;

import com.tsystems.conmob.cmam.domain.AccelerationPair;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AccelerationPair entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccelerationPairRepository extends JpaRepository<AccelerationPair, Long> {

}
