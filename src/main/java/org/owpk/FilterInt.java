package org.owpk;

import org.owpk.entities.Component;

import java.util.List;

public interface FilterInt<E extends Component> {

   List<E> doFilter(List<E> entities);
}
