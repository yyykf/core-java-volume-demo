package cn.ykf;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-10-14
 */
public class NIOTest {

    private final int DEFAULT_BUFFER_SIZE = 1024;

    /**
     * 核心方法，核心属性
     */
    @Test
    public void testBuffer() {
        final ByteBuffer buffer = ByteBuffer.allocate(1024);
        System.out.println("-------------allocate()----------------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        // 写数据
        buffer.put("abcde".getBytes());
        System.out.println("-------------put()----------------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        // 切换到读模式
        buffer.flip();
        System.out.println("-------------flip()----------------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        // 读数据
        final byte[] dst = new byte[buffer.limit()];
        buffer.get(dst);
        System.out.println("-------------get()----------------");
        System.out.println(new String(dst));
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        // 重读
        buffer.rewind();
        System.out.println("-------------rewind()----------------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        // 清空
        buffer.clear();
        System.out.println("-------------clear()----------------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
        // 数据还在
        System.out.println((char) buffer.get());
    }

    /**
     * 简单的例子，将原本的文件字节流和随机访问流转成channel进行读写操作
     */
    @Test
    public void testFileChannel() {
        try (final FileChannel channel = new FileOutputStream("nio.txt").getChannel()) {
            // 写一点数据
            channel.write(ByteBuffer.wrap("先写一些测试数据\n".getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 追加内容
        try (final FileChannel channel = new RandomAccessFile("nio.txt", "rw").getChannel()) {
            // 定位到文件末尾
            channel.position(channel.size());
            channel.write(ByteBuffer.wrap("这一行是追加的内容".getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 开始读取
        try (final FileChannel channel = new FileInputStream("nio.txt").getChannel()) {
            // 分配缓冲区大小为1kb
            final ByteBuffer buffer = ByteBuffer.allocate(DEFAULT_BUFFER_SIZE);
            // 将channel中的数据
            channel.read(buffer);
            // 将缓冲区的指针重置到头部，方便对buffer进行读（channel刚对缓冲区写完，指针在最后）
            // 一般读缓冲区前调用, 设置缓冲区的极限位置(最多能读到哪个位置), 并重置读写指针到头部
            buffer.flip();

            // 读缓冲区
            while (buffer.hasRemaining()) {
                System.out.write(buffer.get());
            }
            System.out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关于flip(), 参考链接: https://www.cnblogs.com/woshijpf/articles/3723364.html
     * <p>
     * 在每次 read() 将数据放入缓冲区之后，flip() 都会准备好缓冲区，以便 write() 提取它的信息。
     * 在 write() 之后，数据仍然在缓冲区中，我们需要 clear() 来重置所有内部指针，以便在下一次 read() 中接受数据。
     */
    @Test
    public void testFlipAndClear() {
        try (final FileChannel in = new FileInputStream("nio.txt").getChannel();
             final FileChannel out = new FileOutputStream("dest.txt").getChannel()) {
            // 通过ByteBuffer, 把nio.txt的数据拷贝到dest.txt
            final ByteBuffer buffer = ByteBuffer.allocate(DEFAULT_BUFFER_SIZE);

            // 开始拷贝数据
            while (in.read(buffer) != -1) {
                // 一定要有, 因为 in.read 是把数据读到缓冲区, 对于缓冲区来说, 是写入, 所以现在的读写指针是在缓冲区末尾, 需要重置为缓冲区首部
                buffer.flip();
                // out.write 是从缓冲区中读数据, 并写入到 channel 中
                out.write(buffer);
                // 清空缓冲区, 方便下次循环使用, 一般在写缓冲区前调用
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过内存映射文件（直接缓冲区）复制文件
     */
    @Test
    public void testMappedByteBuffer() {
        try (final FileChannel in = FileChannel.open(Paths.get("nio.txt"), StandardOpenOption.READ);
             final FileChannel out = FileChannel.open(Paths.get("new_dest.txt"), StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE)) {

            // 内存映射文件
            final MappedByteBuffer inBuffer = in.map(FileChannel.MapMode.READ_ONLY, 0, in.size());
            final MappedByteBuffer outBuffer = out.map(FileChannel.MapMode.READ_WRITE, 0, in.size());

            // 直接操作缓冲区即可，因为是直接缓冲区，处于物理内存上
            final byte[] dst = new byte[inBuffer.limit()];
            // 将数组读到byte数组中
            inBuffer.get(dst);
            // 把byte数组的数据写入缓冲区
            outBuffer.put(dst);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 分散读取和聚集写入
     */
    @Test
    public void testScatterAndGather() {
        try (final FileChannel in = new FileInputStream("nio.txt").getChannel();
             final FileChannel out = new FileOutputStream("new_dest.txt", true).getChannel()) {
            // 分配多个缓冲区
            ByteBuffer[] buffers = {ByteBuffer.allocate(9), ByteBuffer.allocate(1024)};
            // 分散读取
            in.read(buffers);
            // 处理缓冲区数据
            for (ByteBuffer buffer : buffers) {
                buffer.flip();
            }
            System.out.println(new String(buffers[0].array(), 0, buffers[0].limit()));
            System.out.println("-----------");
            System.out.println(new String(buffers[1].array(), 0, buffers[1].limit()));

            // 聚集写入
            out.write(buffers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更好的方式是使用 transferTo() 或者 transferFrom()
     * 也是通过直接缓冲区
     */
    @Test
    public void testTransfer() {
        try (final FileChannel in = new FileInputStream("nio.txt").getChannel();
             final FileChannel out = new FileOutputStream("dest.txt").getChannel()) {
            // in.transferTo(0, in.size(), out);

            // 或者
            out.transferFrom(in, 0, in.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用 CharBuffer 通过字符串形式展示缓冲区内容
     */
    @Test
    public void testCharBuffer() throws IOException {
        // 准备数据, 使用系统默认编码
        try (final FileChannel out = new FileOutputStream("char.txt").getChannel()) {
            out.write(ByteBuffer.wrap("使用系统默认编码的测试数据\n".getBytes()));
        }

        // 读数据
        ByteBuffer buffer = ByteBuffer.allocate(DEFAULT_BUFFER_SIZE);
        this.readToBuffer("char.txt", buffer);

        // 准备处理缓冲区
        buffer.flip();
        // 直接输出乱码
        System.out.println(buffer.asCharBuffer());
        // 使用系统默认编码进行解码
        final String encoding = System.getProperty("file.encoding");
        final Charset defaultCharSet = Charset.forName(encoding);
        System.out.println("使用默认编码( " + encoding + " )解码结果: " + defaultCharSet.decode(buffer));

        // 使用 CharBuffer 写入, 需要编码
        buffer.clear();
        buffer.asCharBuffer().put(defaultCharSet.encode("通过 CharBuffer 写入的测试数据").asCharBuffer());

        // 追加
        try (final FileChannel out = new RandomAccessFile("char.txt", "rw").getChannel()) {
            out.position(out.size());
            out.write(buffer);
        }

        // 清空缓冲区, 将数据读取到缓冲区
        buffer.clear();
        this.readToBuffer("char.txt", buffer);
        // 准备处理缓冲区
        buffer.flip();
        System.out.println("使用默认编码( " + encoding + " )解码结果: " + defaultCharSet.decode(buffer));

    }

    /**
     * 把文件中的内容读取到缓冲区中
     *
     * @param filename 待读取的文件名
     * @param buffer   缓冲区
     */
    private void readToBuffer(String filename, ByteBuffer buffer) {
        try (final FileChannel in = new FileInputStream(filename).getChannel()) {
            // 将数据读到缓冲区
            in.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
