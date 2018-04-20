package com.tsystems.conmob.cmam.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsystems.conmob.cmam.domain.AccelerationPair;

import com.tsystems.conmob.cmam.repository.AccelerationPairRepository;
import com.tsystems.conmob.cmam.web.rest.errors.BadRequestAlertException;
import com.tsystems.conmob.cmam.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing AccelerationPair.
 */
@RestController
@RequestMapping("/api")
public class AccelerationPairResource {

    private final Logger log = LoggerFactory.getLogger(AccelerationPairResource.class);

    private static final String ENTITY_NAME = "accelerationPair";

    private final AccelerationPairRepository accelerationPairRepository;

    public AccelerationPairResource(AccelerationPairRepository accelerationPairRepository) {
        this.accelerationPairRepository = accelerationPairRepository;
    }

    /**
     * POST  /acceleration-pairs : Create a new accelerationPair.
     *
     * @param accelerationPair the accelerationPair to create
     * @return the ResponseEntity with status 201 (Created) and with body the new accelerationPair, or with status 400 (Bad Request) if the accelerationPair has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/acceleration-pairs")
    @Timed
    public ResponseEntity<AccelerationPair> createAccelerationPair(@RequestBody AccelerationPair accelerationPair) throws URISyntaxException {
        log.debug("REST request to save AccelerationPair : {}", accelerationPair);
        if (accelerationPair.getId() != null) {
            throw new BadRequestAlertException("A new accelerationPair cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccelerationPair result = accelerationPairRepository.save(accelerationPair);
        return ResponseEntity.created(new URI("/api/acceleration-pairs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /acceleration-pairs : Updates an existing accelerationPair.
     *
     * @param accelerationPair the accelerationPair to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated accelerationPair,
     * or with status 400 (Bad Request) if the accelerationPair is not valid,
     * or with status 500 (Internal Server Error) if the accelerationPair couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/acceleration-pairs")
    @Timed
    public ResponseEntity<AccelerationPair> updateAccelerationPair(@RequestBody AccelerationPair accelerationPair) throws URISyntaxException {
        log.debug("REST request to update AccelerationPair : {}", accelerationPair);
        if (accelerationPair.getId() == null) {
            return createAccelerationPair(accelerationPair);
        }
        AccelerationPair result = accelerationPairRepository.save(accelerationPair);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, accelerationPair.getId().toString()))
            .body(result);
    }

    /**
     * GET  /acceleration-pairs : get all the accelerationPairs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of accelerationPairs in body
     */
    @GetMapping("/acceleration-pairs")
    @Timed
    public List<AccelerationPair> getAllAccelerationPairs() {
        log.debug("REST request to get all AccelerationPairs");
        return accelerationPairRepository.findAll();
        }

    /**
     * GET  /acceleration-pairs/:id : get the "id" accelerationPair.
     *
     * @param id the id of the accelerationPair to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the accelerationPair, or with status 404 (Not Found)
     */
    @GetMapping("/acceleration-pairs/{id}")
    @Timed
    public ResponseEntity<AccelerationPair> getAccelerationPair(@PathVariable Long id) {
        log.debug("REST request to get AccelerationPair : {}", id);
        AccelerationPair accelerationPair = accelerationPairRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(accelerationPair));
    }

    /**
     * DELETE  /acceleration-pairs/:id : delete the "id" accelerationPair.
     *
     * @param id the id of the accelerationPair to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/acceleration-pairs/{id}")
    @Timed
    public ResponseEntity<Void> deleteAccelerationPair(@PathVariable Long id) {
        log.debug("REST request to delete AccelerationPair : {}", id);
        accelerationPairRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
