package cn.ykf;

import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-10-13
 */
public class URLTest {

    @Test
    public void testUrl() throws IOException {
        final URL url = new URL("http://www.baidu.com");
        final Scanner in = new Scanner(url.openStream(), "UTF-8");

        while (in.hasNextLine()) {
            System.out.println(in.nextLine());
        }
    }

    @Test
    public void testUri() throws URISyntaxException {
        final URI uri = new URI("file:///D:/document/IdeaProjects/core-java-volume/core-java-volume-chapter-18-network/src/test/java/cn/ykf/URLTest.java");
        System.out.println(uri.getScheme());
        System.out.println(uri.getHost());
        System.out.println(uri.getPath());
    }

    @Test
    public void testUrlConnection() throws IOException {
        final URL url = new URL("http://www.baidu.com");
        final URLConnection connection = url.openConnection();
        // 连接
        connection.connect();
        final Scanner in = new Scanner(connection.getInputStream(), "UTF-8");

        // 头部信息
        connection.getHeaderFields().forEach((key, list) -> System.out.println(key + ": " + list));

        while (in.hasNextLine()) {
            System.out.println(in.nextLine());
        }
    }
}
