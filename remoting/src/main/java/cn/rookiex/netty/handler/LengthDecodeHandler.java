package cn.rookiex.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.ReferenceCountUtil;

import java.util.logging.Logger;

/**
 * @Author : Rookiex
 * @Date : Created in 2019/9/22 14:46
 * @Describe :
 * @version: 1.0
 */
public class LengthDecodeHandler extends LengthFieldBasedFrameDecoder {

    public LengthDecodeHandler(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthFieldOffset, lengthFieldLength);
    }


    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in)
            throws Exception {

        in = (ByteBuf) super.decode(ctx, in);
        if (in == null) return null;

        int readableLen = in.readableBytes(); // 可读的数据大小
        //in.markReaderIndex();
        byte[] body = new byte[readableLen];  //  我们读到的长度，满足我们的要求了，把传送过来的数据取出来
        in.readBytes(body);

        // 将byte数据转化为我们需要的对象。

        try {
            // 释放内存空间
//            ReferenceCountUtil.refCnt(in);
            in.release();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return body;
    }
}