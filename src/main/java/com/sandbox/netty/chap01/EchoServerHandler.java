package com.sandbox.netty.chap01;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Author: zhangxin
 * Date:   15-9-28
 */
public class EchoServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        ByteBuf byteBuf = (ByteBuf)msg;
//        byte[] byte_buf = new byte[1024];
//        byteBuf.getBytes(0, byte_buf);
////        System.out.println("recv: " + new String(byte_buf).toString());
//        for(int i = 0; i < byte_buf.length; i++) {
//            System.out.printf("%X ", byte_buf[i]);
//        }
//        System.out.println();

        ByteBuf byteBuf = (ByteBuf)msg;
        for(int i = 0; i < byteBuf.readableBytes(); i++)
            System.out.printf("%X ", byteBuf.getByte(i));
        System.out.println();

        ctx.write(msg);
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
