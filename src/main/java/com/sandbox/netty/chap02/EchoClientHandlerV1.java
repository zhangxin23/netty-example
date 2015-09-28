package com.sandbox.netty.chap02;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Author: zhangxin
 * Date:   15-9-28
 */
public class EchoClientHandlerV1 extends SimpleChannelInboundHandler<Object> {

    @Override
    public void messageReceived(ChannelHandlerContext ctx, Object msg) {
        System.out.println("===========");
        System.out.println(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
