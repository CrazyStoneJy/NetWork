package me.crazystone.study.okhttp.entity;

/**
 * Created by crazy_stone on 17-10-24.
 */

public class TestEntity extends BaseWrapper<TestEntity> {

    private String name;

    public String getName() {
        return name;
    }

    public TestEntity setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "TestEntity{" +
                "name='" + name + '\'' +
                "} " + super.toString();
    }
}
