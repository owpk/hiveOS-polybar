package org.owpk;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.owpk.entities.User;
import org.owpk.entities.UserDetails;
import org.owpk.entities.api.wallet.Wallet;
import org.owpk.module.CurrentModule;
import org.owpk.utils.Resources;

import java.io.IOException;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class App {

    private static org.owpk.module.Module polybar;

    private static final Consumer<Wallet> pullUsdMinimalInfo;
    static {
        pullUsdMinimalInfo = x -> System.out.println(
            (x.getPoolBalances() != null ?
                    (x.getPoolBalances().stream()
                            .map(i -> String.format("%s : %s$", i.getPool(), i.getValueUsd()))
                            .collect(Collectors.joining())) : "N/A"));
    }

    public static void main(String[] args) throws IOException, UnirestException {
        UserDetails userDetails = Resources.read("cfg.yaml", UserDetails.class);
        polybar = new CurrentModule(userDetails);

        switch (args[0]) {
            case "-a":
                polybar.authRequest();
                break;
            case "-w":
                polybar.walletRequest();
                break;
        }
    }

    public static User interactiveAuth() {
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

        Resources.write(user, "cfg.yaml");
        return user;
    }

}
