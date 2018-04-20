package com.tsystems.conmob.cmam.repository;

import com.tsystems.conmob.cmam.domain.ICNPush;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ICNPush entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ICNPushRepository extends JpaRepository<ICNPush, Long> {

}
