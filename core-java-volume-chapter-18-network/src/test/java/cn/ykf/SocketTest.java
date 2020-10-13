package cn.ykf;

import cn.ykf.thread.EchoHandler;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-10-13
 */
public class SocketTest {

    /**
     * 无连接超时、读写超时的限制
     */
    @Test
    public void testSocket() {
        try (final Socket socket = new Socket("time-a.nist.gov", 13);
             final Scanner scanner = new Scanner(socket.getInputStream())) {

            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 带连接超时、读写超时限制
     *
     * @throws IOException
     */
    @Test
    public void testSocketWithTimeout() throws IOException {
        final Socket socket = new Socket();
        // 读写超时时间，直接调用InputStream的read会抛 SocketTimeoutException，使用Scanner包装后不抛，why？
        socket.setSoTimeout(50);
        // 连接，带连接超时时间，超时抛 SocketTimeoutException
        socket.connect(new InetSocketAddress("time-a.nist.gov", 13), 1000);

        final InputStream is = socket.getInputStream();
        // System.out.println(is.read());
        final Scanner scanner = new Scanner(is);
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }

        scanner.close();
        socket.close();
    }

    @Test
    public void testInetAddress() throws UnknownHostException {
        // 本地回环地址127.0.0.1
        System.out.println(InetAddress.getLoopbackAddress());
        // 获取本机地址
        System.out.println(InetAddress.getLocalHost());

        // 获取所有IP地址
        for (InetAddress address : InetAddress.getAllByName("www.baidu.com")) {
            System.out.println(address);
            // IP地址的十进制表示，如 14.215.177.39
            System.out.println(address.getHostAddress());
        }
    }

    /**
     * 只允许单个客户端连接
     *
     * @throws IOException
     */
    @Test
    public void testServerSocket() throws IOException {
        // 监听8888端口
        try (final ServerSocket serverSocket = new ServerSocket(8888)) {

            // 等待客户端连接
            try (final Socket socket = serverSocket.accept();) {

                // 获取输入输出流，输出流自动刷新
                try (final Scanner in = new Scanner(socket.getInputStream(), "UTF-8");
                     final PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true)) {
                    // 向客户端进行响应
                    out.println("Hello! Enter Q to quit");

                    // 接收客户端数据
                    while (in.hasNextLine()) {
                        final String line = in.nextLine();
                        if ("Q".equals(line.trim())) {
                            break;
                        }
                        out.println("Echo: " + line);
                    }
                }
            }
        }
    }

    /**
     * 允许多客户端连接
     */
    @Test
    public void testServerSocketWithThread() throws IOException {
        try (final ServerSocket serverSocket = new ServerSocket(8888)) {
            // 计数器
            int count = 0;

            while (true) {
                final Socket socket = serverSocket.accept();
                System.out.println("第" + ++count + "个请求进入...");

                // 开启线程处理
                new Thread(new EchoHandler(socket)).start();
            }
        }
    }
}
