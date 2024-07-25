package vnavesnoj.ads_loader_bot_service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vnavesnoj.ads_loader_bot_persistence.database.entity.FilterBuilder;
import vnavesnoj.ads_loader_bot_service.database.repository.FilterBuilderRepository;
import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderCreateDto;
import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderEditDto;
import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderReadDto;
import vnavesnoj.ads_loader_bot_service.exception.PatternCastException;
import vnavesnoj.ads_loader_bot_service.exception.UnknownInputFieldException;
import vnavesnoj.ads_loader_bot_service.mapper.Mapper;
import vnavesnoj.ads_loader_bot_service.service.FilterBuilderService;
import vnavesnoj.ads_loader_bot_service.validator.ObjectValidator;
import vnavesnoj.ads_loader_bot_service.validator.component.PatternValidatorHelper;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FilterBuilderServiceImpl implements FilterBuilderService {

    private final FilterBuilderRepository filterBuilderRepository;

    private final Mapper<FilterBuilder, FilterBuilderReadDto> filterBuilderReadMapper;
    private final Mapper<FilterBuilderCreateDto, FilterBuilder> filterBuilderCreateMapper;
    private final Mapper<FilterBuilderEditDto, FilterBuilder> filterBuilderEditMapper;

    private final ObjectValidator<FilterBuilderCreateDto> patternCreateValidator;
    private final ObjectValidator<FilterBuilderEditDto> patternEditValidator;
    private final PatternValidatorHelper patternValidatorHelper;

    private final ObjectMapper objectMapper;

    @Override
    public Optional<FilterBuilderReadDto> findById(Long id) {
        return filterBuilderRepository.findById(id)
                .map(filterBuilderReadMapper::map);
    }

    @Override
    public Optional<FilterBuilderReadDto> findByIdAndUserId(Long id, Long userId) {
        return filterBuilderRepository.findByIdAndUserId(id, userId)
                .map(filterBuilderReadMapper::map);
    }

    @Override
    @Transactional
    public FilterBuilderReadDto create(FilterBuilderCreateDto filterBuilder) {
        return Optional.ofNullable(filterBuilder)
                .map(patternCreateValidator::validate)
                .map(filterBuilderCreateMapper::map)
                .map(filterBuilderRepository::saveAndFlush)
                .map(filterBuilderReadMapper::map)
                .orElseThrow(NullPointerException::new);
    }

    @Override
    @Transactional
    public Optional<FilterBuilderReadDto> updateCurrentInput(Long id, String input) {
        return filterBuilderRepository.findById(id)
                .filter(fb -> {
                    if (patternValidatorHelper.fieldExists(fb.getSpot().getAnalyzer(), input)) {
                        return true;
                    } else {
                        throw new UnknownInputFieldException("unknown input field '"
                                + input + "' for FilterBuilder.id = "
                                + id);
                    }
                })
                .map(item -> {
                    item.setCurrentInput(input);
                    return item;
                })
                .map(filterBuilderRepository::saveAndFlush)
                .map(filterBuilderReadMapper::map);
    }

    @Override
    @Transactional
    public Optional<FilterBuilderReadDto> updatePattern(Long id, String pattern) {
        return filterBuilderRepository.findById(id)
                .map(item -> {
                    patternValidatorHelper.validateJsonPattern(pattern, item.getSpot().getAnalyzer());
                    item.setPattern(pattern);
                    return item;
                })
                .map(filterBuilderRepository::saveAndFlush)
                .map(filterBuilderReadMapper::map);
    }

    @SneakyThrows
    @Override
    public Optional<FilterBuilderReadDto> updatePatternField(Long id, Object value) {
        final var fb = filterBuilderRepository.findById(id)
                .orElse(null);
        if (fb == null) {
            return Optional.empty();
        }
        final Class<?> patternClass = fb.getSpot().getAnalyzer().getPatternClass();
        final Object pattern;
        try {
            pattern = objectMapper.readValue(fb.getPattern(), patternClass);
        } catch (JsonProcessingException e) {
            throw new PatternCastException(e);
        }
        final var currentInput = Optional.ofNullable(fb.getCurrentInput())
                .orElseThrow(() ->
                        new NullPointerException("current input for the FilterBuilder with id = %s is null".formatted(fb.getId()))
                );
        final Field field;
        try {
            field = pattern.getClass().getDeclaredField(currentInput);
        } catch (NoSuchFieldException exception) {
            throw new RuntimeException(
                    "field '%s' for the FilterBuilder with id = %s not exists".formatted(currentInput, fb.getId()),
                    exception
            );
        }
        field.setAccessible(true);
        field.set(pattern, value);
        field.setAccessible(false);
        patternValidatorHelper.validatePatternField(pattern, field.getName());
        fb.setPattern(objectMapper.writeValueAsString(pattern));
        final var saved = filterBuilderRepository.saveAndFlush(fb);
        return Optional.of(filterBuilderReadMapper.map(saved));
    }

    @Override
    @Transactional
    public Optional<FilterBuilderReadDto> update(Long id, FilterBuilderEditDto filterBuilder) {
        return filterBuilderRepository.findById(id)
                .map(item -> {
                    final var validatedDto = patternEditValidator.validate(filterBuilder);
                    return filterBuilderEditMapper.map(validatedDto, item);
                })
                .map(filterBuilderRepository::saveAndFlush)
                .map(filterBuilderReadMapper::map);
    }

    @Override
    public Optional<FilterBuilderReadDto> findByUserId(Long userId) {
        return filterBuilderRepository.findByUserId(userId)
                .map(filterBuilderReadMapper::map);
    }

    @Override
    @Transactional
    public boolean deleteByUserId(Long userId) {
        return filterBuilderRepository.findByUserId(userId)
                .map(item -> {
                    filterBuilderRepository.delete(item);
                    filterBuilderRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
