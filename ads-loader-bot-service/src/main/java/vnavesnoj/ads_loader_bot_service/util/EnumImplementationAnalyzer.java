package vnavesnoj.ads_loader_bot_service.util;

import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.exception.EnumImplementationException;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Component
public class EnumImplementationAnalyzer {

    public <E extends Enum<?>, T> Map<E, T> getUniqueEnumImplementations(Class<E> enumeration, List<T> implementations, Function<T, E> function) {
        final var duplicateImplementations = getDuplicateImplementations(implementations, function);
        final var notImplementedEnums = getNotImplementedEnums(enumeration, implementations, function);
        if (duplicateImplementations.isEmpty() && notImplementedEnums.isEmpty()) {
            final Map<E, T> uniqueImplementations = new HashMap<>();
            implementations.forEach(item -> uniqueImplementations.put(function.apply(item), item));
            return uniqueImplementations;
        } else if (duplicateImplementations.isEmpty()) {
            throw new EnumImplementationException("there are not implemented enums: " + notImplementedEnums);
        } else {
            final var duplications = duplicateImplementations.stream()
                    .map(item -> item.getClass().getName() + "(" + function.apply(item) + ")")
                    .collect(Collectors.joining(",", "[", "]"));
            throw new EnumImplementationException("there are duplicate implementations: " + duplications);
        }
    }

    public <E extends Enum<?>, T> List<E> getNotImplementedEnums(Class<E> enumeration, List<T> implementations, Function<T, E> function) {
        return Arrays.stream(enumeration.getEnumConstants())
                .filter(e -> implementations.stream()
                        .noneMatch(i -> function.apply(i) == e))
                .toList();
    }

    public <E extends Enum<?>, T> List<T> getDuplicateImplementations(List<T> implementations, Function<T, E> function) {
        List<T> duplicateImplementations = new ArrayList<>();
        implementations.stream()
                .collect(Collectors.groupingBy(function))
                .values().stream()
                .filter(list -> list.size() > 1)
                .forEach(duplicateImplementations::addAll);
        return duplicateImplementations;
    }
}
