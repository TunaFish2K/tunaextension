package tunafish2k.tunaextension.puppet;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.lwjgl.Sys;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class PuppetMessageSender {
    public void sendMessage(Puppet puppet, String message) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("sending...");
                try {
                    HttpClient httpClient = HttpClients.custom().disableAutomaticRetries().build();
                    HttpPost request = new HttpPost(String.format("http://%s:%d/send", puppet.host, puppet.port));
                    request.setConfig(RequestConfig.custom().setConnectTimeout(3 * 1000).build());
                    request.setHeader("Content-Type", "application/json");

                    JSONObject body = new JSONObject();
                    body.put("message", message);

                    System.out.println("sending message...");
                    request.setEntity(new StringEntity(body.toString(), "utf-8"));
                    httpClient.execute(request);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0);
    }
}
