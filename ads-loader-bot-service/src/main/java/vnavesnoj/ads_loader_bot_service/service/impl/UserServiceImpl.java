package vnavesnoj.ads_loader_bot_service.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vnavesnoj.ads_loader_bot_common.database.entity.User;
import vnavesnoj.ads_loader_bot_service.database.repository.UserRepository;
import vnavesnoj.ads_loader_bot_service.dto.user.UserCreateDto;
import vnavesnoj.ads_loader_bot_service.dto.user.UserEditDto;
import vnavesnoj.ads_loader_bot_service.dto.user.UserReadDto;
import vnavesnoj.ads_loader_bot_service.mapper.Mapper;
import vnavesnoj.ads_loader_bot_service.service.UserService;

import java.time.Instant;
import java.util.Optional;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final Mapper<User, UserReadDto> userReadMapper;
    private final Mapper<UserCreateDto, User> userCreateMapper;
    private final Mapper<UserEditDto, User> userEditMapper;

    @Override
    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userReadMapper::map);
    }

    @Override
    public UserReadDto create(@NonNull UserCreateDto user) {
        return Optional.of(user)
                .map(userCreateMapper::map)
                .map(entity -> {
                    entity.setNotify(true);
                    entity.setInstant(Instant.now());
                    return entity;
                })
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::map)
                .orElseThrow();
    }

    @Override
    public Optional<UserReadDto> patch(Long id, @NonNull UserEditDto user) {
        return userRepository.findById(id)
                .map(entity -> userEditMapper.map(user, entity))
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::map);
    }

    @Override
    public boolean delete(Long id) {
        return userRepository.findById(id)
                .map(entity -> {
                    userRepository.delete(entity);
                    userRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
