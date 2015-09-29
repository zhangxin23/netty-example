package com.sandbox.netty.telnet;

import io.netty.channel.*;

import java.net.InetAddress;
import java.util.Date;

/**
 * Author: zhangxin
 * Date:   15-9-29
 */
@ChannelHandler.Sharable
public class TelnetServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.write("Welcome to " + InetAddress.getLocalHost().getHostName() + "!\r\n");
        ctx.write("It is " + new Date() + " now.\r\n");
        ctx.flush();
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, String request) {
        String response;
        boolean close = false;
        if(request.isEmpty()) {
            response = "Please type something.\r\n";
        } else if("bye".equals(request.toLowerCase())) {
            response = "Have a good day.\r\n";
            close = true;
        } else {
            response = "Did you san '" + request + "'?\r\n";
        }

        ChannelFuture future = ctx.write(response);

        if(close)
            future.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
