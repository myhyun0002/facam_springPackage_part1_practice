package com.example.was_practice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * was 서버를 직접 만들어 본다.
 */
public class CustomWebApplicationServer {

    private final int port;

    // thread pool 생성 (정해진 갯수의 thread를 미리 생성)
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    private static final Logger logger = LoggerFactory.getLogger(CustomWebApplicationServer.class);

    public CustomWebApplicationServer(int port) {
        this.port = port;
    }

    public void start() throws IOException{
        try(ServerSocket serverSocket = new ServerSocket(port)){
            logger.info("[CustomWebApplicationServer] started {} port",port);

            Socket clientSocket;
            logger.info("[CustomWebApplicationServer] waiting for client.");

            /**
             * Step 2
             *  여러 사용자가 들어왔을 때마다 Thread를 생성해서 사용자 요청을 처리하도록 한다.
             *  thread는 생성될 때마다 독립적인 스택 메모리를 할당한다.
             *  메모리 할당 작업은 비싼 작업이다.
             *  - 단점
             *      - 들어올 때마다 메모리를 할당하는 건 매우 비효율적이다.
             *      - thread는 비용이 커
             *      - 즉 동시 접속자 수가 많아지면 많은 thread를 생성하게 되고
             *      - cpu의 context switching 횟수가 많아진다는 것이고 cpu 사용량, 메모리 사용량 증가
             *
             *
             *  - 개선
             *      - 정해진 개수의 thread를 미리 생성해서 (thread pool) 계속해서 재활용하는 방식으로 한다.
             */
//            while((clientSocket = serverSocket.accept()) != null){
//                logger.info("[CustomWebApplicationServer] client connect");
//                new Thread(new ClientRequestHandler(clientSocket)).start();
//            }

            /**
             * Step 3
             * - thread pool 을 활용해서 안정적인 서비스 구현
             *
             *
             * - 결과
             *      new client pool-1-thread-1 started
             *      - 1번 pool에 있는 1번 thread라는 뜻
             */
            while((clientSocket = serverSocket.accept()) != null){
                logger.info("[CustomWebApplicationServer] client connect");
                executorService.execute(new ClientRequestHandler(clientSocket));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
