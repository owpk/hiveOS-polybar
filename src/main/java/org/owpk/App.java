package org.owpk;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.owpk.core.RawDataManager;
import org.owpk.entities.apiJson.auth.User;
import org.owpk.entities.apiJson.farm.Farm;
import org.owpk.entities.apiJson.wallet.Wallet;
import org.owpk.entities.jsonConfig.JsonAppConfig;
import org.owpk.core.AuthManager;
import org.owpk.core.EntityDataManager;
import org.owpk.utils.Resources;

import java.util.Scanner;

public class App {

   public static void main(String[] args) {
      String[] _args = args;

      if (checkIfDebugNeeded(args))
         _args = getOtherArgs(args, "--debug");

      JsonAppConfig jsonConfig = Resources.ConfigReader.getJsonConfig().getJsonAppConfig();

      switch (_args[0]) {
         case "-a":
            AuthManager authManager = new AuthManager();
            User user = interactiveAuth();
            authManager.authRequest(user);
            break;
         case "-w":
            new EntityDataManager().entityRequest(getOtherArgs(_args, _args[0]),
                    "/farms/" + jsonConfig.getFarmId() + "/wallets", Wallet.class);
            break;
         case "-f":
            new EntityDataManager().entityRequest(getOtherArgs(_args, _args[0]),
                    "/farms", Farm.class);
            break;
         case "-r":
            RawDataManager rawInputManager = new RawDataManager();
            rawInputManager.rawRequest(getOtherArgs(_args, _args[0]));
      }
   }

   private static boolean checkIfDebugNeeded(String[] args) {
      for (String arg: args) {
         if (arg.equals("--debug")) {
            Configurator.setRootLevel(Level.ALL);
            return true;
         }
      }
      return false;
   }

   private static String[] getOtherArgs(String[] args, String argName) {
      for (int i = 0; i < args.length; i++) {
         if (args[i].equals(argName)) {
            String[] _args = new String[args.length - 1];
            System.arraycopy(args, 0, _args, 0, i);
            System.arraycopy(args, i + 1, _args, i, _args.length - i);
            return _args;
         }
      }
      return args;
   }

   private static User interactiveAuth() {
      User user = new User();
      Scanner sc = new Scanner(System.in);

      System.out.print("Login: ");
      String login = sc.nextLine();

      System.out.print("Enter password: ");
      String password = sc.nextLine();

      System.out.print("Enter two auth code: ");
      String twoAuth = sc.nextLine();

      System.out.print("Remember?: ");
      String remember = sc.nextLine();

      user.setLogin(login);
      user.setPassword(password);
      user.setTwofa_code(twoAuth);
      user.setRemember(remember.startsWith("tru"));

      return user;
   }

}
