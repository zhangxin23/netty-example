package com.sandbox.netty.localecho;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.local.LocalAddress;
import io.netty.channel.local.LocalChannel;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Author: zhangxin
 * Date:   15-9-28
 */
public class LocalEcho {
    static final String PORT = System.getProperty("port", "test_port");

    public static void main(String[] args) throws Exception {
        final LocalAddress addr = new LocalAddress(PORT);

        EventLoopGroup serverGroup = new DefaultEventLoopGroup();
        EventLoopGroup clientGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(serverGroup)
                    .channel(LocalServerChannel.class)
                    .handler(new ChannelInitializer<LocalServerChannel>() {
                        @Override
                        public void initChannel(LocalServerChannel ch) throws Exception {
                            ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
                        }
                    })
                    .childHandler(new ChannelInitializer<LocalChannel>() {
                        @Override
                        public void initChannel(LocalChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new LoggingHandler(LogLevel.INFO),
                                    new LocalEchoServerHandler()
                            );
                        }
                    });

            Bootstrap b = new Bootstrap();
            b.group(clientGroup)
                    .channel(LocalChannel.class)
                    .handler(new ChannelInitializer<LocalChannel>() {
                        @Override
                        public void initChannel(LocalChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new LoggingHandler(LogLevel.INFO),
                                    new LocalEchoClientHandler()
                            );
                        }
                    });

            sb.bind(addr).sync();

            Channel ch = b.connect(addr).sync().channel();

            ChannelFuture lastWriteFuture = null;
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while(true) {
                String line = in.readLine();
                if(line == null || line.equalsIgnoreCase("quit"))
                    break;

                lastWriteFuture = ch.writeAndFlush(line);
            }

            if(lastWriteFuture != null)
                lastWriteFuture.awaitUninterruptibly();

        } finally {
            serverGroup.shutdownGracefully();
            clientGroup.shutdownGracefully();
        }
    }
}
