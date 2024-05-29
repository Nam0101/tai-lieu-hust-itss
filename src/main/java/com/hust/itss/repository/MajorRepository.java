package com.hust.itss.repository;

import com.hust.itss.domain.Major;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Major entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {}
