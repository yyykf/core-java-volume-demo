package cn.ykf;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-10-15
 */
public class SocketChannelTest {

    /**
     * 没有使用选择器的服务端，阻塞
     */
    @Test
    public void serverWithBlocking() {
        try (final ServerSocketChannel server = ServerSocketChannel.open();
             final FileChannel out = FileChannel.open(Paths.get("server.txt"), StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            // 绑定监听端口
            server.bind(new InetSocketAddress(9999));
            // 接收请求
            final SocketChannel socket = server.accept();

            final ByteBuffer buffer = ByteBuffer.allocate(1024);
            // 循环读客户端的数据到缓冲区
            // 因为 read() 是阻塞的，如果客户端没有关闭输出流来通知服务端传输结束，那么服务端就会一直阻塞到这里
            while (socket.read(buffer) != -1) {
                // 把缓冲区的数据写到本地
                buffer.flip();
                out.write(buffer);
                buffer.clear();
            }

            // 向客户端发送响应数据
            socket.write(ByteBuffer.wrap("服务器接收数据成功".getBytes()));

            // 关闭socket
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void clientWithBlocking() {
        try (final SocketChannel socket = SocketChannel.open(new InetSocketAddress("localhost", 9999));
             final FileChannel in = FileChannel.open(Paths.get("nio.txt"), StandardOpenOption.READ)) {

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            // 把本地文件读取到缓冲区中
            while (in.read(buffer) != -1) {
                // 把缓冲区数据发送给服务端
                buffer.flip();
                socket.write(buffer);
                buffer.clear();
            }

            // 关闭输出流
            socket.shutdownOutput();

            // 重新分配缓冲区，避免本地文件的影响
            buffer = ByteBuffer.allocate(1024);
            // 读取服务器响应
            int len;
            while ((len = socket.read(buffer)) != -1) {
                buffer.flip();
                System.out.println(new String(buffer.array(), 0, len));
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 非阻塞
     */
    @Test
    public void serverWithNonBlocking() {
        try (final ServerSocketChannel serverSocket = ServerSocketChannel.open()) {
            // 监听端口
            serverSocket.bind(new InetSocketAddress(9999));
            // 切换非阻塞模式
            serverSocket.configureBlocking(false);

            // 注册到选择器
            final Selector selector = Selector.open();
            // 监听接受事件
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);

            // 轮询获取选择器上已经准备就绪的事件
            // select 依旧是阻塞的
            while (selector.select() > 0) {
                // 已经准备就绪的事件
                final Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

                while (iterator.hasNext()) {
                    // 判断分别是什么事件就绪
                    final SelectionKey key = iterator.next();

                    if (key.isAcceptable()) {
                        // 接受就绪
                        final SocketChannel socket = serverSocket.accept();
                        // 获取客户端连接并且配置为非阻塞
                        socket.configureBlocking(false);
                        // 也注册到选择器上，这里选择监听读事件
                        socket.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        // 读就绪
                        final SocketChannel socket = (SocketChannel) key.channel();
                        // 开始读数据
                        final ByteBuffer buffer = ByteBuffer.allocate(1024);

                        int len;
                        while ((len = socket.read(buffer)) > 0) {
                            System.out.println(len);
                            buffer.flip();
                            System.out.println(new String(buffer.array(), 0, len));
                            buffer.clear();
                        }
                    }

                    // 已就绪事件处理完，需要移除
                    iterator.remove();

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void clientWithNonBlocking() {
        try (final SocketChannel socket = SocketChannel.open(new InetSocketAddress("localhost", 9999));
             final Scanner in = new Scanner(System.in)) {
            // 配置非阻塞模式
            socket.configureBlocking(false);

            final ByteBuffer buffer = ByteBuffer.allocate(1024);
            // 给服务器发送数据
            while (in.hasNextLine()) {
                buffer.put((LocalDateTime.now().toString() + "\n" + in.nextLine()).getBytes());
                buffer.flip();
                socket.write(buffer);
                buffer.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
