package com.tsystems.conmob.cmam.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsystems.conmob.cmam.domain.Composite;

import com.tsystems.conmob.cmam.repository.CompositeRepository;
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
 * REST controller for managing Composite.
 */
@RestController
@RequestMapping("/api")
public class CompositeResource {

    private final Logger log = LoggerFactory.getLogger(CompositeResource.class);

    private static final String ENTITY_NAME = "composite";

    private final CompositeRepository compositeRepository;

    public CompositeResource(CompositeRepository compositeRepository) {
        this.compositeRepository = compositeRepository;
    }

    /**
     * POST  /composites : Create a new composite.
     *
     * @param composite the composite to create
     * @return the ResponseEntity with status 201 (Created) and with body the new composite, or with status 400 (Bad Request) if the composite has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/composites")
    @Timed
    public ResponseEntity<Composite> createComposite(@RequestBody Composite composite) throws URISyntaxException {
        log.debug("REST request to save Composite : {}", composite);
        if (composite.getId() != null) {
            throw new BadRequestAlertException("A new composite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Composite result = compositeRepository.save(composite);
        return ResponseEntity.created(new URI("/api/composites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /composites : Updates an existing composite.
     *
     * @param composite the composite to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated composite,
     * or with status 400 (Bad Request) if the composite is not valid,
     * or with status 500 (Internal Server Error) if the composite couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/composites")
    @Timed
    public ResponseEntity<Composite> updateComposite(@RequestBody Composite composite) throws URISyntaxException {
        log.debug("REST request to update Composite : {}", composite);
        if (composite.getId() == null) {
            return createComposite(composite);
        }
        Composite result = compositeRepository.save(composite);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, composite.getId().toString()))
            .body(result);
    }

    /**
     * GET  /composites : get all the composites.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of composites in body
     */
    @GetMapping("/composites")
    @Timed
    public List<Composite> getAllComposites() {
        log.debug("REST request to get all Composites");
        return compositeRepository.findAll();
        }

    /**
     * GET  /composites/:id : get the "id" composite.
     *
     * @param id the id of the composite to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the composite, or with status 404 (Not Found)
     */
    @GetMapping("/composites/{id}")
    @Timed
    public ResponseEntity<Composite> getComposite(@PathVariable Long id) {
        log.debug("REST request to get Composite : {}", id);
        Composite composite = compositeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(composite));
    }

    /**
     * DELETE  /composites/:id : delete the "id" composite.
     *
     * @param id the id of the composite to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/composites/{id}")
    @Timed
    public ResponseEntity<Void> deleteComposite(@PathVariable Long id) {
        log.debug("REST request to delete Composite : {}", id);
        compositeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
