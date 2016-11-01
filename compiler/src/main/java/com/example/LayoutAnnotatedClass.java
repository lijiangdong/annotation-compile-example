package com.example;

import javax.lang.model.element.TypeElement;

/**
 * Created by lijiangdong on 2016/10/31.
 */
public class LayoutAnnotatedClass {
    private TypeElement annotatedClassElement;
    private String qualifiedSuperClassName;
    private String simpleTypeName;
    private int id;

    public LayoutAnnotatedClass(TypeElement annotatedClassElement) {
        this.annotatedClassElement = annotatedClassElement;
        ContentView contentView = annotatedClassElement.getAnnotation(ContentView.class);
        id = contentView.value();

        if ("".equals(id)) {
            throw new IllegalArgumentException(
                    String.format(
                            "id() in @%s for class %s is null or empty! that's not allowed",
                            ContentView.class.getSimpleName(),
                            annotatedClassElement.getQualifiedName().toString()));
        }

        qualifiedSuperClassName = annotatedClassElement.getQualifiedName().toString();
        simpleTypeName = annotatedClassElement.getSimpleName().toString();
    }

    public int getId() {
        return id;
    }

    public TypeElement getTypeElement() {
        return annotatedClassElement;
    }

    public String getQualifiedSuperClassName() {
        return qualifiedSuperClassName;
    }

    public String getSimpleTypeName() {
        return simpleTypeName;
    }
}
