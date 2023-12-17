package com.doitmin.webapp.api.mapper;

import com.doitmin.webapp.api.dto.BoardDto;
import com.doitmin.webapp.api.entities.Board;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface BoardMapper extends EntityMapper<BoardDto, Board> {
    BoardMapper INSTANCE = Mappers.getMapper(BoardMapper.class);

    @Override
    Board toEntity(final BoardDto dto);

    @Override
    BoardDto toDto(final Board entity);
}

