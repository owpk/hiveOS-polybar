package org.owpk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.owpk.entities.User;
import org.owpk.entities.api.wallet.Wallet;
import org.owpk.module.CurrentModule;
import org.owpk.utils.JsonMapper;
import org.owpk.utils.Resources;
import org.owpk.utils.TokenManager;
import org.owpk.entities.UserDetails;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class App {
    private static final TokenManager tokenManager;
    private static final CurrentModule polybar;
    private static final Consumer<Wallet> pullUsdMinimalInfo;

    static {
        tokenManager = new TokenManager();
        polybar = new CurrentModule();

        pullUsdMinimalInfo = x -> System.out.println(
            (x.getPoolBalances() != null ?
                    (x.getPoolBalances().stream()
                            .map(i -> String.format("%s : %s$", i.getPool(), i.getValueUsd()))
                            .collect(Collectors.joining())) : "N/A"));

    }

    public static void main(String[] args) throws IOException, UnirestException {
        UserDetails userDetails = Resources.read("cfg.yaml", UserDetails.class);

        switch (args[0]) {
            case "-ai":
                User u = interactiveAuth();
                tryToAuth(u);
                break;
            case "-a":
                System.out.println("Enter two auth code: ");
                String twoAuth = new Scanner(System.in).nextLine();
                userDetails.getUser().setTwofa_code(twoAuth);
                tryToAuth(userDetails.getUser());
                break;
            case "-wpm":
                walletRequest(userDetails, pullUsdMinimalInfo);
                break;
            case "-w":
                walletRequest(userDetails, System.out::println);
                break;
            case "-wp":
                walletRequest(userDetails, x -> System.out.println(x.getPoolBalances()));
        }
    }

    private static User interactiveAuth() {
        User user = new User();
        Scanner sc = new Scanner(System.in);

        System.out.print("Login: " );
        String login = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        System.out.print("Enter two auth code: ");
        String twoAuth = sc.nextLine();

        user.setLogin(login);
        user.setPassword(password);
        user.setTwofa_code(twoAuth);

        return user;
    }

    //TODO Make it manage by Module.class
    private static void tryToAuth(User user) throws IOException, UnirestException {
        HttpResponse<JsonNode> response = polybar.auth(user);
        if (response.getStatus() == 200) {
            tokenManager.writeToken((String) response.getBody().getObject().get("access_token"),
                    (Integer) response.getBody().getObject().get("expires_in"));
        }
        System.out.println("Auth:"+response.getStatusText());
    }

    //TODO Make it manage by Module.class
    private static void walletRequest(UserDetails userDetails, Consumer<Wallet> consumer) throws UnirestException {
        if (tokenManager.checkIfExpired()) {
            HttpResponse<JsonNode> response = polybar.wallet(userDetails.getFarm_id(), tokenManager.getToken());

            if (response.getStatus() == 200) {
                List<Wallet> walletList = new ArrayList<>();

                for (Object o : (JSONArray) response.getBody().getObject().get("data")) {
                    Wallet w = JsonMapper.mapFromJson(((JSONObject) o).toString(), Wallet.class);
                        if (userDetails.getWalletNames() != null && userDetails.getWalletNames().contains(w.getName()))
                            walletList.add(w);
                        else if (userDetails.getWalletNames() == null || userDetails.getWalletNames().size() == 0) {
                            walletList.add(w);
                        }
                }

                walletList.forEach(consumer);
                Map<String, String> studentMap = new ObjectMapper().convertValue(walletList.get(0), Map.class);
                System.out.println(studentMap.get("coin"));

            } else {
                System.out.println(response.getBody());
                System.out.println(response.getStatus());
                System.out.println(response.getStatusText());
            }
        }
    }

}
