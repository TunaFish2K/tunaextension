package tunafish2k.tunaextension.puppet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class CallbackMessageHandler implements HttpHandler {
    public static LinkedList<String> messages = new LinkedList<>();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            StringBuffer sb = new StringBuffer();
            BufferedReader br = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody(), "utf-8"));
            String temp;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
            br.close();

            String bodyRaw = sb.toString();

            JSONObject body = JSON.parseObject(bodyRaw);
            String message = body.getString("message");
            String name = body.getString("username");

            messages.push(String.format("\u00a7b[%s] %s", name, message));
            System.out.println(messages.size());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpExchange.getResponseHeaders().add("Content-Type:", "text/html");
            httpExchange.sendResponseHeaders(200, 0);
            httpExchange.getRequestBody().close();
        }
    }
}
