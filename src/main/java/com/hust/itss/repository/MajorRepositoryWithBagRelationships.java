package com.hust.itss.repository;

import com.hust.itss.domain.Major;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface MajorRepositoryWithBagRelationships {
    Optional<Major> fetchBagRelationships(Optional<Major> major);

    List<Major> fetchBagRelationships(List<Major> majors);

    Page<Major> fetchBagRelationships(Page<Major> majors);
}
