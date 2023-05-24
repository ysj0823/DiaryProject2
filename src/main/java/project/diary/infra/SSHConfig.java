package project.diary.infra;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class SSHConfig {

    @Bean
    public SSHClient sshClient() {
        SSHClient ssh = new SSHClient();
        ssh.addHostKeyVerifier(new PromiscuousVerifier());
        try {
            ssh.connect("classnet.mju.ac.kr", 1004);
            ssh.authPassword("s60201972", "1234");
        } catch (IOException e) {
            // 예외 처리
            e.printStackTrace();
        }
        return ssh;
    }

}
