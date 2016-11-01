package com.example;

import java.util.List;

import javax.lang.model.element.TypeElement;

/**
 * Created by lijiangdong on 2016/10/31.
 */
public class AnnotatedClass {
    private TypeElement annotatedClassElement;
    private String qualifiedSuperClassName;
    private String simpleTypeName;
    private List<ContentType> contentTypes;
    private List<InjectViewField> injectViewFields;

    public AnnotatedClass(TypeElement annotatedClassElement) {
        this.annotatedClassElement = annotatedClassElement;
        qualifiedSuperClassName = annotatedClassElement.getQualifiedName().toString();
        simpleTypeName = annotatedClassElement.getSimpleName().toString();
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

    public void addInjectView(InjectViewField injectViewField){
        injectViewFields.add(injectViewField);
    }

    public void addContentType(ContentType contentType){
        contentTypes.add(contentType);
    }
}
