package cn.rookiex.common;

import cn.rookiex.common.api.Connect;

/**
 * @Author : Rookiex
 * @Date : Created in 2019/9/20 13:56
 * @Describe :
 * @version: 1.0
 */
public interface DaYe {
    void acceptAndReturnMsg(Connect tcpConnect, Message msg);

    void sendMessage(Connect tcpConnect, int id);

    int getLastIdCount();

    void start();
}
