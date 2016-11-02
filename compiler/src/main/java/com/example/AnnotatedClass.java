package com.example;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by lijiangdong on 2016/10/31.
 */
public class AnnotatedClass {
    private final String SUFFIX = "$$ViewInjector";
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
        ClassName layoutName = ClassName.get(ViewInjector.class);
        ClassName superInterface = ClassName.bestGuess(typeElement.getQualifiedName().toString());
        MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder("inject")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.VOID)
                .addParameter(superInterface,"activity")
                .beginControlFlow("if(activity instanceof android.support.v7.app.AppCompatActivity)");
        for (ContentType contentType : contentTypes){
            methodSpecBuilder.addStatement("activity.setContentView($L)",contentType.getResId());
        }
        for (InjectViewField injectViewField : injectViewFields){
            methodSpecBuilder.addStatement("activity.$N = ($T)activity.findViewById($L)",
                    injectViewField.getSimpleName().toString(),injectViewField.getFieldType(),injectViewField.getResId());
        }
        TypeSpec typeSpec = TypeSpec.classBuilder(typeElement.getSimpleName()+SUFFIX)
                .addSuperinterface(ParameterizedTypeName.get(layoutName,superInterface))
                .addModifiers(Modifier.PUBLIC)
                .addMethod(methodSpecBuilder.build())
                .build();
        return JavaFile
                .builder(elementUtils.getPackageOf(typeElement).getQualifiedName().toString(),typeSpec)
                .build();

    }
}
