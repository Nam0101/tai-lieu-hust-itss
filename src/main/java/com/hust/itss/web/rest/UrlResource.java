package com.hust.itss.web.rest;

import com.hust.itss.repository.UrlRepository;
import com.hust.itss.service.UrlService;
import com.hust.itss.service.dto.UrlDTO;
import com.hust.itss.web.rest.errors.BadRequestAlertException;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.hust.itss.domain.Url}.
 */
@RestController
@RequestMapping("/api/urls")
public class UrlResource {

    private final Logger log = LoggerFactory.getLogger(UrlResource.class);

    private static final String ENTITY_NAME = "url";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UrlService urlService;

    private final UrlRepository urlRepository;

    public UrlResource(UrlService urlService, UrlRepository urlRepository) {
        this.urlService = urlService;
        this.urlRepository = urlRepository;
    }

    /**
     * {@code POST  /urls} : Create a new url.
     *
     * @param urlDTO the urlDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new urlDTO, or with status {@code 400 (Bad Request)} if the url has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<UrlDTO> createUrl(@RequestBody UrlDTO urlDTO) throws URISyntaxException {
        log.debug("REST request to save Url : {}", urlDTO);
        if (urlDTO.getId() != null) {
            throw new BadRequestAlertException("A new url cannot already have an ID", ENTITY_NAME, "idexists");
        }
        urlDTO = urlService.save(urlDTO);
        return ResponseEntity.created(new URI("/api/urls/" + urlDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, urlDTO.getId().toString()))
            .body(urlDTO);
    }

    /**
     * {@code PUT  /urls/:id} : Updates an existing url.
     *
     * @param id the id of the urlDTO to save.
     * @param urlDTO the urlDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated urlDTO,
     * or with status {@code 400 (Bad Request)} if the urlDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the urlDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UrlDTO> updateUrl(@PathVariable(value = "id", required = false) final Long id, @RequestBody UrlDTO urlDTO)
        throws URISyntaxException {
        log.debug("REST request to update Url : {}, {}", id, urlDTO);
        if (urlDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, urlDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!urlRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        urlDTO = urlService.update(urlDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, urlDTO.getId().toString()))
            .body(urlDTO);
    }

    /**
     * {@code PATCH  /urls/:id} : Partial updates given fields of an existing url, field will ignore if it is null
     *
     * @param id the id of the urlDTO to save.
     * @param urlDTO the urlDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated urlDTO,
     * or with status {@code 400 (Bad Request)} if the urlDTO is not valid,
     * or with status {@code 404 (Not Found)} if the urlDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the urlDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UrlDTO> partialUpdateUrl(@PathVariable(value = "id", required = false) final Long id, @RequestBody UrlDTO urlDTO)
        throws URISyntaxException {
        log.debug("REST request to partial update Url partially : {}, {}", id, urlDTO);
        if (urlDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, urlDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!urlRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UrlDTO> result = urlService.partialUpdate(urlDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, urlDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /urls} : get all the urls.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of urls in body.
     */
    @GetMapping("")
    public ResponseEntity<List<UrlDTO>> getAllUrls(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Urls");
        Page<UrlDTO> page = urlService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /urls/:id} : get the "id" url.
     *
     * @param id the id of the urlDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the urlDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UrlDTO> getUrl(@PathVariable("id") Long id) {
        log.debug("REST request to get Url : {}", id);
        Optional<UrlDTO> urlDTO = urlService.findOne(id);
        return ResponseUtil.wrapOrNotFound(urlDTO);
    }

    /**
     * {@code DELETE  /urls/:id} : delete the "id" url.
     *
     * @param id the id of the urlDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUrl(@PathVariable("id") Long id) {
        log.debug("REST request to delete Url : {}", id);
        urlService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
