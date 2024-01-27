package reflection.config;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

public final class AnnotationListConfig {

    public static List<Class<? extends Annotation>> generatingListAnnotationsByPackage(String packageName) throws ClassNotFoundException {
        String path = packageName.replace(".", "/");
        File directory = new File(path);

        File[] files = directory.listFiles();
        List<Class<? extends Annotation>> list = null;

        if (directory.exists() && nonNull(files) && files.length != 0) {

            list = new ArrayList<>(files.length);

            for (File file : files) {
                if (file.getName().endsWith(".java")) {
                    String className = packageName.substring(4, packageName.length()) + '.' + file.getName().substring(0, file.getName().length() - 5);
                    Class<? extends Annotation> clazz = (Class<? extends Annotation>) Class.forName(className);

                    list.add(clazz);
                }
            }


        }

        return list;

    }

}
