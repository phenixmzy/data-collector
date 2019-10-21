package com.phenix.bigdata.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.phenix.bigdata.model.GamePlay;
import com.phenix.bigdata.model.factory.GamePlayFactory;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class GamePlayRunnable implements Runnable {
    private int times;
    private String bootStrapServer;
    private String topic;
    private Producer<String, String> procuder = null;
    private int gamePlayMaxDelay;
    private int gameIdMaxNum;
    private int userIdMaxNum;


    public GamePlayRunnable(int times, String bootStrapServer, String topic, int gameIdMaxNum, int userIdMaxNum, int gamePlayMaxDelay) {
        this.times = times;
        this.bootStrapServer = bootStrapServer;
        this.topic = topic;
        this.procuder = new KafkaProducer<String, String>(getkafkaConf(this.bootStrapServer));
        this.gameIdMaxNum = gameIdMaxNum;
        this.userIdMaxNum = userIdMaxNum;
        this.gamePlayMaxDelay = gamePlayMaxDelay;
    }

    @Override
    public void run() {
        for (int i = 0; i < times; i++) {
            GamePlay gamePlay = GamePlayFactory.build(this.gameIdMaxNum, this.userIdMaxNum, this.gamePlayMaxDelay);
            String gamePlayJsonStr = JSON.toJSONString(gamePlay, SerializerFeature.WriteMapNullValue);
            sendToKafka(topic, gamePlayJsonStr);
        }
    }



    public Properties getkafkaConf(String bootStrapServer) {
        Properties properties = new Properties();
        // config producer
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, "200000");
        properties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, "60000");
        properties.put(ProducerConfig.LINGER_MS_CONFIG, "100");
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
        //properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "lz4");

        properties.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, "524288000");
        properties.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, "134217728");
        properties.put(ProducerConfig.SEND_BUFFER_CONFIG, "134217728");
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, "134217728");

        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        return properties;
    }

    private void sendToKafka(String topic, String json) {
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, json);
        procuder.send(record);
    }


}