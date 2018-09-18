package rocks.byivo.todolist.controllers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-it.properties")
@Ignore
public class DefaultIntegrationTestRunner {

    @LocalServerPort
    int port;
    
    @Before
    public void setUpPort() throws Exception {
	RestAssured.port = port;
    }
    
}
