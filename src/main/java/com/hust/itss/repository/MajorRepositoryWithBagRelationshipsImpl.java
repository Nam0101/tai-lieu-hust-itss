package com.hust.itss.repository;

import com.hust.itss.domain.Major;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class MajorRepositoryWithBagRelationshipsImpl implements MajorRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String MAJORS_PARAMETER = "majors";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Major> fetchBagRelationships(Optional<Major> major) {
        return major.map(this::fetchSubjects);
    }

    @Override
    public Page<Major> fetchBagRelationships(Page<Major> majors) {
        return new PageImpl<>(fetchBagRelationships(majors.getContent()), majors.getPageable(), majors.getTotalElements());
    }

    @Override
    public List<Major> fetchBagRelationships(List<Major> majors) {
        return Optional.of(majors).map(this::fetchSubjects).orElse(Collections.emptyList());
    }

    Major fetchSubjects(Major result) {
        return entityManager
            .createQuery("select major from Major major left join fetch major.subjects where major.id = :id", Major.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Major> fetchSubjects(List<Major> majors) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, majors.size()).forEach(index -> order.put(majors.get(index).getId(), index));
        List<Major> result = entityManager
            .createQuery("select major from Major major left join fetch major.subjects where major in :majors", Major.class)
            .setParameter(MAJORS_PARAMETER, majors)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
