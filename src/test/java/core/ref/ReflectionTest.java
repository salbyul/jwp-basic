package core.ref;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.model.Question;
import next.model.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            logger.debug("field: {}", field.getName());
        }

        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            logger.debug("constructor: {}", constructor.getName());
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            for (Class<?> parameterType : parameterTypes) {
                logger.debug("parameter: {}", parameterType.getName());
            }
        }

        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            logger.debug("method: {}", method.getName());
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (Class<?> parameterType : parameterTypes) {
                logger.debug("parameter: {}", parameterType.getName());
            }
        }
    }
    
    @Test
    public void newInstanceWithConstructorArgs() {
        Class<User> clazz = User.class;
        try {
            Constructor<User> declaredConstructor = clazz.getDeclaredConstructor(String.class, String.class, String.class, String.class);
            User user = declaredConstructor.newInstance("첫번 째 인자", "두번 째 인자", "세번 째 인자", "네번 째 인자");
            logger.debug("user: {}", user);
        } catch (Exception e) {
            Assert.fail();
        }
    }
    
    @Test
    public void privateFieldAccess() {
        Class<Student> clazz = Student.class;
        try {
            Constructor<Student> constructor = clazz.getConstructor();
            Field[] declaredFields = clazz.getDeclaredFields();
            Student student = constructor.newInstance();
            for (Field declaredField : declaredFields) {
                logger.debug("field: [{}] [{}]", declaredField.getName(), declaredField.getType());
                declaredField.setAccessible(true);
                if (declaredField.getName().equals("name")) {
                    declaredField.set(student, "이름입니다.");
                }
                if (declaredField.getName().equals("age")) {
                    declaredField.set(student, 25);
                }
            }
            logger.debug("student: [name: {}] [age: {}]", student.getName(), student.getAge());
        } catch (Exception e) {
            Assert.fail();
        }
    }
}
