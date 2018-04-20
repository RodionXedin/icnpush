package com.tsystems.conmob.cmam.repository;

import com.tsystems.conmob.cmam.domain.Composite;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Composite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompositeRepository extends JpaRepository<Composite, Long> {

}
