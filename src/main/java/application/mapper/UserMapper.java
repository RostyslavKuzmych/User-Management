package application.mapper;

import application.config.MapperConfig;
import application.dto.UserRequestDto;
import application.dto.UserResponseDto;
import application.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User toEntity(UserRequestDto userRequestDto);

    UserResponseDto toDto(User user);
}
