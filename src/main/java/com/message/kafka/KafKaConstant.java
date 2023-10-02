package com.message.kafka;

public class KafKaConstant {
    public static final String BROKER_SERVER_LOCAL = "localhost:9092"; //docker 에서 실패
    public static final String BROKER_SERVER_DOCKER = "kafka:9092"; //docker 에서 동작
    public static final String BROKER_SERVER_DOCKER2 = "host.docker.internal:9092"; //docker 에서 동작
    public static final String MSG_TOPIC = "MESSAGE_TOPIC";

    public static final String BROKER_SERVER = BROKER_SERVER_DOCKER2;
}
