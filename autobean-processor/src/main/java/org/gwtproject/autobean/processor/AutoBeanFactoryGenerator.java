package org.gwtproject.autobean.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import org.gwtproject.autobean.gwt.client.impl.AbstractAutoBeanFactory;
import org.gwtproject.autobean.gwt.client.impl.JsniCreatorMap;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;


//package com.sample.cors;
//
//public class TestAutoBeanFactoryImpl extends com.google.web.bindery.autobean.gwt.client.impl.AbstractAutoBeanFactory implements com.sample.cors.TestAutoBeanFactory {
//    @Override protected void initializeCreatorMap(com.google.web.bindery.autobean.gwt.client.impl.JsniCreatorMap map) {
//        map.add(com.sample.cors.Person.class, getConstructors_com_sample_cors_Person());
//    }
//    private native com.google.gwt.core.client.JsArray<com.google.gwt.core.client.JavaScriptObject> getConstructors_com_sample_cors_Person() /*-{
//    return [
//      @com.sample.cors.PersonAutoBean::new(Lcom/google/web/bindery/autobean/shared/AutoBeanFactory;),
//      @com.sample.cors.PersonAutoBean::new(Lcom/google/web/bindery/autobean/shared/AutoBeanFactory;Lcom/sample/cors/Person;)
//    ];
//  }-*/;
//    @Override protected void initializeEnumMap() {
//    }
//    public com.google.web.bindery.autobean.shared.AutoBean person() {
//        return new com.sample.cors.PersonAutoBean(TestAutoBeanFactoryImpl.this);
//    }
//}

public class AutoBeanFactoryGenerator {
    private final String name;
    private final TypeElement factory;

    AutoBeanFactoryGenerator(TypeElement factory) {
        this.factory = factory;
        this.name = factory.getSimpleName().toString() + "Impl";
    }

    public TypeSpec generate() {
        return TypeSpec.classBuilder(name)
                .addModifiers(Modifier.PUBLIC)
                .superclass(AbstractAutoBeanFactory.class)
                .addSuperinterface(TypeName.get(factory.asType()))
                .addMethod(initilizeCreatorMap())
                .addMethod(initializeEnumMap())
                .build();
    }

    private MethodSpec initilizeCreatorMap() {
        return MethodSpec.methodBuilder("initializeCreatorMap")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .addParameter(ClassName.get(JsniCreatorMap.class), "map")
                .build();
    }

    private MethodSpec initializeEnumMap() {
        return MethodSpec.methodBuilder("initializeEnumMap")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .build();
    }
}
