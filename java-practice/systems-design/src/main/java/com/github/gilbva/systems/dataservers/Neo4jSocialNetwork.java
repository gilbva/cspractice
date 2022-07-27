package com.github.gilbva.systems.dataservers;

import java.util.List;
import java.util.Map;

/**
 * Neo4j Cypher https://neo4j.com/docs/cypher-manual/current/clauses
 */
public class Neo4jSocialNetwork implements AutoCloseable {

    public Neo4jSocialNetwork(String url) {
    }

    @Override
    public void close() throws Exception {
    }

    public List<Integer> getUsers() {
        return null;
    }

    public void deleteAll() {
    }

    public void addUser(String name, int age) {
    }

    public Map<String, Integer> getUser(Integer id) {
        return null;
    }
}
