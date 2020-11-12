package org.owpk.utils;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@ToString
public class TokenManager {
    private final String TEMP_FILE = Resources.CURRENT_DIR + "/tmp";

    public void writeToken(String token, Integer tokenExpiration) {
        File temp = new File(TEMP_FILE);
        temp.setWritable(true);
        temp.setReadable(true);
        try (PrintWriter writer = new PrintWriter(temp)) {
            String dayX = String.valueOf(new Date().getTime());
            writer.write(token + "@");
            writer.write(tokenExpiration + "@");
            writer.write(dayX + "@");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            temp.setWritable(false);
            temp.setReadable(false);
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
        File temp = new File(TEMP_FILE);
            try {
                temp.setReadable(true);
                lines = Arrays.stream(new String(Files.readAllBytes(temp.toPath()))
                       .split("@"))
                       .collect(Collectors.toList());
            } catch (IOException e) {
                System.out.println("AuthError");
                e.printStackTrace();
            } finally {
                temp.setReadable(false);
            }
        return lines;
    }
}
