package com.sandbox.netty.chap01;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Author: zhangxin
 * Date:   15-9-28
 */
public final class EchoClient {
    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));

    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>(){
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture f = b.connect(HOST, PORT).sync();

            String input = "";
            while(true) {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                input = br.readLine();
                if(input.equals("quit"))
                    break;
                ByteBuf sendBuf = Unpooled.buffer(SIZE);
                sendBuf.writeBytes(input.getBytes());
                f.channel().writeAndFlush(sendBuf);
            }

            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
