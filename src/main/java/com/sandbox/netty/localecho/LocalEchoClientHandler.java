package com.sandbox.netty.localecho;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Author: zhangxin
 * Date:   15-9-28
 */
public class LocalEchoClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void messageReceived(ChannelHandlerContext ctx, String msg) {
        System.out.println(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
