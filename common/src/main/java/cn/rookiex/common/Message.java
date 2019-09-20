package cn.rookiex.common;

import java.nio.ByteBuffer;

/**
 * @Author : Rookiex
 * @Date : Created in 2019/9/19 17:06
 * @Describe :
 * @version: 1.0
 */
public class Message {

    public static final String L_SEND_1 = "老张,最近好吗";
    public static final String Z_RESULT_1 = "好呀";
    public static final String Z_SEND_2 = "下个棋呗?";
    public static final String L_RESULT_2 = "不下棋了,回家做饭";
    public static final String Z_SEND_3 = "天气冷了注意身体";
    public static final String L_RESULT_3 = "好的,你也要注意身体";
    public static final String L_SEND_4 = "改天来拜访,再见";
    public static final String Z_RESULT_4 = "再见";

    public Message(int id, String message) {
        this.id = id;
        this.context = message;
    }

    /**
     * 消息id
     */
    private int id;

    /**
     * 消息内容
     */
    private String context;

    public Message() {
    }

    public static String getResultMsgByAccept(String acceptMsg) {
        switch (acceptMsg) {
            case L_SEND_1:
                return Z_RESULT_1;
            case Z_SEND_2:
                return L_RESULT_2;
            case Z_SEND_3:
                return L_RESULT_3;
            case L_SEND_4:
                return Z_RESULT_4;
            default:
                return null;
        }
    }

    public static String getLiDaYeMsgById(int id) {
        if (id % 2 == 0) {
            return L_SEND_1;
        }
        return L_SEND_4;
    }

    public static String getZhangDaYeMsgById(int id) {
        if (id % 2 == 0) {
            return Z_SEND_2;
        }
        return Z_SEND_3;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void bytesRead(ByteBuffer data) {
        id = data.getInt();
        int length = data.getInt();
        byte[] info = new byte[length];
        data.get(info);
        context = new String(info);
    }

    public ByteBuffer bytesWrite() {
        byte[] bytes = context.getBytes();
        int length = bytes.length;
        ByteBuffer data = ByteBuffer.allocate(12 + length);
        data.putInt(8 + length);
        data.putInt(id);
        data.putInt(length);
        data.put(bytes);
        return data;
    }
}
