package cn.rookiex.nio.handler;

import cn.rookiex.common.Message;
import cn.rookiex.common.api.ClientHandler;
import cn.rookiex.common.api.Connect;

import cn.rookiex.nio.connect.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author : Rookiex
 * @Date : Created in 2019/9/19 20:01
 * @Describe :
 * @version: 1.0
 */
public class NioClientHandlerImpl implements ClientHandler,Runnable {
    private String host;
    private int port;
    private Selector selector;
    private NioConnect nioConnect;
    private volatile boolean started;

    public NioClientHandlerImpl(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void stop() {
        started = false;
    }

    public Connect getNioConnect() {
        return nioConnect;
    }

    @Override
    public void openConnect() {
        try {
            //创建选择器
            selector = Selector.open();
            //打开监听通道
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
            //如果为 true，则此通道将被置于阻塞模式；如果为 false，则此通道将被置于非阻塞模式
            socketChannel.configureBlocking(false);//开启非阻塞模式
            socketChannel.register(selector, SelectionKey.OP_READ);
            nioConnect = new NioConnect();
            nioConnect.setSocketChannel(socketChannel);
            started = true;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void run() {
        //循环遍历selector
        while (started) {
            try {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();
                SelectionKey key;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();
                    try {
                        if (key.isValid()) {
                            //读消息
                            if (key.isReadable()) {
                                nioConnect.receive();
                            }
                        }
                    } catch (Exception e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        //selector关闭后会自动释放里面管理的资源
        if (selector != null) {
            try {
                selector.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
