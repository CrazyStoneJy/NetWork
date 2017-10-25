package me.crazystone.study.okhttp;

/**
 * Created by crazy_stone on 17-10-25.
 */

public interface Method {

    int GET = 0x1;
    int POST = GET + 1;
    int DELETE = POST + 1;
    int HEAD = DELETE + 1;

}
