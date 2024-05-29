package com.hust.itss.repository;

import com.hust.itss.domain.Subject;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Subject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Query(
        value = "SELECT * FROM subject WHERE " +
        "LOWER(remove_accent(name)) LIKE LOWER(remove_accent(CONCAT('%', :searchTerm, '%'))) OR " +
        "LOWER(remove_accent(code)) LIKE LOWER(remove_accent(CONCAT('%', :searchTerm, '%')))",
        nativeQuery = true
    )
    List<Subject> findByNameOrCodeIgnoreCase(@Param("searchTerm") String searchTerm);
}
