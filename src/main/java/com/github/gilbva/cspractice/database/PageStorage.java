package com.github.gilbva.cspractice.database;

import java.io.Closeable;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class PageStorage implements Closeable {
    private final RandomAccessFile raf;

    public PageStorage(RandomAccessFile raf) {
        this.raf = raf;
    }

    public int pageSize() {
        return 0;
    }

    public int pageCount() {
        return 0;
    }

    public int createPage() throws IOException {
        return 0;
    }

    public void writePage(int id, ByteBuffer data) throws IOException {

    }

    public void readPage(int id, ByteBuffer data) throws IOException {

    }

    public void clear(int pageSize) throws IOException {

    }

    public void close() throws IOException {

    }
}
