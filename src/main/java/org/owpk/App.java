package org.owpk;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.owpk.entities.User;
import org.owpk.entities.UserDetails;
import org.owpk.entities.api.wallet.Wallet;
import org.owpk.module.CurrentModule;
import org.owpk.utils.Resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
        polybar = new CurrentModule();

        switch (args[0]) {
            case "-a":
                UserDetails userDetails = interactiveAuth();
                Resources.write(userDetails, Resources.USER_DETAILS_CONFIG);
                polybar.authRequest(userDetails);
                break;
            case "-w":
                polybar.walletRequest();
                break;
        }
    }

    public static UserDetails interactiveAuth() {
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

        UserDetails userDetails = new UserDetails();
        userDetails.setUser(user);

        return userDetails;
    }

}
