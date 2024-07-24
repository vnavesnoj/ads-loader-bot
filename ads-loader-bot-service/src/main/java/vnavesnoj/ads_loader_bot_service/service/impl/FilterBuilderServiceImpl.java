package vnavesnoj.ads_loader_bot_service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vnavesnoj.ads_loader_bot_persistence.database.entity.FilterBuilder;
import vnavesnoj.ads_loader_bot_service.database.repository.FilterBuilderRepository;
import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderCreateDto;
import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderEditDto;
import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderReadDto;
import vnavesnoj.ads_loader_bot_service.exception.PatternValidationException;
import vnavesnoj.ads_loader_bot_service.exception.UnknownInputFieldException;
import vnavesnoj.ads_loader_bot_service.mapper.Mapper;
import vnavesnoj.ads_loader_bot_service.service.FilterBuilderService;
import vnavesnoj.ads_loader_bot_service.validator.JsonPatternValidator;
import vnavesnoj.ads_loader_bot_service.validator.ObjectValidator;

import java.lang.reflect.Field;
import java.util.Arrays;
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
    private final JsonPatternValidator jsonPatternValidator;

    private final ObjectMapper objectMapper;
    private final Validator validator;

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
                    if (jsonPatternValidator.fieldExists(fb.getSpot().getAnalyzer(), input)) {
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
                    jsonPatternValidator.validateJsonPattern(pattern, item.getSpot().getAnalyzer());
                    item.setPattern(pattern);
                    return item;
                })
                .map(filterBuilderRepository::saveAndFlush)
                .map(filterBuilderReadMapper::map);
    }

    @Override
    @Transactional
    public Optional<FilterBuilderReadDto> update(Long id, FilterBuilderEditDto filterBuilder) {
        return filterBuilderRepository.findById(id)
                .map(item -> filterBuilderEditMapper.map(filterBuilder, item))
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

    @SneakyThrows(JsonProcessingException.class)
    private FilterBuilder validatePattern(FilterBuilder filterBuilder) {
        final Class<?> patternClass = getPatternClass(filterBuilder);
        final Object pattern = objectMapper.readValue(filterBuilder.getPattern(), patternClass);
        final var errors = validator.validate(pattern);
        if (!errors.isEmpty()) {
            throw new PatternValidationException(errors);
        }
        return filterBuilder;
    }

    private String validateCurrentInput(FilterBuilder filterBuilder, String currentInput) {
        final Class<?> patternClass = getPatternClass(filterBuilder);
        return Arrays.stream(patternClass.getDeclaredFields())
                .map(Field::getName)
                .filter(field -> field.equalsIgnoreCase(currentInput))
                .findFirst()
                .orElseThrow((() ->
                        new UnknownInputFieldException("unknown input field '"
                                + currentInput + "' for FilterBuilder.id = "
                                + filterBuilder.getId())
                ));
    }

    private static Class<?> getPatternClass(FilterBuilder filterBuilder) {
        return filterBuilder.getSpot()
                .getAnalyzer()
                .getPatternClass();
    }
}
