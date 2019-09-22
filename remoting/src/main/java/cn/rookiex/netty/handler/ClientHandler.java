package cn.rookiex.netty.handler;

import cn.rookiex.common.DaYe;
import cn.rookiex.common.HuTong;
import cn.rookiex.common.Message;
import cn.rookiex.common.api.Connect;
import cn.rookiex.netty.NettyConnect;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;

import java.io.UnsupportedEncodingException;

/**
 * @Author : Rookiex
 * @Date : Created in 2019/9/22 14:43
 * @Describe :
 * @version: 1.0
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    private static final AttributeKey<Connect> NETTY_CONNECT = AttributeKey.valueOf("connect");

    /**
     * tcp链路简历成功后调用
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        NettyConnect connection = new NettyConnect(ctx.channel());
        ctx.channel().attr(NETTY_CONNECT).set(connection);
        HuTong.huTong.setConnect(connection);
        System.out.println("胡同ready");
        HuTong.huTong.getDaye().start();
        super.channelActive(ctx);
    }

    /**
     * 收到服务器消息后调用
     *
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

    /**
     * 发生异常时调用
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

