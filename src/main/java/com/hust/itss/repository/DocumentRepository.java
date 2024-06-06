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
    // get document by major id (native query)
    @Query(
        value = "SELECT d.* FROM document d INNER JOIN subject s ON d.subject_id = s.id INNER JOIN rel_major__subjects rms ON s.id = rms.subjects_id INNER JOIN major ma ON ma.id = rms.major_id AND ma.id = :majorId",
        nativeQuery = true
    )
    List<Document> findByMajorId(@Param("majorId") Long majorId);

    // get document by text search:
    //SELECT
    //  d.*
    //FROM
    //  DOCUMENT d
    //  INNER JOIN subject s ON d.subject_id = s.id
    //WHERE
    //  LOWER ( remove_accent ( s.NAME ) ) LIKE LOWER ( remove_accent ( CONCAT ( '%', 'MI1111', '%' ) ) )
    //  OR LOWER ( remove_accent ( s.code ) ) LIKE LOWER ( remove_accent ( CONCAT ( '%', 'MI1111', '%' ) ) )
    @Query(
        value = "SELECT d.* FROM document d INNER JOIN subject s ON d.subject_id = s.id WHERE LOWER(remove_accent(s.name)) LIKE LOWER(remove_accent(CONCAT('%', :searchTerm, '%'))) OR LOWER(remove_accent(s.code)) LIKE LOWER(remove_accent(CONCAT('%', :searchTerm, '%')))",
        nativeQuery = true
    )
    List<Document> findBySearchTerm(@Param("searchTerm") String searchTerm);
}
