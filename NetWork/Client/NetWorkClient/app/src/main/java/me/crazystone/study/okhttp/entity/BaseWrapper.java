package me.crazystone.study.okhttp.entity;

import java.util.List;

/**
 * Created by crazy_stone on 17-10-24.
 */

public class BaseWrapper<T> {

    private int status;
    private String message;
    private T data;
    private List<T> datas;
    private int start;
    private int count;


    public int getStatus() {
        return status;
    }

    public BaseWrapper setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public BaseWrapper setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public BaseWrapper setData(T data) {
        this.data = data;
        return this;
    }

    public List<T> getDatas() {
        return datas;
    }

    public BaseWrapper setDatas(List<T> datas) {
        this.datas = datas;
        return this;
    }

    public int getStart() {
        return start;
    }

    public BaseWrapper setStart(int start) {
        this.start = start;
        return this;
    }

    public int getCount() {
        return count;
    }

    public BaseWrapper setCount(int count) {
        this.count = count;
        return this;
    }

    @Override
    public String toString() {
        return "BaseWrapper{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", datas=" + datas +
                ", start=" + start +
                ", count=" + count +
                '}';
    }
}
