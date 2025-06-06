package pro.sky.DBJava.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class InfoController {
    private static final Logger logger = LoggerFactory.getLogger(InfoController.class);
    @Value("${server.port}")
    private String port;

    @GetMapping("/port")
    public String getPort() {
        logger.info("Was invoked method for get port");
        logger.debug("Returning port value: {}", port);
        return port;
    }
}