package by.test;

import com.by.model.Card;
import com.by.model.ParkingCoupon;
import com.by.typeEnum.ValidEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Calendar;
import java.util.function.Function;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by zodiake on 2015/11/30.
 */

public class ReflectionTest {
    @Test
    public <T> void reflection(Function<T, Void> notPermanent, Function<T, Void> permanent, Function<T, Void> notValid, T t) {
        Class c = t.getClass();
        try {
            Field f = c.getDeclaredField("valid");
            if (!Modifier.isPublic(f.getModifiers())) {
                f.setAccessible(true);
            }
            ValidEnum validEnum = (ValidEnum) f.get(t);
            Field beginField = c.getDeclaredField("beginTime");
            Field endField = c.getDeclaredField("endTime");
            Calendar beginTime = (Calendar) beginField.get(t);
            Calendar endTime = (Calendar) endField.get(t);
            assertEquals(validEnum, ValidEnum.VALID);
            if (validEnum.equals(ValidEnum.VALID)) {
                if (beginTime != null && endTime != null) {
                    //this is temp
                    notPermanent.apply(t);
                } else {
                    //this is permanent rule
                    permanent.apply(t);
                }
            } else {
                notValid.apply(t);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
