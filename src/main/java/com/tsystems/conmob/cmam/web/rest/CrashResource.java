package com.tsystems.conmob.cmam.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsystems.conmob.cmam.domain.Crash;

import com.tsystems.conmob.cmam.repository.CrashRepository;
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
 * REST controller for managing Crash.
 */
@RestController
@RequestMapping("/api")
public class CrashResource {

    private final Logger log = LoggerFactory.getLogger(CrashResource.class);

    private static final String ENTITY_NAME = "crash";

    private final CrashRepository crashRepository;

    public CrashResource(CrashRepository crashRepository) {
        this.crashRepository = crashRepository;
    }

    /**
     * POST  /crashes : Create a new crash.
     *
     * @param crash the crash to create
     * @return the ResponseEntity with status 201 (Created) and with body the new crash, or with status 400 (Bad Request) if the crash has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/crashes")
    @Timed
    public ResponseEntity<Crash> createCrash(@RequestBody Crash crash) throws URISyntaxException {
        log.debug("REST request to save Crash : {}", crash);
        if (crash.getId() != null) {
            throw new BadRequestAlertException("A new crash cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Crash result = crashRepository.save(crash);
        return ResponseEntity.created(new URI("/api/crashes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /crashes : Updates an existing crash.
     *
     * @param crash the crash to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated crash,
     * or with status 400 (Bad Request) if the crash is not valid,
     * or with status 500 (Internal Server Error) if the crash couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/crashes")
    @Timed
    public ResponseEntity<Crash> updateCrash(@RequestBody Crash crash) throws URISyntaxException {
        log.debug("REST request to update Crash : {}", crash);
        if (crash.getId() == null) {
            return createCrash(crash);
        }
        Crash result = crashRepository.save(crash);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, crash.getId().toString()))
            .body(result);
    }

    /**
     * GET  /crashes : get all the crashes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of crashes in body
     */
    @GetMapping("/crashes")
    @Timed
    public List<Crash> getAllCrashes() {
        log.debug("REST request to get all Crashes");
        return crashRepository.findAll();
        }

    /**
     * GET  /crashes/:id : get the "id" crash.
     *
     * @param id the id of the crash to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the crash, or with status 404 (Not Found)
     */
    @GetMapping("/crashes/{id}")
    @Timed
    public ResponseEntity<Crash> getCrash(@PathVariable Long id) {
        log.debug("REST request to get Crash : {}", id);
        Crash crash = crashRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(crash));
    }

    /**
     * DELETE  /crashes/:id : delete the "id" crash.
     *
     * @param id the id of the crash to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/crashes/{id}")
    @Timed
    public ResponseEntity<Void> deleteCrash(@PathVariable Long id) {
        log.debug("REST request to delete Crash : {}", id);
        crashRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
