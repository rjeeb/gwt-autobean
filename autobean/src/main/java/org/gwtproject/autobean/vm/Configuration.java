/*
 * Copyright 2010 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.gwtproject.autobean.vm;

import org.gwtproject.autobean.shared.AutoBean;

import java.util.*;

/**
 * Used by {@link AutoBeanFactorySource#createBean(Class, org.gwtproject.autobean.vm.Configuration)}. This
 * type replicates the annotations that may be applied to an AutoBeanFactory
 * declaration.
 * <p>
 * <span style='color: red'>This is experimental, unsupported code.</span>
 */
public class Configuration {
  /**
   * Builds {@link org.gwtproject.autobean.vm.Configuration} objects.
   */
  public static class Builder {
    private org.gwtproject.autobean.vm.Configuration toReturn = new org.gwtproject.autobean.vm.Configuration();

    public org.gwtproject.autobean.vm.Configuration build() {
      toReturn.noWrap.add(AutoBean.class);
      toReturn.noWrap = Collections.unmodifiableSet(toReturn.noWrap);
      try {
        return toReturn;
      } finally {
        toReturn = null;
      }
    }

    /**
     * Equivalent to applying a
     * {@link org.gwtproject.autobean.shared.AutoBeanFactory.Category
     * Category} annotation to an AutoBeanFactory declaration.
     * 
     * @param categories the category types that should be searched for static
     *          implementations of non-property methods
     * @return the Builder
     */
    public Builder setCategories(Class<?>... categories) {
      toReturn.categories =
          Collections.unmodifiableList(new ArrayList<Class<?>>(Arrays.asList(categories)));
      return this;
    }

    /**
     * Equivalent to applying a
     * {@link org.gwtproject.autobean.shared.AutoBeanFactory.NoWrap
     * NoWrap} annotation to an AutoBeanFactory declaration.
     * 
     * @param noWrap the types that should be excluded from wrapping
     * @return the Builder
     */
    public Builder setNoWrap(Class<?>... noWrap) {
      toReturn.noWrap.addAll(Arrays.asList(noWrap));
      return this;
    }
  }

  private List<Class<?>> categories = Collections.emptyList();

  private Set<Class<?>> noWrap = new HashSet<Class<?>>();

  private Configuration() {
  }

  public List<Class<?>> getCategories() {
    return categories;
  }

  public Set<Class<?>> getNoWrap() {
    return noWrap;
  }
}
