package com.github.gilbva.cspractice;

import com.github.gilbva.cspractice.replication.Database;
import com.github.gilbva.cspractice.replication.DatabaseSnapshot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReplicationTest {
    @Test
    public void testReplication() {
        Database database = new Database();

        database.set("k1", "value");
        Assertions.assertEquals("value", database.get("k1"));

        database.set("k1", "value 1");
        database.set("k2", "value 2");
        Assertions.assertEquals("value 1", database.get("k1"));
        Assertions.assertEquals("value 2", database.get("k2"));

        DatabaseSnapshot snapshot1 = database.takeSnapshot();
        database.set("k1", "value 11");
        Assertions.assertEquals("value 11", database.get("k1"));
        Assertions.assertEquals("value 2", database.get("k2"));
        Assertions.assertEquals("value 1", snapshot1.get("k1"));
        Assertions.assertEquals("value 2", snapshot1.get("k2"));

        DatabaseSnapshot snapshot2 = database.takeSnapshot();
        database.set("k2", "value 22");
        Assertions.assertEquals("value 11", database.get("k1"));
        Assertions.assertEquals("value 22", database.get("k2"));
        Assertions.assertEquals("value 1", snapshot1.get("k1"));
        Assertions.assertEquals("value 2", snapshot1.get("k2"));
        Assertions.assertEquals("value 11", snapshot2.get("k1"));
        Assertions.assertEquals("value 2", snapshot2.get("k2"));

        database.delete("k2");
        Assertions.assertEquals("value 11", database.get("k1"));
        Assertions.assertNull(database.get("k2"));
        Assertions.assertEquals("value 1", snapshot1.get("k1"));
        Assertions.assertEquals("value 2", snapshot1.get("k2"));
        Assertions.assertEquals("value 11", snapshot2.get("k1"));
        Assertions.assertEquals("value 2", snapshot2.get("k2"));

        database.delete("k1");
        Assertions.assertNull(database.get("k1"));
        Assertions.assertNull(database.get("k2"));
        Assertions.assertEquals("value 1", snapshot1.get("k1"));
        Assertions.assertEquals("value 2", snapshot1.get("k2"));
        Assertions.assertEquals("value 11", snapshot2.get("k1"));
        Assertions.assertEquals("value 2", snapshot2.get("k2"));
    }
}
