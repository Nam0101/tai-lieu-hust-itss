package com.hust.itss.service.impl;

import com.hust.itss.domain.Major;
import com.hust.itss.repository.MajorRepository;
import com.hust.itss.service.MajorService;
import com.hust.itss.service.dto.MajorDTO;
import com.hust.itss.service.mapper.MajorMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.hust.itss.domain.Major}.
 */
@Service
@Transactional
public class MajorServiceImpl implements MajorService {

    private final Logger log = LoggerFactory.getLogger(MajorServiceImpl.class);

    private final MajorRepository majorRepository;

    private final MajorMapper majorMapper;

    public MajorServiceImpl(MajorRepository majorRepository, MajorMapper majorMapper) {
        this.majorRepository = majorRepository;
        this.majorMapper = majorMapper;
    }

    @Override
    public MajorDTO save(MajorDTO majorDTO) {
        log.debug("Request to save Major : {}", majorDTO);
        Major major = majorMapper.toEntity(majorDTO);
        major = majorRepository.save(major);
        return majorMapper.toDto(major);
    }

    @Override
    public MajorDTO update(MajorDTO majorDTO) {
        log.debug("Request to update Major : {}", majorDTO);
        Major major = majorMapper.toEntity(majorDTO);
        major = majorRepository.save(major);
        return majorMapper.toDto(major);
    }

    @Override
    public Optional<MajorDTO> partialUpdate(MajorDTO majorDTO) {
        log.debug("Request to partially update Major : {}", majorDTO);

        return majorRepository
            .findById(majorDTO.getId())
            .map(existingMajor -> {
                majorMapper.partialUpdate(existingMajor, majorDTO);

                return existingMajor;
            })
            .map(majorRepository::save)
            .map(majorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MajorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Majors");
        return majorRepository.findAll(pageable).map(majorMapper::toDto);
    }

    /**
     *  Get all the majors where Subject is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MajorDTO> findAllWhereSubjectIsNull() {
        log.debug("Request to get all majors where Subject is null");
        return StreamSupport.stream(majorRepository.findAll().spliterator(), false)
            .filter(major -> major.getSubject() == null)
            .map(majorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MajorDTO> findOne(Long id) {
        log.debug("Request to get Major : {}", id);
        return majorRepository.findById(id).map(majorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Major : {}", id);
        majorRepository.deleteById(id);
    }
}
