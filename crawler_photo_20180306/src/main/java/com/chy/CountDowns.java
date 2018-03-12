package com.chy;

/**
 * @author chenhaoyu
 * @Created 2018-03-06 15:31
 */
public class CountDowns {

    public int counts = 0;

    public CountDowns(int counts) {
        this.counts = counts;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }
    synchronized void decreaseCounts() {
        this.counts--;
    }


}
