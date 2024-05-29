package com.hust.itss.service;

import com.hust.itss.service.dto.MajorDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.hust.itss.domain.Major}.
 */
public interface MajorService {
    /**
     * Save a major.
     *
     * @param majorDTO the entity to save.
     * @return the persisted entity.
     */
    MajorDTO save(MajorDTO majorDTO);

    /**
     * Updates a major.
     *
     * @param majorDTO the entity to update.
     * @return the persisted entity.
     */
    MajorDTO update(MajorDTO majorDTO);

    /**
     * Partially updates a major.
     *
     * @param majorDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MajorDTO> partialUpdate(MajorDTO majorDTO);

    /**
     * Get all the majors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MajorDTO> findAll(Pageable pageable);

    /**
     * Get all the MajorDTO where Subject is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<MajorDTO> findAllWhereSubjectIsNull();

    /**
     * Get the "id" major.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MajorDTO> findOne(Long id);

    /**
     * Delete the "id" major.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
