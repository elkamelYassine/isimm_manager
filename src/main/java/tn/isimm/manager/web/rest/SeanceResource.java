package tn.isimm.manager.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import tn.isimm.manager.domain.Seance;
import tn.isimm.manager.repository.SeanceRepository;
import tn.isimm.manager.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tn.isimm.manager.domain.Seance}.
 */
@RestController
@RequestMapping("/api/seances")
@Transactional
public class SeanceResource {

    private final Logger log = LoggerFactory.getLogger(SeanceResource.class);

    private static final String ENTITY_NAME = "seance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SeanceRepository seanceRepository;

    public SeanceResource(SeanceRepository seanceRepository) {
        this.seanceRepository = seanceRepository;
    }

    /**
     * {@code POST  /seances} : Create a new seance.
     *
     * @param seance the seance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new seance, or with status {@code 400 (Bad Request)} if the seance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Seance> createSeance(@Valid @RequestBody Seance seance) throws URISyntaxException {
        log.debug("REST request to save Seance : {}", seance);
        if (seance.getId() != null) {
            throw new BadRequestAlertException("A new seance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Seance result = seanceRepository.save(seance);
        return ResponseEntity
            .created(new URI("/api/seances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /seances/:id} : Updates an existing seance.
     *
     * @param id the id of the seance to save.
     * @param seance the seance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seance,
     * or with status {@code 400 (Bad Request)} if the seance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the seance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Seance> updateSeance(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Seance seance
    ) throws URISyntaxException {
        log.debug("REST request to update Seance : {}, {}", id, seance);
        if (seance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, seance.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!seanceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Seance result = seanceRepository.save(seance);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, seance.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /seances/:id} : Partial updates given fields of an existing seance, field will ignore if it is null
     *
     * @param id the id of the seance to save.
     * @param seance the seance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seance,
     * or with status {@code 400 (Bad Request)} if the seance is not valid,
     * or with status {@code 404 (Not Found)} if the seance is not found,
     * or with status {@code 500 (Internal Server Error)} if the seance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Seance> partialUpdateSeance(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Seance seance
    ) throws URISyntaxException {
        log.debug("REST request to partial update Seance partially : {}, {}", id, seance);
        if (seance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, seance.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!seanceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Seance> result = seanceRepository
            .findById(seance.getId())
            .map(existingSeance -> {
                if (seance.getJour() != null) {
                    existingSeance.setJour(seance.getJour());
                }
                if (seance.getNumSeance() != null) {
                    existingSeance.setNumSeance(seance.getNumSeance());
                }
                if (seance.getSalle() != null) {
                    existingSeance.setSalle(seance.getSalle());
                }

                return existingSeance;
            })
            .map(seanceRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, seance.getId().toString())
        );
    }

    /**
     * {@code GET  /seances} : get all the seances.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of seances in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Seance>> getAllSeances(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Seances");
        Page<Seance> page = seanceRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /seances/:id} : get the "id" seance.
     *
     * @param id the id of the seance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the seance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Seance> getSeance(@PathVariable("id") Long id) {
        log.debug("REST request to get Seance : {}", id);
        Optional<Seance> seance = seanceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(seance);
    }

    /**
     * {@code DELETE  /seances/:id} : delete the "id" seance.
     *
     * @param id the id of the seance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeance(@PathVariable("id") Long id) {
        log.debug("REST request to delete Seance : {}", id);
        seanceRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
