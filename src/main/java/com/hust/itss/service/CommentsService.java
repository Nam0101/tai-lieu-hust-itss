package com.hust.itss.service;

import com.hust.itss.service.dto.CommentsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.hust.itss.domain.Comments}.
 */
public interface CommentsService {
    /**
     * Save a comments.
     *
     * @param commentsDTO the entity to save.
     * @return the persisted entity.
     */
    CommentsDTO save(CommentsDTO commentsDTO);

    /**
     * Updates a comments.
     *
     * @param commentsDTO the entity to update.
     * @return the persisted entity.
     */
    CommentsDTO update(CommentsDTO commentsDTO);

    /**
     * Partially updates a comments.
     *
     * @param commentsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CommentsDTO> partialUpdate(CommentsDTO commentsDTO);

    /**
     * Get all the comments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CommentsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" comments.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CommentsDTO> findOne(Long id);

    /**
     * Delete the "id" comments.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
