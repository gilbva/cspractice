package com.github.gilbva.cspractice.dataservers;

import com.github.gilbva.systems.dataservers.Neo4jSocialNetwork;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

public class Neo4jSocialNetworkTest {

    private Random random = new Random();

    @Test
    public void testSocialNetwork() throws Exception {
        try(var graph = new Neo4jSocialNetwork("bolt://neo4j:7687")) {
            graph.deleteAll();
            Assertions.assertTrue(graph.getUsers().isEmpty());

            TreeMap<String, Integer> usersMap = new TreeMap<>();
            for (int i = 0; i < 1000; i++) {
                String name = UUID.randomUUID().toString().substring(0, 5);
                int age = 3 + (int) (79d * random.nextGaussian());

                usersMap.put(name, age);
                graph.addUser(name, age);
            }

            List<Integer> ids = graph.getUsers();
            var users = new TreeMap<>(ids.stream()
                                                .map(graph::getUser)
                                                .flatMap(x -> x.entrySet().stream())
                                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

            Assertions.assertArrayEquals(users.keySet().toArray(), usersMap.keySet().toArray());
            Assertions.assertArrayEquals(users.values().toArray(), usersMap.values().toArray());

        }
    }
}
