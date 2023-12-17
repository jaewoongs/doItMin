package com.doitmin.webapp.api.mapper;

import com.doitmin.webapp.api.dto.BoardPostDto;
import com.doitmin.webapp.api.entities.Board;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface BoardPostMapper extends EntityMapper<BoardPostDto, Board> {
    BoardPostMapper INSTANCE = Mappers.getMapper(BoardPostMapper.class);

    @Override
    Board toEntity(final BoardPostDto dto);
}
