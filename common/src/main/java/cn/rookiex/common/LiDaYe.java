package cn.rookiex.common;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author : Rookiex
 * @Date : Created in 2019/9/19 17:32
 * @Describe :
 * @version: 1.0
 */
public class LiDaYe {

    private static Map<Integer,Integer> map = new HashMap<>();
    private AtomicInteger sendNum = new AtomicInteger();

    /**
     * 收到消息,然后发送新的消息
     * @param msg 收到的消息
     */
    public Message acceptAndReturnMsg(Message msg) {
        int id = msg.getId();
        map.remove(id);

        int i = sendNum.incrementAndGet();
        String context = msg.getContext();
        return sendMessage(context, i);
    }

    /**
     * 收到消息,保存到记录map,并且返回消息
     * @param acceptMsg 收到的消息
     * @param id 需要发送的消息id
     * @return 回送的消息
     */
    public Message sendMessage(String acceptMsg, int id) {
        String resultMsg = Message.getResultMsgByAccept(acceptMsg);
        Message message = new Message(id, resultMsg);
        map.put(id,id);
        return message;
    }
}
