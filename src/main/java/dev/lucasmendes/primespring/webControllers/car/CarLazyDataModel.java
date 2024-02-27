package dev.lucasmendes.primespring.webControllers.car;

import dev.lucasmendes.primespring.entities.Car;
import dev.lucasmendes.primespring.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Log4j2
public class CarLazyDataModel extends LazyDataModel<Car> {

    private final CarRepository carRepository;

    @Override
    public Car getRowData(String rowKey) {
        var found = carRepository.findById(Integer.parseInt(rowKey));
        return found.orElse(null);
    }

    @Override
    public String getRowKey(Car customer) {
        return customer.getId().toString();
    }

    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return (int) carRepository.count(this.filter(filterBy));
    }

    @Override
    public List<Car> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        var pageNumber = offset / pageSize;
        var orders = sortBy.values().stream().map(entry -> {
            var isAscending = entry.getOrder().isAscending();
            var field = entry.getField();
            return isAscending ? Sort.Order.asc(field) : Sort.Order.desc(field);
        }).toList();
        var pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orders));
        var filter = this.filter(filterBy);
        var all = carRepository.findAll(filter, pageable);
        return all.getContent();
    }

    private void parseValuesAndSet(Field field, Car car, String value) throws IllegalAccessException {
        var fieldType = field.getType();
        if (fieldType.equals(Integer.class)) {
            field.set(car, Integer.parseInt(value));
            return;
        }
        if (fieldType.equals(Double.class)) {
            field.set(car, Double.parseDouble(value));
            return;
        }
        if (fieldType.equals(Long.class)) {
            field.set(car, Long.parseLong(value));
            return;
        }
        if (fieldType.equals(Boolean.class)) {
            field.set(car, Boolean.parseBoolean(value));
        }
    }

    private Example<Car> filter(Map<String, FilterMeta> filterBy) {
        var car = new Car();
        var matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase();

        for (Map.Entry<String, FilterMeta> entry : filterBy.entrySet()) {
            var property = entry.getKey();
            var value = entry.getValue().getFilterValue();

            if (value == null || value.toString().isBlank()) {
                matcher = matcher.withIgnorePaths(property);
                continue;
            }

            try {
                var field = Car.class.getDeclaredField(property);
                var fieldClass = field.getType();
                field.setAccessible(true);
                if (fieldClass.equals(value.getClass())) {
                    field.set(car, value);
                } else {
                    this.parseValuesAndSet(field, car, value.toString());
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                log.error(e.getMessage());
            } catch (Error e) {
                log.error("Unknown error: " + e.getMessage());
            }
        }

        return Example.of(car, matcher);
    }
}
