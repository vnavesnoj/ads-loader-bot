package vnavesnoj.ads_loader_bot_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vnavesnoj.ads_loader_bot_common.mapper.Mapper;
import vnavesnoj.ads_loader_bot_persistence.database.entity.Spot;
import vnavesnoj.ads_loader_bot_service.database.repository.SpotRepository;
import vnavesnoj.ads_loader_bot_service.dto.spot.SpotReadDto;
import vnavesnoj.ads_loader_bot_service.service.SpotService;

import java.util.Optional;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Service
public class SpotServiceImpl implements SpotService {

    private final SpotRepository spotRepository;
    private final Mapper<Spot, SpotReadDto> spotReadMapper;

    @Override
    public Page<SpotReadDto> findAllByCategoryId(Integer categoryId, Pageable pageable) {
        return spotRepository.findAllByCategoryId(categoryId, pageable)
                .map(spotReadMapper::map);
    }

    @Override
    public Optional<SpotReadDto> findById(Integer id) {
        return spotRepository.findById(id)
                .map(spotReadMapper::map);
    }
}
