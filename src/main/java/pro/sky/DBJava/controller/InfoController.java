package pro.sky.DBJava.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

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

    @GetMapping("/sum")
    public long getSum() {
        logger.info("Was invoked method for calculating sum");
        long sum = Stream.iterate(1L, a -> a + 1)
                .limit(1_000_000)
                .parallel()
                .reduce(0L, Long::sum);
        return sum;
    }

    @GetMapping("/sum-optimized")
    public long getSumOptimized() {
        logger.info("Was invoked method for optimized sum calculation");
        long n = 1_000_000;
        return (n * (n + 1)) / 2;
    }
}