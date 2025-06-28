package kfu.itis.arseniy.service;

import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EmailCodeService {

    @Value("${brevo.api-key}")
    private String apiKey;

    private static final String API_URL = "https://api.brevo.com/v3/smtp/email";
    private final OkHttpClient client = new OkHttpClient();

    public void sendCode(String toEmail, String code) {
        String json = "{"
                + "\"sender\":{\"name\":\"Rakoedov\",\"email\":\"a.arseniy.b@yandex.ru\"},"
                + "\"to\":[{\"email\":\"" + toEmail + "\"}],"
                + "\"subject\":\"Ваш код входа\","
                + "\"htmlContent\":\"<h1>Код входа: " + code + "</h1>\""
                + "}";

        sendRequest(json);
    }

    public boolean sendPasswordResetEmail(String toEmail, String resetLink) {
        String json = "{"
                + "\"sender\":{\"name\":\"Rakoedov\",\"email\":\"a.arseniy.b@yandex.ru\"},"
                + "\"to\":[{\"email\":\"" + toEmail + "\"}],"
                + "\"subject\":\"Password Reset Request\","
                + "\"textContent\":\"To reset your password, click here: " + resetLink + "\""
                + "}";

        return sendRequest(json);
    }

    private boolean sendRequest(String json) {
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("api-key", apiKey)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            if (!response.isSuccessful()) {
                System.out.println("Ошибка при отправке письма: " + response.code() + " - " + responseBody);
                return false;
            } else {
                System.out.println("Письмо успешно отправлено: " + responseBody);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
