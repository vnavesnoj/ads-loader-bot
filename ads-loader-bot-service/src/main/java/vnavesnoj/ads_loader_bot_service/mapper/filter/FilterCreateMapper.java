package vnavesnoj.ads_loader_bot_service.mapper.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.database.entity.Filter;
import vnavesnoj.ads_loader_bot_service.database.repository.UserRepository;
import vnavesnoj.ads_loader_bot_service.dto.filter.FilterCreateDto;
import vnavesnoj.ads_loader_bot_service.mapper.Mapper;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Log4j2
@RequiredArgsConstructor
@Component
public class FilterCreateMapper implements Mapper<FilterCreateDto, Filter> {

    private final UserRepository userRepository;

    @Override
    public Filter map(FilterCreateDto object) {
        final var user = userRepository.findById(object.getUserId())
                .orElseThrow(() -> log.throwing(new IllegalArgumentException(
                        "user with id = " + object.getUserId() + " does not exist"
                )));
        return Filter.builder()
                .name(object.getName())
                .platform(object.getPlatform())
                .spot(object.getSpot())
                .jsonPattern(object.getJsonPattern())
                .user(user)
                .build();
    }
}
