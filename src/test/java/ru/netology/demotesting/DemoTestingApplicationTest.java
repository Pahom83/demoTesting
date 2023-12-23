package ru.netology.demotesting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoTestingApplicationTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Container
    private final GenericContainer<?> devapp = new GenericContainer<>("devapp")
            .withExposedPorts(8080);
    @Container
    private final GenericContainer<?> prodapp = new GenericContainer<>("prodapp")
            .withExposedPorts(8081);

    @Test
    void contextLoads() {
        String answerDevapp = "Current profile is dev";
        String answerProdapp = "Current profile is production";
        String entityDevapp = restTemplate.getForEntity("http://localhost:" + devapp.getMappedPort(8080) + "/profile", String.class)
                .getBody();
//        System.out.println(entityDevapp.getBody());
        String entityProdapp = restTemplate.getForEntity("http://localhost:" + prodapp.getMappedPort(8081) + "/profile", String.class)
                .getBody();
//        System.out.println(forEntityProdapp.getBody());

        Assertions.assertEquals(answerDevapp, entityDevapp);
        Assertions.assertEquals(answerProdapp, entityProdapp);

    }

}