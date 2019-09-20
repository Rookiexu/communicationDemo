package cn.rookiex.common.api;

import cn.rookiex.common.Message;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * @Author : Rookiex
 * @Date : Created in 2019/9/19 18:17
 * @Describe :
 * @version: 1.0
 */
public interface Connect {
    void writeData(Message message);
}
