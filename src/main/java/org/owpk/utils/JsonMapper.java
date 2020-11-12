package org.owpk.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.owpk.entities.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonMapper {
   private static final ObjectMapper mapper;

   static {
      mapper = new ObjectMapper();
   }

   public static <T> String writeToJson(T obj) throws IOException {
      final StringWriter writer = new StringWriter();
      mapper.writeValue(writer, obj);
      return writer.toString();
   }

   public static <T, E> T convert(E obj, Class<T> clazz) {
      return mapper.convertValue(obj, clazz);
   }

   @SuppressWarnings("unchecked")
   public static <E extends Component> List<Map<String, Object>> convert(List<E> entities) {
      List<Map<String, Object>> maps = new ArrayList<>();
      for (E e : entities) {
         maps.add(convert(e, HashMap.class));
      }
      return maps;
   }

   public static <T> T readFromJson(String json, Class<T> clazz) {
      T obj = null;
      try {
         obj = mapper.readValue(json, clazz);
      } catch (JsonProcessingException e) {
         e.printStackTrace();
      }
      return obj;
   }


}
