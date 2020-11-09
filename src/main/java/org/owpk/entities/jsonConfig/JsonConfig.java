package org.owpk.entities.jsonConfig;

import java.util.List;
import java.util.Map;

public class JsonConfig<E> {
   private String objectName;
   private List<Map<String, String>> filterBy;
   private List<String> fieldsToShow;
   private List<String> containsEntities;
}
