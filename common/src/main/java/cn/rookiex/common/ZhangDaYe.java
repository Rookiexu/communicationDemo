package cn.rookiex.common;

import cn.rookiex.common.Message;

/**
 * @Author : Rookiex
 * @Date : Created in 2019/9/19 17:13
 * @Describe :
 * @version: 1.0
 */
public class ZhangDaYe {

    /**
     * 根据收到的消息返回消息
     * @param msg 收到的消息
     * @return 回复的消息
     */
    public Message acceptAndReturnMsg(Message msg) {
        int id = msg.getId();
        String context = msg.getContext();
        return sendMessage(context,id);
    }

    /**
     * @param acceptMsg 收到的消息内容
     * @param id 收到的消息id
     * @return 返回的消息内容
     */
    public Message sendMessage(String acceptMsg, int id) {
        String resultMsg = Message.getResultMsgByAccept(acceptMsg);
        return new Message(id, resultMsg);
    }
}
