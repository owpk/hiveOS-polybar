package org.owpk.core.resolver;

import com.mashape.unirest.http.JsonNode;
import org.owpk.entities.Component;
import org.owpk.entities.Composite;
import org.owpk.entities.jsonConfig.JsonConfig;
import org.owpk.utils.Resources;
import picocli.CommandLine;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EntityResolver<E extends Component> extends AbsResolver<List<E>> {
   private final String JSON_CONFIG_ENTITY_NAME;

   @CommandLine.Option(names = {"-f", "--filter"},
          description = "filter by json field name and value [-f field1:value1,value2;field2:value1,value2;...]")
   protected String filter;

   @CommandLine.Option(names = {"-v", "--verbose"},
          description = "filter by json field name [-v field1,field2,field3:filed1:field2,field3...] ")
   protected String arguments;

   @CommandLine.Option(names = {"-d", "--delimiter"})
   protected boolean delimiter;

   @CommandLine.Option(names = {"-D", "--default"})
   protected boolean defaultPrintPattern;

   @CommandLine.Option(names = {"-r", "--raw"})
   protected boolean configJson;

   public EntityResolver(String[] args, String jsonConfigEntityName) {
      super(args);
      this.JSON_CONFIG_ENTITY_NAME = jsonConfigEntityName;
   }

   public void resolve(List<E> list) {
      try {
         if (defaultPrintPattern)
            Resources.ConfigReader.getJsonConfig().getJsonAppConfig().setFormat("%s : %s\n");
         Composite<E> composite = new Composite<>(list);
         composite.useDelimiter(delimiter);
         if (!configJson) {
            JsonConfig cfg = Resources.ConfigReader.getJsonConfigProperty(JSON_CONFIG_ENTITY_NAME);
            composite.doFilter(cfg);
            composite.execute(cfg);
         } else {
            composite.doFilter(parseFilterOptions());
            composite.execute(getOptionList());
         }
      } catch (Exception e) {
         log.error(e);
      }
   }

   protected List<String> getOptionList() {
      return parseOptions(arguments, ",");
   }

   public void printError(JsonNode body, int status) {
      System.out.printf("Err: %s, %d", body, status);
   }

   protected Map<String, List<String>> parseFilterOptions() {
      return parseOptions(filter, ";").stream()
             .collect(Collectors.toMap(x -> x.split(":")[0],
                    i -> parseOptions(i.split(":")[1], ",")));
   }

   protected List<String> parseOptions(String opt, String regex) {
      if (opt == null) return Collections.emptyList();
      return Arrays.stream(opt.split(regex)).map(String::trim).collect(Collectors.toList());
   }

}
