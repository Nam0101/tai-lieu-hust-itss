package com.hust.itss.repository;

import com.hust.itss.domain.Document;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Document entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    @Query(
        value = "SELECT d.* " +
        "FROM document d " +
        "INNER JOIN subject s ON d.subject_id = s.id " +
        "INNER JOIN rel_major__subjects rms ON s.id = rms.subjects_id " +
        "INNER JOIN major ma ON ma.id = rms.major_id AND ma.id = :majorId ORDER BY d.rating DESC",
        nativeQuery = true
    )
    List<Document> findByMajorId(@Param("majorId") Long majorId);

    @Query(
        value = "SELECT d.* " +
        "FROM document d " +
        "INNER JOIN subject s ON d.subject_id = s.id " +
        "WHERE LOWER(remove_accent(s.name)) LIKE LOWER(remove_accent(CONCAT('%', :searchTerm, '%'))) " +
        "OR LOWER(remove_accent(s.code)) LIKE LOWER(remove_accent(CONCAT('%', :searchTerm, '%'))) ORDER BY d.rating DESC",
        nativeQuery = true
    )
    List<Document> findBySearchTerm(@Param("searchTerm") String searchTerm);
}
