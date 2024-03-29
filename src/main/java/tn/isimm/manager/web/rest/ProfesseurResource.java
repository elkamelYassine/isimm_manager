package tn.isimm.manager.web.rest;

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
import tn.isimm.manager.domain.Professeur;
import tn.isimm.manager.repository.ProfesseurRepository;
import tn.isimm.manager.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tn.isimm.manager.domain.Professeur}.
 */
@RestController
@RequestMapping("/api/professeurs")
@Transactional
public class ProfesseurResource {

    private final Logger log = LoggerFactory.getLogger(ProfesseurResource.class);

    private static final String ENTITY_NAME = "professeur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfesseurRepository professeurRepository;

    public ProfesseurResource(ProfesseurRepository professeurRepository) {
        this.professeurRepository = professeurRepository;
    }

    /**
     * {@code POST  /professeurs} : Create a new professeur.
     *
     * @param professeur the professeur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new professeur, or with status {@code 400 (Bad Request)} if the professeur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Professeur> createProfesseur(@RequestBody Professeur professeur) throws URISyntaxException {
        log.debug("REST request to save Professeur : {}", professeur);
        if (professeur.getId() != null) {
            throw new BadRequestAlertException("A new professeur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Professeur result = professeurRepository.save(professeur);
        return ResponseEntity
            .created(new URI("/api/professeurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /professeurs/:id} : Updates an existing professeur.
     *
     * @param id the id of the professeur to save.
     * @param professeur the professeur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated professeur,
     * or with status {@code 400 (Bad Request)} if the professeur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the professeur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Professeur> updateProfesseur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Professeur professeur
    ) throws URISyntaxException {
        log.debug("REST request to update Professeur : {}, {}", id, professeur);
        if (professeur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, professeur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!professeurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Professeur result = professeurRepository.save(professeur);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, professeur.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /professeurs/:id} : Partial updates given fields of an existing professeur, field will ignore if it is null
     *
     * @param id the id of the professeur to save.
     * @param professeur the professeur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated professeur,
     * or with status {@code 400 (Bad Request)} if the professeur is not valid,
     * or with status {@code 404 (Not Found)} if the professeur is not found,
     * or with status {@code 500 (Internal Server Error)} if the professeur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Professeur> partialUpdateProfesseur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Professeur professeur
    ) throws URISyntaxException {
        log.debug("REST request to partial update Professeur partially : {}, {}", id, professeur);
        if (professeur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, professeur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!professeurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Professeur> result = professeurRepository
            .findById(professeur.getId())
            .map(existingProfesseur -> {
                if (professeur.getPrenom() != null) {
                    existingProfesseur.setPrenom(professeur.getPrenom());
                }
                if (professeur.getNom() != null) {
                    existingProfesseur.setNom(professeur.getNom());
                }
                if (professeur.getEmail() != null) {
                    existingProfesseur.setEmail(professeur.getEmail());
                }
                if (professeur.getMatricule() != null) {
                    existingProfesseur.setMatricule(professeur.getMatricule());
                }
                if (professeur.getDepartement() != null) {
                    existingProfesseur.setDepartement(professeur.getDepartement());
                }
                if (professeur.getTitre() != null) {
                    existingProfesseur.setTitre(professeur.getTitre());
                }
                if (professeur.getNumTel() != null) {
                    existingProfesseur.setNumTel(professeur.getNumTel());
                }
                if (professeur.getDateDeNaissance() != null) {
                    existingProfesseur.setDateDeNaissance(professeur.getDateDeNaissance());
                }
                if (professeur.getPhotoDeProfile() != null) {
                    existingProfesseur.setPhotoDeProfile(professeur.getPhotoDeProfile());
                }
                if (professeur.getPhotoDeProfileContentType() != null) {
                    existingProfesseur.setPhotoDeProfileContentType(professeur.getPhotoDeProfileContentType());
                }

                return existingProfesseur;
            })
            .map(professeurRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, professeur.getId().toString())
        );
    }

    /**
     * {@code GET  /professeurs} : get all the professeurs.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of professeurs in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Professeur>> getAllProfesseurs(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Professeurs");
        Page<Professeur> page;
        if (eagerload) {
            page = professeurRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = professeurRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /professeurs/:id} : get the "id" professeur.
     *
     * @param id the id of the professeur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the professeur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Professeur> getProfesseur(@PathVariable("id") Long id) {
        log.debug("REST request to get Professeur : {}", id);
        Optional<Professeur> professeur = professeurRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(professeur);
    }

    /**
     * {@code DELETE  /professeurs/:id} : delete the "id" professeur.
     *
     * @param id the id of the professeur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfesseur(@PathVariable("id") Long id) {
        log.debug("REST request to delete Professeur : {}", id);
        professeurRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
