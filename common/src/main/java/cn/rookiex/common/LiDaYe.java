package cn.rookiex.common;

import cn.rookiex.common.api.Connect;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author : Rookiex
 * @Date : Created in 2019/9/19 17:32
 * @Describe :
 * @version: 1.0
 */
public class LiDaYe implements DaYe, Runnable {

    private static Map<Integer, Integer> map = new HashMap<>();
    private AtomicInteger returnCount = new AtomicInteger();
    private AtomicInteger returnCount2 = new AtomicInteger();
    private AtomicInteger returnCount3 = new AtomicInteger();

    @Override
    public int getLastIdCount() {
        return map.size();
    }

    @Override
    public void start() {
        Thread t = new Thread(this);
        t.start();
    }

    /**
     * 收到消息,然后发送新的消息
     *
     * @param tcpConnect
     * @param msg        收到的消息
     */
    @Override
    public void acceptAndReturnMsg(Connect tcpConnect, Message msg) {
        String context = msg.getContext();
        String resultMsg = Message.getResultMsgByAccept(context);
        int id = msg.getId();
        if (resultMsg != null) {
            Message message = new Message(id, resultMsg);
//            System.out.println("李大爷收到,张大爷第" + id + "次说, ==>" + context + ". 回复了==> " + resultMsg);
            tcpConnect.writeData(message);
            returnCount2.incrementAndGet();
        } else {
            map.remove(id);
            returnCount3.incrementAndGet();
//            System.out.println("李大爷收到,第" + id + "次的消息的回复, ==>" + context);
        }

        if (returnCount.incrementAndGet() == 2 * HuTong.huTong.getMissTimes()) {
            HuTong.huTong.getOverCountDownLatch().countDown();
        }
    }

    /**
     * 收到消息,保存到记录map,并且返回消息
     *
     * @param tcpConnect 收到的消息
     * @param id         需要发送的消息id
     */
    @Override
    public void sendMessage(Connect tcpConnect, int id) {
        String liDaYeMsgById = Message.getLiDaYeMsgById(id);
        Message message = new Message(id, liDaYeMsgById);
        if (id % 10000 == 0 || (id > 60000 && id % 1000 == 0) || (id > 68000 && id % 100 == 0)) {
            System.out.println("李大爷第" + id + "次说, ==>" + liDaYeMsgById);
            System.out.println("now count == " + returnCount.get() + " map count = " + map.size() + "count2 == " + returnCount2.get() + " count3 = " + returnCount3.get());
        }
        map.put(id, id);
        tcpConnect.writeData(message);
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
}
