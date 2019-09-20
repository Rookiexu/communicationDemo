package cn.rookiex.common.api;


/**
 * @Author : Rookiex
 * @Date : Created in 2019/9/19 18:07
 * @Describe :
 * @version: 1.0
 */
public interface ServerHandler {

    /**
     * 开放端口
     * @param port 端口
     * */
     void bindPort(int port);


    /**
     * 打开指定的连接
     * @param host 主机
     * @param port 端口
     */
    Connect openConnect(String host, int port);

}
