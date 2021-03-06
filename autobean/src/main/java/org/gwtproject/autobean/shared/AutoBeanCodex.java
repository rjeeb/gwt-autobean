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
package org.gwtproject.autobean.shared;

import org.gwtproject.autobean.shared.impl.AutoBeanCodexImpl;
import org.gwtproject.autobean.shared.impl.StringQuoter;

/**
 * Utility methods for encoding an AutoBean graph into a JSON-compatible string.
 * This codex intentionally does not preserve object identity, nor does it
 * encode cycles, but it will detect them.
 */
public class AutoBeanCodex {

    /**
     * Decode an AutoBeanCodex payload.
     *
     * @param <T>     the expected return type
     * @param factory an AutoBeanFactory capable of producing {@code AutoBean<T>}
     * @param clazz   the expected return type
     * @param data    a payload previously generated by {@link #encode(AutoBean)}
     * @return an AutoBean containing the payload contents
     */
    public static <T> AutoBean<T> decode(AutoBeanFactory factory, Class<T> clazz, Splittable data) {
        return AutoBeanCodexImpl.doDecode(AutoBeanCodexImpl.EncodeState.forDecode(factory), clazz, data);
    }

    /**
     * Decode an AutoBeanCodex payload.
     *
     * @param <T>     the expected return type
     * @param factory an AutoBeanFactory capable of producing {@code AutoBean<T>}
     * @param clazz   the expected return type
     * @param payload a payload string previously generated by
     *                {@link #encode(AutoBean)}{@link Splittable#getPayload()
     *                .getPayload()}.
     * @return an AutoBean containing the payload contents
     */
    public static <T> AutoBean<T> decode(AutoBeanFactory factory, Class<T> clazz, String payload) {
        Splittable data = StringQuoter.split(payload);
        return decode(factory, clazz, data);
    }

    /**
     * Copy data from a {@link Splittable} into an AutoBean. Unset values in the
     * Splittable will not nullify data that already exists in the AutoBean.
     *
     * @param data the source data to copy
     * @param bean the target AutoBean
     */
    public static void decodeInto(Splittable data, AutoBean<?> bean) {
        AutoBeanCodexImpl.doDecodeInto(AutoBeanCodexImpl.EncodeState.forDecode(bean.getFactory()), data, bean);
    }

    /**
     * Encodes an AutoBean. The actual payload contents can be retrieved through
     * {@link Splittable#getPayload()}.
     *
     * @param bean the bean to encode
     * @return a Splittable that encodes the state of the AutoBean
     */
    public static Splittable encode(AutoBean<?> bean) {
        if (bean == null) {
            return Splittable.NULL;
        }

        StringBuilder sb = new StringBuilder();
        AutoBeanCodexImpl.EncodeState state = AutoBeanCodexImpl.EncodeState.forEncode(bean.getFactory(), sb);
        AutoBeanCodexImpl.doEncode(state, bean);
        return StringQuoter.split(sb.toString());
    }
}
