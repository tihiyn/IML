import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GradeCalculator {
    private static final Set<Integer> AVAIL_GRADES = Set.of(2, 3, 4, 5);

    // на самом деле, оценки не во всех странах целочисленные и шкалы могут отличаться
    // (5-бальная, 10-бальная). Лучше, чтобы метод принимал List<Grade<T>, где Grade, например,
    // абстрактный класс. Тогда проверка оценок на валидность уйдёт из метода и валидность
    // будет гарантироваться системой типов
    public double calculateAverage(List<Integer> grades) {
        if (grades == null || grades.isEmpty()) {
            throw new IllegalArgumentException("Список оценок не должен быть null или пуст");
        }
        if (!AVAIL_GRADES.containsAll(grades)) {
            throw new IllegalArgumentException("Не все оценки валидны");
        }
        return grades.stream()
            .mapToInt(Integer::intValue)
            .average()
            .getAsDouble();
    }
}

public class Task5 {
    private final GradeCalculator gc = new GradeCalculator();

    @Test
    void testCalculateAverage() {
        assertEquals(4, gc.calculateAverage(List.of(3, 5, 4)));
    }

    @Test
    void testCalculateAverageIfNotIntAvg() {
        assertEquals(4.25, gc.calculateAverage(List.of(3, 5, 4, 5)));
    }

    @Test
    void testCalculateAverageIfNull() {
        assertThrows(IllegalArgumentException.class, () -> gc.calculateAverage(null));
    }

    @Test
    void testCalculateAverageIfEmpty() {
        assertThrows(IllegalArgumentException.class, () -> gc.calculateAverage(Collections.emptyList()));
    }

    @Test
    void testCalculateAverageIfNotContainsNotAvailGrades() {
        assertThrows(IllegalArgumentException.class, () -> gc.calculateAverage(List.of(150, 2, 3)));
    }
}
