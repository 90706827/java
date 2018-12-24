package com.jangni.shiro.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName IdWorker
 * Description  根据twitter的snowflake算法生成唯一ID
 * Author Mr.Jangni
 * Date 2018/12/24 20:23
 * Version 1.0
 **/
public class IdWorker {
    private static final Logger LOG = LoggerFactory.getLogger(IdWorker.class);
    private long dataCenterId;
    private long dataCenterIdBits = 5L;
    private long lastTimestamp = -1L;
    private long sequence = 0L;
    private long sequenceBits = 12L;
    private long sequenceMask = ~(-1L << sequenceBits);
    private long workerId;
    private long workerIdBits = 5L;
    private long datacenterIdShift = sequenceBits + workerIdBits;
    private long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;
    private long workerIdShift = sequenceBits;

    public IdWorker(long workerId, long dataCenterId) {
        // sanity check for workerId
        long maxWorkerId = ~(-1L << workerIdBits);
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        long maxDatacenterId = ~(-1L << dataCenterIdBits);
        if (dataCenterId > maxDatacenterId || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
        LOG.info(String.format("worker starting. timestamp left shift %d, datacenter id bits %d, worker id bits %d, sequence bits %d, workerid %d", timestampLeftShift, dataCenterIdBits, workerIdBits, sequenceBits, workerId));
    }

    public synchronized long nextId() {
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            LOG.error(String.format("clock is moving backwards.  Rejecting requests until %d.", lastTimestamp));
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        long twepoch = 1288834974657L;
        return ((timestamp - twepoch) << timestampLeftShift) | (dataCenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    public static void main(String[] args) {
        System.out.println( new IdWorker(15,31).nextId());
    }
}