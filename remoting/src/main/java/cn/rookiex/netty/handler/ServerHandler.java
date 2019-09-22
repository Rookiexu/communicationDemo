package cn.rookiex.netty.handler;

import cn.rookiex.common.HuTong;
import cn.rookiex.common.Message;
import cn.rookiex.common.api.Connect;
import cn.rookiex.netty.NettyConnect;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;

import java.io.UnsupportedEncodingException;

/**
 * @Author : Rookiex
 * @Date : Created in 2019/9/22 14:42
 * @Describe :
 * @version: 1.0
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    public static final AttributeKey<NettyConnect> NETTY_CONNECT = AttributeKey.valueOf("connect");
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        NettyConnect connection = new NettyConnect(ctx.channel());
        ctx.channel().attr(NETTY_CONNECT).set(connection);
        HuTong.huTong.setConnect(connection);
        super.channelActive(ctx);
        System.out.println("胡同ready");
        HuTong.huTong.getDaye().start();
    }

    /**
     * 收到客户端消息
     * @throws UnsupportedEncodingException
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException {
        byte[] buf = (byte[]) msg;
        Message message = new Message();
        message.bytesRead(buf);
        Connect connect = ctx.channel().attr(NETTY_CONNECT).get();
        HuTong.huTong.getDaye().acceptAndReturnMsg(connect,message);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /**
     * 异常处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
