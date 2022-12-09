package com.design.onlineofflineindicator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.export.datadog.DatadogMetricsExportAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

@SpringBootApplication(exclude = {DatadogMetricsExportAutoConfiguration.class, DataSourceAutoConfiguration.class})
public class OnlineOfflineIndicatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineOfflineIndicatorApplication.class, args);
    }

}
