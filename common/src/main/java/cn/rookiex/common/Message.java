package cn.rookiex.common;

/**
 * @Author : Rookiex
 * @Date : Created in 2019/9/19 17:06
 * @Describe :
 * @version: 1.0
 */
public class Message {

    public static final String SEND_1 = "你好嘞";
    public static final String RESULT_1 = "好嘞,吃了没";
    public static final String SEND_2 = "吃了,遛个弯?";
    public static final String RESULT_2 = "不了,先回家";
    public static final String SEND_3 = "好的,再见";
    public static final String RESULT_3 = "再见";

    public Message(int id,String message){
        this.id = id;
        this.context = message;
    }

    /**
     * 消息id
     */
    private int id;

    /**
     * 消息内容
     * */
    private String context;

    public static String getResultMsgByAccept(String acceptMsg) {
        switch (acceptMsg){
            case SEND_1:
                return RESULT_1;
            case RESULT_1:
                return SEND_2;
            case SEND_2:
                return RESULT_2;
            case RESULT_2:
                return SEND_3;
            case SEND_3:
                return RESULT_3;
            case RESULT_3:
                return SEND_1;
            default:
                return null;
        }
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


}
