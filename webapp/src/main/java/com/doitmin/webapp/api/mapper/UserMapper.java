package com.doitmin.webapp.api.mapper;

import com.doitmin.webapp.api.dto.SignUpDto;
import com.doitmin.webapp.api.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends EntityMapper<SignUpDto, User> {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Override
    @Mapping(target = "id", constant = "0L")
    @Mapping(target = "salt", constant = "null")
    @Mapping(target = "nickname", source = "nickname")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "profileImageUrl", constant = "null")
    User toEntity(final SignUpDto dto);
}
