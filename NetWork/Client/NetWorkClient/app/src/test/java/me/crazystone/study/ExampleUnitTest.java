package me.crazystone.study;

import org.junit.Test;

import me.crazystone.study.okhttp.entity.TestEntity;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        TestEntity entity = new TestEntity();
        entity.setName("jiayan");
//        entity.setData(entity);
//        entity.setDatas(Util.immutableList(entity));
        entity.setCount(10);
        entity.setStart(0);
        entity.setStatus(1);
        entity.setMessage("hello");
        System.out.println(entity.toString());


    }
}