package com.tsystems.conmob.cmam.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsystems.conmob.cmam.domain.ICNPush;

import com.tsystems.conmob.cmam.repository.ICNPushRepository;
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
 * REST controller for managing ICNPush.
 */
@RestController
@RequestMapping("/api")
public class ICNPushResource {

    private final Logger log = LoggerFactory.getLogger(ICNPushResource.class);

    private static final String ENTITY_NAME = "iCNPush";

    private final ICNPushRepository iCNPushRepository;

    public ICNPushResource(ICNPushRepository iCNPushRepository) {
        this.iCNPushRepository = iCNPushRepository;
    }

    /**
     * POST  /icn-pushes : Create a new iCNPush.
     *
     * @param iCNPush the iCNPush to create
     * @return the ResponseEntity with status 201 (Created) and with body the new iCNPush, or with status 400 (Bad Request) if the iCNPush has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/icn-pushes")
    @Timed
    public ResponseEntity<ICNPush> createICNPush(@RequestBody ICNPush iCNPush) throws URISyntaxException {
        log.debug("REST request to save ICNPush : {}", iCNPush);
        if (iCNPush.getId() != null) {
            throw new BadRequestAlertException("A new iCNPush cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ICNPush result = iCNPushRepository.save(iCNPush);
        return ResponseEntity.created(new URI("/api/icn-pushes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /icn-pushes : Updates an existing iCNPush.
     *
     * @param iCNPush the iCNPush to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated iCNPush,
     * or with status 400 (Bad Request) if the iCNPush is not valid,
     * or with status 500 (Internal Server Error) if the iCNPush couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/icn-pushes")
    @Timed
    public ResponseEntity<ICNPush> updateICNPush(@RequestBody ICNPush iCNPush) throws URISyntaxException {
        log.debug("REST request to update ICNPush : {}", iCNPush);
        if (iCNPush.getId() == null) {
            return createICNPush(iCNPush);
        }
        ICNPush result = iCNPushRepository.save(iCNPush);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, iCNPush.getId().toString()))
            .body(result);
    }

    /**
     * GET  /icn-pushes : get all the iCNPushes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of iCNPushes in body
     */
    @GetMapping("/icn-pushes")
    @Timed
    public List<ICNPush> getAllICNPushes() {
        log.debug("REST request to get all ICNPushes");
        return iCNPushRepository.findAll();
        }

    /**
     * GET  /icn-pushes/:id : get the "id" iCNPush.
     *
     * @param id the id of the iCNPush to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the iCNPush, or with status 404 (Not Found)
     */
    @GetMapping("/icn-pushes/{id}")
    @Timed
    public ResponseEntity<ICNPush> getICNPush(@PathVariable Long id) {
        log.debug("REST request to get ICNPush : {}", id);
        ICNPush iCNPush = iCNPushRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(iCNPush));
    }

    /**
     * DELETE  /icn-pushes/:id : delete the "id" iCNPush.
     *
     * @param id the id of the iCNPush to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/icn-pushes/{id}")
    @Timed
    public ResponseEntity<Void> deleteICNPush(@PathVariable Long id) {
        log.debug("REST request to delete ICNPush : {}", id);
        iCNPushRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
