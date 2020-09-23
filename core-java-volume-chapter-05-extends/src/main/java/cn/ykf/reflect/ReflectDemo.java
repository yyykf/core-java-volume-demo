package cn.ykf.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Scanner;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-09-23
 */
public class ReflectDemo {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter Class Name (e.g. java.util.Date):");
        String name = in.next();

        try {
            Class<?> clazz = Class.forName(name);
            Class<?> superclass = clazz.getSuperclass();
            String modifier = Modifier.toString(clazz.getModifiers());
            // 修饰符
            System.out.print(modifier + " " + name);
            // 父类
            if (superclass != null && superclass != Object.class) {
                System.out.print(" extends " + superclass.getName() + " {");
            }
            System.out.println();
            // 构造函数
            printConstructors(clazz);
            System.out.println();
            // 方法
            printMethods(clazz);
            System.out.println();
            // 成员
            printFields(clazz);
            System.out.println("}");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void printFields(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            String name = field.getName();
            // 类型
            Class<?> type = field.getType();
            String modifier = Modifier.toString(field.getModifiers());
            System.out.println("\t" + modifier + " " + type.getName() + " " + name + ";");
        }
    }

    private static void printMethods(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            // 返回值
            Class<?> returnType = method.getReturnType();
            // 修饰符
            String modifier = Modifier.toString(method.getModifiers());
            String name = method.getName();

            System.out.print("\t" + modifier + " " + returnType + " " + name + "(");

            // 参数
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
                if (i > 0) {
                    System.out.print(", ");
                }
                System.out.print(parameterTypes[i].getName());
            }
            System.out.println(");");
        }
    }

    private static void printConstructors(Class<?> clazz) {
        // 声明的构造函数，不包含父类的
        Constructor<?>[] constructors = clazz.getConstructors();
        for (Constructor<?> constructor : constructors) {
            String name = constructor.getName();
            String modifier = Modifier.toString(constructor.getModifiers());
            System.out.print("\t" + modifier + " " + name + "(");

            // 参数
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
                if (i > 0) {
                    System.out.print(", ");
                }
                System.out.print(parameterTypes[i].getName());
            }
            System.out.println(");");
        }
    }
}
