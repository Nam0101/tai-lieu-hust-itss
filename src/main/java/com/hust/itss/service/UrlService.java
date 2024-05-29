package com.hust.itss.service;

import com.hust.itss.service.dto.UrlDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.hust.itss.domain.Url}.
 */
public interface UrlService {
    /**
     * Save a url.
     *
     * @param urlDTO the entity to save.
     * @return the persisted entity.
     */
    UrlDTO save(UrlDTO urlDTO);

    /**
     * Updates a url.
     *
     * @param urlDTO the entity to update.
     * @return the persisted entity.
     */
    UrlDTO update(UrlDTO urlDTO);

    /**
     * Partially updates a url.
     *
     * @param urlDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UrlDTO> partialUpdate(UrlDTO urlDTO);

    /**
     * Get all the urls.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UrlDTO> findAll(Pageable pageable);

    /**
     * Get the "id" url.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UrlDTO> findOne(Long id);

    /**
     * Delete the "id" url.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
