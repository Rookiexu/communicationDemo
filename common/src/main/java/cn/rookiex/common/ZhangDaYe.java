package cn.rookiex.common;


import cn.rookiex.common.api.Connect;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author : Rookiex
 * @Date : Created in 2019/9/19 17:13
 * @Describe :
 * @version: 1.0
 */
public class ZhangDaYe implements DaYe, Runnable {

    private static Map<Integer, Integer> map = new HashMap<>();

    @Override
    public int getLastIdCount() {
        return map.size();
    }

    private AtomicInteger returnCount = new AtomicInteger();
    private AtomicInteger returnCount2 = new AtomicInteger();
    private AtomicInteger returnCount3 = new AtomicInteger();

    /**
     * 根据收到的消息返回消息
     *
     * @param tcpConnect
     * @param msg        收到的消息
     * @return 回复的消息
     */
    @Override
    public void acceptAndReturnMsg(Connect tcpConnect, Message msg) {
        int id = msg.getId();
        String context = msg.getContext();
        String resultMsg = Message.getResultMsgByAccept(context);
        if (resultMsg != null) {
            Message message = new Message(id, resultMsg);
//            System.out.println("张大爷收到,李大爷第" + id + "次说, ==>" + context + ". 回复了==> " + resultMsg);
            tcpConnect.writeData(message);
            returnCount2.incrementAndGet();
        } else {
            map.remove(id);
            returnCount3.incrementAndGet();
//            System.out.println("张大爷收到,第" + id + "次的消息的回复, ==>" + context);
        }

        if (returnCount.incrementAndGet() == 2 * HuTong.huTong.getMissTimes()) {
            HuTong.huTong.getOverCountDownLatch().countDown();
        }
    }

    /**
     * @param acceptMsg 收到的消息内容
     * @param id        收到的消息id
     */
    @Override
    public void sendMessage(Connect tcpConnect, int id) {
        String zhangDaYeMsgById = Message.getZhangDaYeMsgById(id);
        Message message = new Message(id, zhangDaYeMsgById);
        if (id % 10000 == 0)
            System.out.println("张大爷第" + id + "次说, ==>" + zhangDaYeMsgById);
        tcpConnect.writeData(message);
        map.put(id, id);
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        try {
            HuTong.huTong.getStartCountDownLatch().await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        HuTong.huTong.setStartTime(System.currentTimeMillis());
        for (int i = 0; i < HuTong.huTong.getMissTimes(); i++) {
            sendMessage(HuTong.huTong.getConnect(), i);
        }

        HuTong.huTong.getOverCountDownLatch().countDown();

        while (HuTong.huTong.getOverCountDownLatch().getCount() > 0) {
            System.out.println("now count == " + returnCount.get() + " map count = " + map.size() + "count2 == " + returnCount2.get() + " count3 = " + returnCount3.get());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void start() {
        Thread t = new Thread(this);
        t.start();
    }

    public clase 张大爷{
        public void 接收消息并且回复(Msg msg){
            //接收李大爷的消息 并且返回对应的回应
        }

        public void 主动打招呼(int id){
            //主动向李大爷打招呼
            //具体的打招呼内容根据id获得
        }
    }

    public clase 李大爷{
        public void 接收消息并且回复(Msg msg){
            //接收张大爷的消息 并且返回对应的回应
        }

        public void 主动打招呼(int id){
            //主动向张大爷打招呼
            //具体的打招呼内容根据id获得
        }
    }

    public clase 胡同{
        //保存大爷的数据
        //会面的次数
        //保存连接
    }

    public clase 消息{
        //id 作为大爷一应一答是否完成的判断依据
        //context 消息的内容
        //自定义的序列化
    }

    public clase Server{
        //id 作为大爷一应一答是否完成的判断依据
        //context 消息的内容
        //序列化方法
    }
}
