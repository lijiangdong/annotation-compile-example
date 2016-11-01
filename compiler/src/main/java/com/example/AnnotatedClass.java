package com.example;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by lijiangdong on 2016/10/31.
 */
public class AnnotatedClass {
    private TypeElement typeElement;
    private List<ContentType> contentTypes;
    private List<InjectViewField> injectViewFields;
    private Elements elementUtils;
    public AnnotatedClass(TypeElement typeElement, Elements elementUtils) {
        this.typeElement = typeElement;
        this.elementUtils = elementUtils;
    }

    public String getQualifiedSuperClassName() {
        return typeElement.getQualifiedName().toString();
    }

    public void addInjectView(InjectViewField injectViewField){
        injectViewFields.add(injectViewField);
    }

    public void addContentType(ContentType contentType){
        contentTypes.add(contentType);
    }

    public JavaFile generateJavaFile(){
        MethodSpec methodSpec = MethodSpec.methodBuilder("")
                .build();
        TypeSpec typeSpec = TypeSpec.classBuilder("")
                .build();
        return JavaFile
                .builder(elementUtils.getPackageOf(typeElement).getQualifiedName().toString(),typeSpec)
                .build();

    }
}
