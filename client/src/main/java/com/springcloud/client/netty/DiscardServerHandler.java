package com.springcloud.client.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2020/12/2 10:25
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //以静默方式丢弃接收的数据
//        ((ByteBuf) msg).release();
        ByteBuf in = (ByteBuf) msg;
        try {
//            while (in.isReadable()) {
//                System.out.println(((char) in.readByte()));
//                System.out.flush();
//            }
            System.out.println(in.toString(CharsetUtil.US_ASCII));
        }finally {
            ReferenceCountUtil.release(msg);
        }
        //通常，channelRead() 处理程序方法实现
        /*try {
            //do something with msg
        }finally {
            ReferenceCountUtil.release(msg);
        }*/
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //出现异常时关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
