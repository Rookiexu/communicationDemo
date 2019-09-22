package cn.rookiex.netty;

import cn.rookiex.common.Message;
import cn.rookiex.common.api.Connect;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import java.nio.ByteBuffer;

/**
 * @Author : Rookiex
 * @Date : Created in 2019/9/22 15:23
 * @Describe :
 * @version: 1.0
 */
public class NettyConnect implements Connect {

    private final Channel channel;

    public NettyConnect(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void writeData(Message message) {
        byte[] byteBuffer = message.bytesWrite();
        ByteBuf byteBuf = Unpooled.buffer(byteBuffer.length, 1024 * 512).writeBytes(byteBuffer);
        channel.writeAndFlush(byteBuf.retain());
        byteBuf.release();
    }
}
