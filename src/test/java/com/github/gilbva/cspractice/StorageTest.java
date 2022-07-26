package com.github.gilbva.cspractice;

import com.github.gilbva.cspractice.datastructures.disk.PageStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.*;

public class StorageTest {

    private Random random = new Random();

    @Test
    public void testPageStorage() throws IOException {
        try(PageStorage storage = createFilePageStorage("page-store-test", ".pages")) {
            Assertions.assertTrue(storage.pageSize() > 256);
            for (int i = 0; i < 100; i++) {
                ByteBuffer page = createRandomPage(storage.pageSize());
                int[] pageIndex = new int[]{i};
                Assertions.assertThrows(Throwable.class, () -> storage.writePage(pageIndex[0], page));
                Assertions.assertThrows(Throwable.class, () -> storage.readPage(pageIndex[0], page));
                Assertions.assertEquals(i, storage.createPage());
                Assertions.assertEquals(i + 1, storage.pageCount());
                storage.writePage(i, page);
                ByteBuffer page1 = ByteBuffer.allocate(storage.pageSize());
                storage.readPage(i, page1);
                Assertions.assertArrayEquals(page.array(), page1.array());
            }
        }
    }

    private ByteBuffer createRandomPage(int pageSize) {
        byte[] arr = new byte[pageSize];
        random.nextBytes(arr);
        return ByteBuffer.wrap(arr);
    }

    private PageStorage createFilePageStorage(String prefix, String sufix) throws IOException {
        var file = File.createTempFile(prefix, sufix);
        var raf = new RandomAccessFile(file, "rw");
        return new PageStorage(raf);
    }
}