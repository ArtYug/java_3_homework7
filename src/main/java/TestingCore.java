import annotations.AfterSuite;
import annotations.BeforeSuite;
import annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;

/*                               Homework 7 java 3
        1. Создать класс, который может выполнять «тесты».
        В качестве тестов выступают классы с наборами методов, снабженных аннотациями @Test. Класс,
        запускающий тесты, должен иметь статический метод start(Class testClass), которому в качестве
        аргумента передается объект типа Class. Из «класса-теста» вначале должен быть запущен метод с
        аннотацией @BeforeSuite, если он присутствует. Далее запускаются методы с аннотациями @Test, а
        по завершении всех тестов – метод с аннотацией @AfterSuite.
        К каждому тесту необходимо добавить приоритеты (int-числа от 1 до 10), в соответствии с которыми
        будет выбираться порядок их выполнения. Если приоритет одинаковый, то порядок не имеет
        значения. Методы с аннотациями @BeforeSuite и @AfterSuite должны присутствовать в
        единственном экземпляре. Если это не так – необходимо бросить RuntimeException при запуске
        «тестирования».
        P.S. Это практическое задание – проект, который пишется «с нуля». Данная задача не связана
        напрямую с темой тестирования через JUnit*/


public class TestingCore {
    public static void start(Class<?> tClass) {
        Method[] methods = tClass.getDeclaredMethods();
        Example example = new Example();

        long valueBeforeSuite = Arrays.stream(methods)
                .filter(method -> method.getAnnotation(BeforeSuite.class) != null).count();

        long valueAfterSuite = Arrays.stream(methods)
                .filter(method -> method.getAnnotation(AfterSuite.class) != null).count();

        if (valueBeforeSuite > 1 || valueAfterSuite > 1)
            throw new RuntimeException("Number BeforeSuite or AfterSuite annotated methods is more than one");

        Arrays.stream(methods)
                .filter(method -> method.getAnnotation(BeforeSuite.class) != null)
                .forEach(method -> {
                    try {
                        method.invoke(example);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });

        Comparator<Method> comparator = (o1, o2) -> {
            if (o1.getAnnotation(Test.class).val() > o2.getAnnotation(Test.class).val())
                return 1;
            else if (o1.getAnnotation(Test.class).val() < o1.getAnnotation(Test.class).val())
                return -1;
            else
                return 0;
        };

        Arrays.stream(methods)
                .filter(method -> method.getAnnotation(Test.class) != null)
                .sorted(comparator)
                .forEach(method -> {
                    try {
                        method.invoke(example);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });

        Arrays.stream(methods)
                .filter(method -> method.getAnnotation(AfterSuite.class) != null)
                .forEach(method -> {
                    try {
                        method.invoke(example);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });

    }
}
