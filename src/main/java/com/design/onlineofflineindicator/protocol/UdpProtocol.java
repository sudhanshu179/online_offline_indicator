package com.design.onlineofflineindicator.protocol;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

@Configuration
public class UdpProtocol {

    @Bean
    public DatagramSocket startUdpServer() throws IOException {
        String[] args = {"12345", "PONG"};
        DatagramSocket socket = new DatagramSocket(Integer.parseInt(args[0]));

        // Create a buffer to receive incoming data
        while(true) {
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            // Wait for an incoming packet
            socket.receive(packet);

            // Extract the data from the packet
            String data = new String(packet.getData(), 0, packet.getLength());
            if (data.equals("stop")){
                sendResponseviaSocket(args, socket, packet);
                break;
            }
            // Print the data
            System.out.println("Received: " + data);

            sendResponseviaSocket(args, socket, packet);
        }
        socket.close();
        return socket;
    }

    private void sendResponseviaSocket(String[] args, DatagramSocket socket, DatagramPacket packet) throws IOException {
        // Convert the reply to bytes
        byte[] reply = args[1].getBytes();

        // Create a packet with the reply data, the sender's address and port, and send it
        packet = new DatagramPacket(
                reply, reply.length, packet.getAddress(), packet.getPort()
        );
        socket.send(packet);
    }

    //    @Bean
    public void startUdpClient() throws IOException {
        String[] args = {"localhost", "12345", "PONG"};
        // Create a UDP socket
        DatagramSocket socket = new DatagramSocket();

        // Convert the arguments to bytes
        byte[] data = args[0].getBytes();
        InetAddress address = InetAddress.getByName(args[0]);
        int port = Integer.parseInt(args[1]);

        // Create a UDP packet with the data, destination address, and port
        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);

        // Send the packet
        socket.send(packet);

        // Receive a reply
        byte[] reply = new byte[1024];
        packet = new DatagramPacket(reply, reply.length);
        socket.receive(packet);

        // Print the reply
        String received = new String(packet.getData(), 0, packet.getLength());
        System.out.println("Received: " + received);
    }
}
