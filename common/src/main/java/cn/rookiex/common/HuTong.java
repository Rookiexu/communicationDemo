package cn.rookiex.common;

import cn.rookiex.common.api.Connect;

import java.util.concurrent.CountDownLatch;

/**
 * @Author : Rookiex
 * @Date : Created in 2019/9/20 15:13
 * @Describe :
 * @version: 1.0
 */
public class HuTong {
    public static HuTong huTong = new HuTong();

    private Connect connect;
    private DaYe daye;
    private int missTimes = 1000000;

    private long startTime;
    private long endTime;

    private CountDownLatch startCountDownLatch = new CountDownLatch(1);
    private CountDownLatch overCountDownLatch = new CountDownLatch(2);


    private HuTong() {
    }

    public DaYe getDaye() {
        return daye;
    }

    public void setDaye(DaYe daye) {
        this.daye = daye;
    }

    public Connect getConnect() {
        return connect;
    }

    public void setConnect(Connect connect) {
        this.connect = connect;
    }

    public int getMissTimes() {
        return missTimes;
    }

    public void setMissTimes(int missTimes) {
        this.missTimes = missTimes;
    }

    public CountDownLatch getStartCountDownLatch() {
        return startCountDownLatch;
    }

    public void setStartCountDownLatch(CountDownLatch startCountDownLatch) {
        this.startCountDownLatch = startCountDownLatch;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public CountDownLatch getOverCountDownLatch() {
        return overCountDownLatch;
    }

    public void setOverCountDownLatch(CountDownLatch overCountDownLatch) {
        this.overCountDownLatch = overCountDownLatch;
    }
}
