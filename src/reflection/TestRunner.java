package reflection;

import reflection.annotation.*;
import reflection.config.AnnotationListConfig;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TestRunner {
    private static Map<Class<? extends Annotation>, List<Method>> mapAnnotationsByPackage = null;
    private static final Set<Class<? extends Annotation>> setAllowedAnnotationsOnStaticMethods = Set.of(AfterSuite.class, BeforeSuite.class);
    private static final Set<Class<? extends Annotation>> setUnresolvedAnnotationsOnStaticMethods = Set.of(Test.class, CsvSource.class, BeforeTest.class, AfterTest.class);
    private static final Set<Class<? extends Annotation>> setAllowedAnnotationsNonStaticMethods = Set.of(Test.class, CsvSource.class);
    private static final Set<Class<? extends Annotation>> setUnresolvedAnnotationsNonStaticMethods = Set.of(AfterSuite.class, BeforeSuite.class, BeforeTest.class, AfterTest.class);
    private static final Set<Class<? extends Annotation>> setAllowedOtherAnnotations = Set.of(BeforeTest.class, AfterTest.class);
    private static final Set<Class<? extends Annotation>> setUnresolvedOtherAnnotations = Set.of(AfterSuite.class, Test.class, BeforeSuite.class, CsvSource.class);

    static {
        final String packageName = "src.reflection.annotation";
        try {

            mapAnnotationsByPackage = AnnotationListConfig.generatingListAnnotationsByPackage(packageName).stream()
                    .collect(Collectors.toMap(Function.identity(), annotationClass -> new ArrayList<>()));

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void runTests(Class<?> aClass) {
        try {
            Object instance = aClass.getDeclaredConstructor().newInstance();

            Method[] methods = aClass.getDeclaredMethods();

            generatingMapAnnotationsByPackage(mapAnnotationsByPackage, methods);

            List<Method> beforeSuiteMethods = mapAnnotationsByPackage.get(BeforeSuite.class);

            List<Method> afterSuiteMethods = mapAnnotationsByPackage.get(AfterSuite.class);

            if (beforeSuiteMethods.size() > 1 || afterSuiteMethods.size() > 1) {
                throw new RuntimeException("Only one method with @BeforeSuite and @AfterSuite is allowed");
            }

            List<Method> testMethods = mapAnnotationsByPackage.get(Test.class);

            if (testMethods.isEmpty()) {
                throw new RuntimeException("Methods with the @Test annotation specify such an annotation for the test method");
            }

            if (!beforeSuiteMethods.isEmpty()) {
                beforeSuiteMethods.getFirst().invoke(instance);
            }

            sortTestMethods(testMethods);

            List<Method> beforeTestMethods = mapAnnotationsByPackage.get(BeforeTest.class);
            List<Method> afterTestMethods = mapAnnotationsByPackage.get(AfterTest.class);

            runBasicTests(testMethods, beforeTestMethods, afterTestMethods, instance);

            if (!afterSuiteMethods.isEmpty()) {
                afterSuiteMethods.getFirst().invoke(instance);
            }

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private static void generatingMapAnnotationsByPackage(Map<Class<? extends Annotation>, List<Method>> mapAnnotationsByPackage, Method[] methods) {

        for (Method method : methods) {
            List<Annotation> annotations = List.of(method.getDeclaredAnnotations());

            if (!annotations.isEmpty()) {
                boolean isStatic = Modifier.isStatic(method.getModifiers());
                if (isStatic) {
                    fillMapWithStaticMethods(mapAnnotationsByPackage, annotations, method);
                } else {
                    fillMapWithNoStaticMethods(mapAnnotationsByPackage, annotations, method);
                }

            }
        }

    }

    private static void fillMapWithStaticMethods(Map<Class<? extends Annotation>, List<Method>> mapAnnotationsByPackage,
                                                 List<Annotation> annotations,
                                                 Method method) {

        AtomicInteger count = new AtomicInteger();

        annotations.forEach(element -> {

            boolean existUnresolvedAnnotations = setUnresolvedAnnotationsOnStaticMethods.contains(element.annotationType());

            if (existUnresolvedAnnotations) {
                throw new RuntimeException("Inconsistent annotations");
            }

            boolean existAllowedAnnotations = setAllowedAnnotationsOnStaticMethods.contains(element.annotationType());

            if (existAllowedAnnotations) {
                count.getAndIncrement();

                mapAnnotationsByPackage.computeIfPresent(element.annotationType(), (aClass, methods) -> {
                    methods.add(method);
                    return methods;
                });

            }

            if (count.get() > 1) throw new RuntimeException("There cannot be two static annotations on a method");

        });

    }

    private static void fillMapWithNoStaticMethods(Map<Class<? extends Annotation>, List<Method>> mapAnnotationsByPackage,
                                                   List<Annotation> annotations,
                                                   Method method) {

        boolean existMethodsWithBasicTestAnnotations = false;
        boolean existMethodsWithUnavailableBasicTest = false;

        for (Annotation annotation : annotations) {
            if (setAllowedAnnotationsNonStaticMethods.contains(annotation.annotationType())) {
                existMethodsWithBasicTestAnnotations = true;
            }

            if (setUnresolvedAnnotationsNonStaticMethods.contains(annotation.annotationType())) {
                existMethodsWithUnavailableBasicTest = true;
            }
        }

        if (existMethodsWithBasicTestAnnotations && existMethodsWithUnavailableBasicTest) {
            throw new RuntimeException("Unavailable use of multiple annotations");
        }

        if (existMethodsWithBasicTestAnnotations && !existMethodsWithUnavailableBasicTest) {
            annotations.forEach(element -> {
                if (setAllowedAnnotationsNonStaticMethods.contains(element.annotationType())) {
                    mapAnnotationsByPackage.computeIfPresent(element.annotationType(), (aClass, methods) -> {
                        methods.add(method);
                        return methods;
                    });
                }
            });
        }

        if (!existMethodsWithBasicTestAnnotations && existMethodsWithUnavailableBasicTest) {

            AtomicInteger count = new AtomicInteger();

            annotations.forEach(element -> {
                if (setUnresolvedOtherAnnotations.contains(element.annotationType())) {
                    throw new RuntimeException("Unavailable use of annotations");
                }
                if (setAllowedOtherAnnotations.contains(element.annotationType())) {

                    count.getAndIncrement();

                    mapAnnotationsByPackage.computeIfPresent(element.annotationType(), (aClass, methods) -> {
                        methods.add(method);
                        return methods;
                    });
                }
            });

            if (count.get() > 1) throw new RuntimeException("There cannot be two static annotations on a method");

        }

    }

    private static void sortTestMethods(List<Method> testMethods) {
        testMethods.sort((m1, m2) -> {
            int p1 = m1.getAnnotation(Test.class).priority();
            int p2 = m2.getAnnotation(Test.class).priority();

            if ((p1 > 11 || p1 < 1) || (p2 > 11 || p2 < 1))
                throw new RuntimeException("The priority parameter in @Test is not in the range from 1 to 10 inclusive");

            return Integer.compare(p2, p1);
        });
    }


    private static void runBasicTests(List<Method> testMethods,
                                      List<Method> beforeTestMethods,
                                      List<Method> afterTestMethods,
                                      Object instance) throws InvocationTargetException, IllegalAccessException {
        for (Method testMethod : testMethods) {

            if (!beforeTestMethods.isEmpty()) {
                for (Method method : beforeTestMethods) {
                    method.invoke(instance);
                }
            }

            if (testMethod.isAnnotationPresent(CsvSource.class)) {

                CsvSource csvSource = testMethod.getAnnotation(CsvSource.class);

                String[] values = csvSource.value().split("\\s*,\\s*");

                Object[] parsedArgs = Arrays.stream(values)
                        .map(CastUtils::castStringInObject)
                        .toArray();

                testMethod.invoke(instance, parsedArgs);

            } else {
                testMethod.invoke(instance);
            }

            if (!afterTestMethods.isEmpty()) {
                for (Method method : afterTestMethods) {
                    method.invoke(instance);
                }
            }

        }

    }

}
