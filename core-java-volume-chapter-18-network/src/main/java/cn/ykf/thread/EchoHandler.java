package cn.ykf.thread;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-10-13
 */
public class EchoHandler implements Runnable {

    private Socket socket;

    public EchoHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
