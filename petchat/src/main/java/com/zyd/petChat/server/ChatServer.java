package com.zyd.petChat.server;

import com.zyd.petChat.handler.HttpRequestHandler;
import com.zyd.petChat.handler.WsChatReceiveHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @program: petfamily
 * @author: zyd
 * @description:
 * @create: 2019-05-03 13:48
 */
public class ChatServer {
    private static int PORT = 9090;

    public static void main(String[] args) throws Exception {
        int port = PORT;
        if (args != null || args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (Exception e) {
                port = PORT;
            }
        }

        new ChatServer().bind(port);
    }

    public void bind(int port) throws Exception {
// NioEventLoopGroup是个线程组，包含一组nio线程，专门用于网络事件的处理（reactor线程组）
        // 这里一个用于服务端接收客户端的联结，另外一个进行socketchannel的网络读写
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossGroup, workerGroup) //boss辅助客户端的tcp连接请求  worker负责与客户端之前的读写操作
                    .channel(NioServerSocketChannel.class) //配置客户端的channel类型
                    .option(ChannelOption.SO_BACKLOG, 1024) //配置TCP参数，握手字符串长度设置
                    .option(ChannelOption.TCP_NODELAY, false) //禁止TCP_NODELAY算法
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//开启心跳包活机制，就是客户端、服务端建立连接处于ESTABLISHED状态，超过2小时没有交流，机制会被启动
                    .childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(592048))//配置固定长度接收缓存区分配器
                    .childHandler(new ChildChannelHandler()); //绑定I/O事件的处理类,WebSocketChildChannelHandler中定义
            // 绑定端口，同步等待成功
            ChannelFuture future = bootstrap.bind(port).sync();

            // 等待服务端监听端口关闭
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
        /*
         * (non-Javadoc)
         *
         * @see
         * io.netty.channel.ChannelInitializer#initChannel(io.netty.channel.Channel)
         */
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            System.out.println(Thread.currentThread().getName() + ",服务器初始化通道...");
            ch.pipeline().addLast("http-codec", new HttpServerCodec()); // HTTP编码解码器
            ch.pipeline().addLast("aggregator", new HttpObjectAggregator(65536)); // 把HTTP头、HTTP体拼成完整的HTTP请求
            ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler()); // 方便大文件传输，不过实质上都是短的文本数据
            ch.pipeline().addLast("http-handler", new HttpRequestHandler());
            ch.pipeline().addLast("websocket-handler",new WsChatReceiveHandler());
        }
    }
}
