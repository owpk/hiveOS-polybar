package org.owpk.utils;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@ToString
public class TokenManager {
    private final String TEMP_FILE = "tmp";

    public void writeToken(String token, Integer tokenExpiration) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEMP_FILE))) {
            String dayX = String.valueOf(new Date().getTime());
            writer.write(token+"\n");
            writer.write(tokenExpiration+"\n");
            writer.write(dayX+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getToken() {
        String token = "default";
        List<String> lines = getTempData();
        if (lines.size() > 0)
            token = lines.get(0);
        return token;
    }

    public boolean checkIfExpired() {
        double currentTime = new Date().getTime() * 0.001;
        List<String> lines = getTempData();
        if (lines.size() != 3) return false;
        Long tokenExpirationTime = Long.parseLong(lines.get(1));
        Double dayX = Long.parseLong(lines.get(2)) * 0.001;
        if (dayX + tokenExpirationTime > currentTime) {
            return true;
        }
        System.out.println("AccessTokenExpired");
        return false;
    }

    private List<String> getTempData() {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get(TEMP_FILE));
        } catch (Exception e) {
            System.out.println("AuthError");
        }
        return lines;
    }

}
