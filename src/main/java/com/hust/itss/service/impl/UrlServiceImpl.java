package com.hust.itss.service.impl;

import com.hust.itss.domain.Url;
import com.hust.itss.repository.UrlRepository;
import com.hust.itss.service.UrlService;
import com.hust.itss.service.dto.UrlDTO;
import com.hust.itss.service.mapper.UrlMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.hust.itss.domain.Url}.
 */
@Service
@Transactional
public class UrlServiceImpl implements UrlService {

    private final Logger log = LoggerFactory.getLogger(UrlServiceImpl.class);

    private final UrlRepository urlRepository;

    private final UrlMapper urlMapper;

    public UrlServiceImpl(UrlRepository urlRepository, UrlMapper urlMapper) {
        this.urlRepository = urlRepository;
        this.urlMapper = urlMapper;
    }

    @Override
    public UrlDTO save(UrlDTO urlDTO) {
        log.debug("Request to save Url : {}", urlDTO);
        Url url = urlMapper.toEntity(urlDTO);
        url = urlRepository.save(url);
        return urlMapper.toDto(url);
    }

    @Override
    public UrlDTO update(UrlDTO urlDTO) {
        log.debug("Request to update Url : {}", urlDTO);
        Url url = urlMapper.toEntity(urlDTO);
        url = urlRepository.save(url);
        return urlMapper.toDto(url);
    }

    @Override
    public Optional<UrlDTO> partialUpdate(UrlDTO urlDTO) {
        log.debug("Request to partially update Url : {}", urlDTO);

        return urlRepository
            .findById(urlDTO.getId())
            .map(existingUrl -> {
                urlMapper.partialUpdate(existingUrl, urlDTO);

                return existingUrl;
            })
            .map(urlRepository::save)
            .map(urlMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UrlDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Urls");
        return urlRepository.findAll(pageable).map(urlMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UrlDTO> findOne(Long id) {
        log.debug("Request to get Url : {}", id);
        return urlRepository.findById(id).map(urlMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Url : {}", id);
        urlRepository.deleteById(id);
    }
}
