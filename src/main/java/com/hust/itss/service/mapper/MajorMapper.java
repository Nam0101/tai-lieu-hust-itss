package com.hust.itss.service.mapper;

import com.hust.itss.domain.Major;
import com.hust.itss.service.dto.MajorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Major} and its DTO {@link MajorDTO}.
 */
@Mapper(componentModel = "spring")
public interface MajorMapper extends EntityMapper<MajorDTO, Major> {}
