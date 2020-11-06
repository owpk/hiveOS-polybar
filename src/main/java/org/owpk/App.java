package org.owpk;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.owpk.entities.api.auth.User;
import org.owpk.module.CurrentModule;
import org.owpk.resolver.WalletSpecResolver;

import java.io.IOException;
import java.util.Scanner;

public class App {

   public static void main(String[] args) throws IOException, UnirestException {
      org.owpk.module.Module module = new CurrentModule();
      switch (args[0]) {
         case "-a":
            User user = interactiveAuth();
            module.authRequest(user);
            break;
         case "-w":
            module.walletsRequest(new WalletSpecResolver(getOther(args)));
            break;
      }
   }

   private static String[] getOther(String[] args){
      final String[] _args = new String[args.length - 1];
      System.arraycopy(args, 1, _args, 0, args.length - 1);
      return _args;
   }

   public static User interactiveAuth() {
      User user = new User();
      Scanner sc = new Scanner(System.in);

      System.out.print("Login: ");
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

}
