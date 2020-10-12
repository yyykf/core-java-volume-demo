package cn.ykf;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-10-12
 */
public class PathTest {

    @Test
    public void testPath() throws IOException {
        final Path file = Paths.get("d:", "新建文本文档.txt");
        System.out.println(file.toFile().exists());

        final Path directory = file.resolve("d:\\document");
        System.out.println(directory.getRoot());

        // directory的相对路径
        System.out.println(file.relativize(directory));

        // 兄弟路径，也就是同一父路径
        System.out.println(file.resolveSibling("software"));

    }
}
