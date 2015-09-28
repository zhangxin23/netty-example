package com.sandbox.netty.chap01;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Author: zhangxin
 * Date:   15-9-28
 */
public class EchoClientHandler extends ChannelHandlerAdapter {

    private final ByteBuf firstMessage;

    public EchoClientHandler() {
        firstMessage = Unpooled.buffer(EchoClient.SIZE);
        for(int i = 0; i < firstMessage.capacity(); i++) {
            firstMessage.writeByte((byte)i);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(firstMessage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        byte[] byte_buf = new byte[1024];
//        ((ByteBuf)msg).getBytes(0, byte_buf);
//        for(int i = 0; i < byte_buf.length; i++)
//            System.out.printf("%X ", byte_buf[i]);
//        System.out.println();
//        ctx.write(msg);

        ByteBuf byteBuf = (ByteBuf)msg;
        for(int i = 0; i < byteBuf.readableBytes(); i++) {
            System.out.printf("%X ", byteBuf.getByte(i));
        }
        System.out.println();
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
