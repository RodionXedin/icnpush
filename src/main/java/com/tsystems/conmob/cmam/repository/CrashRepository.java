package com.tsystems.conmob.cmam.repository;

import com.tsystems.conmob.cmam.domain.Crash;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Crash entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CrashRepository extends JpaRepository<Crash, Long> {

}
