package com.douglasbuilder.orderapp.mappers;

import com.douglasbuilder.orderapp.dto.profile.ProfileResponseDTO;
import com.douglasbuilder.orderapp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfileMapper {

  ProfileResponseDTO toProfile(User user);
}
