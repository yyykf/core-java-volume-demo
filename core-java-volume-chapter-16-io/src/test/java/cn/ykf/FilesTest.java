package cn.ykf;

import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-10-12
 */
public class FilesTest {
    @Test
    public void testFiles() throws IOException {
        // 小文件，直接读字节数组
        final Path path = Paths.get("d:", "新建文本文档.txt");
        final byte[] bytes = Files.readAllBytes(path);
        final String s = new String(bytes, StandardCharsets.UTF_8);
        System.out.println(s);

        System.out.println("-----------------");

        // 逐行读取
        Files.readAllLines(path, StandardCharsets.UTF_8).forEach(System.out::println);
    }
}
