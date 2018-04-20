package com.tsystems.conmob.cmam.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsystems.conmob.cmam.domain.SpeedData;

import com.tsystems.conmob.cmam.repository.SpeedDataRepository;
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
 * REST controller for managing SpeedData.
 */
@RestController
@RequestMapping("/api")
public class SpeedDataResource {

    private final Logger log = LoggerFactory.getLogger(SpeedDataResource.class);

    private static final String ENTITY_NAME = "speedData";

    private final SpeedDataRepository speedDataRepository;

    public SpeedDataResource(SpeedDataRepository speedDataRepository) {
        this.speedDataRepository = speedDataRepository;
    }

    /**
     * POST  /speed-data : Create a new speedData.
     *
     * @param speedData the speedData to create
     * @return the ResponseEntity with status 201 (Created) and with body the new speedData, or with status 400 (Bad Request) if the speedData has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/speed-data")
    @Timed
    public ResponseEntity<SpeedData> createSpeedData(@RequestBody SpeedData speedData) throws URISyntaxException {
        log.debug("REST request to save SpeedData : {}", speedData);
        if (speedData.getId() != null) {
            throw new BadRequestAlertException("A new speedData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SpeedData result = speedDataRepository.save(speedData);
        return ResponseEntity.created(new URI("/api/speed-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /speed-data : Updates an existing speedData.
     *
     * @param speedData the speedData to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated speedData,
     * or with status 400 (Bad Request) if the speedData is not valid,
     * or with status 500 (Internal Server Error) if the speedData couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/speed-data")
    @Timed
    public ResponseEntity<SpeedData> updateSpeedData(@RequestBody SpeedData speedData) throws URISyntaxException {
        log.debug("REST request to update SpeedData : {}", speedData);
        if (speedData.getId() == null) {
            return createSpeedData(speedData);
        }
        SpeedData result = speedDataRepository.save(speedData);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, speedData.getId().toString()))
            .body(result);
    }

    /**
     * GET  /speed-data : get all the speedData.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of speedData in body
     */
    @GetMapping("/speed-data")
    @Timed
    public List<SpeedData> getAllSpeedData() {
        log.debug("REST request to get all SpeedData");
        return speedDataRepository.findAll();
        }

    /**
     * GET  /speed-data/:id : get the "id" speedData.
     *
     * @param id the id of the speedData to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the speedData, or with status 404 (Not Found)
     */
    @GetMapping("/speed-data/{id}")
    @Timed
    public ResponseEntity<SpeedData> getSpeedData(@PathVariable Long id) {
        log.debug("REST request to get SpeedData : {}", id);
        SpeedData speedData = speedDataRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(speedData));
    }

    /**
     * DELETE  /speed-data/:id : delete the "id" speedData.
     *
     * @param id the id of the speedData to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/speed-data/{id}")
    @Timed
    public ResponseEntity<Void> deleteSpeedData(@PathVariable Long id) {
        log.debug("REST request to delete SpeedData : {}", id);
        speedDataRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
