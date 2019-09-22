package cn.rookiex.netty.core;

import cn.rookiex.common.HuTong;
import cn.rookiex.common.LiDaYe;
import cn.rookiex.netty.handler.ClientHandler;
import cn.rookiex.netty.handler.LengthDecodeHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author : Rookiex
 * @Date : Created in 2019/9/22 15:06
 * @Describe :
 * @version: 1.0
 */
public class Client implements Runnable{

    public static void main(String[] args) throws Exception {
            LiDaYe liDaYe = new LiDaYe();
            HuTong.huTong.setDaye(liDaYe);
            new Thread(new Client()).start();
    }

    @Override
    public void run() {
        String host = "127.0.0.1";
        int port = 9090;
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) {
                    ch.pipeline()
                            .addLast(new LengthDecodeHandler(1024 * 512,0,4))
                            .addLast(new ClientHandler());
                }
            });
            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
