package org.owpk.resolver;

import java.util.List;

public interface Resolver<E> {
   void resolve(List<E> list);
}
