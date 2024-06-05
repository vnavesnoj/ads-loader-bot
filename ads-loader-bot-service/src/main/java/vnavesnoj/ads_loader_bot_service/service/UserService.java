package vnavesnoj.ads_loader_bot_service.service;

import vnavesnoj.ads_loader_bot_service.dto.UserCreateDto;
import vnavesnoj.ads_loader_bot_service.dto.UserEditDto;
import vnavesnoj.ads_loader_bot_service.dto.UserReadDto;

import java.util.Optional;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface UserService {

    Optional<UserReadDto> findById(Long id);

    UserReadDto create(UserCreateDto user);

    Optional<UserReadDto> update(Long id, UserEditDto user);

    boolean delete(Long id);
}
