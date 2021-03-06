package com.example;

import com.google.auto.common.SuperficialValidation;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class MyClass extends AbstractProcessor {
    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;
    private Messager messager;
    private Map<String,AnnotatedClass> annotatedClassMap;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
        annotatedClassMap = new HashMap<>();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> stringSet = new LinkedHashSet<>();
        stringSet.add(ContentView.class.getCanonicalName());
        stringSet.add(Inject.class.getCanonicalName());
        return stringSet;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//        studyJavaPoet();
        annotatedClassMap.clear();
        for (Element element : roundEnv.getElementsAnnotatedWith(ContentView.class)){
            if (!SuperficialValidation.validateElement(element))continue;
            TypeElement typeElement = (TypeElement) element;
            AnnotatedClass annotatedClass = annotatedClassMap.get(typeElement.getQualifiedName().toString());
            if (annotatedClass == null){
                annotatedClass = new AnnotatedClass(typeElement,elementUtils);
                annotatedClassMap.put(typeElement.getQualifiedName().toString(),annotatedClass);
            }
            annotatedClass.addContentType(new ContentType(element));
        }

        for (Element element : roundEnv.getElementsAnnotatedWith(Inject.class)){
            if (!SuperficialValidation.validateElement(element))continue;
            getAnnotatedClass(element).addInjectView(new InjectViewField(element));
        }

        for (Element element : roundEnv.getElementsAnnotatedWith(OnClick.class)){
            if (!SuperficialValidation.validateElement(element))continue;
            getAnnotatedClass(element).addClickMethod(new OnClickMethod(element));
        }

        for (Map.Entry<String,AnnotatedClass> entry : annotatedClassMap.entrySet()){
            AnnotatedClass annotatedClass = entry.getValue();
            JavaFile javaFile = annotatedClass.generateJavaFile();
            try {
                javaFile.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        for (Element element :roundEnv.getElementsAnnotatedWith(Inject.class)){
//            if (!SuperficialValidation.validateElement(element)) continue;
//            Inject annotation = element.getAnnotation(Inject.class);
//            VariableElement variableElement = (VariableElement) element;
//            TypeElement typeElement = (TypeElement) element.getEnclosingElement();
//            ClassName layoutName = ClassName.get(ViewInjector.class);
//            ClassName superInterface = ClassName.bestGuess(typeElement.getQualifiedName().toString());
//            MethodSpec viewInject = MethodSpec.methodBuilder("inject")
//                    .addAnnotation(Override.class)
//                    .addModifiers(Modifier.PUBLIC)
//                    .returns(TypeName.VOID)
//                    .addParameter(superInterface,"activity")
//                    .beginControlFlow("if(activity instanceof android.support.v7.app.AppCompatActivity)")
//                    .addStatement("activity.$N = ($T)activity.findViewById($L)",variableElement.getSimpleName().toString(),variableElement.asType(),annotation.value())
//                    .endControlFlow()
//                    .build();
//
//            TypeSpec injectClass = TypeSpec.classBuilder(typeElement.getSimpleName()+SUFFIX + 1)
//                    .addSuperinterface(ParameterizedTypeName.get(layoutName,superInterface))
//                    .addModifiers(Modifier.PUBLIC)
//                    .addMethod(viewInject)
//                    .build();
//            JavaFile javaFile = JavaFile.builder(elementUtils.getPackageOf(typeElement).getQualifiedName().toString(),injectClass)
//                    .build();
//            try {
//                javaFile.writeTo(processingEnv.getFiler());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        return false;
    }

    private AnnotatedClass getAnnotatedClass(Element element){
        TypeElement typeElement = (TypeElement) element.getEnclosingElement();
        AnnotatedClass annotatedClass = annotatedClassMap.get(typeElement.getQualifiedName().toString());
        if (annotatedClass == null){
            annotatedClass = new AnnotatedClass(typeElement,elementUtils);
            annotatedClassMap.put(typeElement.getQualifiedName().toString(),annotatedClass);
        }
        return annotatedClass;
    }

    private void error(Element e, String msg, Object... args) {
        messager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(msg, args),
                e);
    }

//    private void generateCode(int id,TypeElement typeElement){
//        ClassName layoutName = ClassName.get(ViewInjector.class);
//        ClassName superInterface = ClassName.bestGuess(typeElement.getQualifiedName().toString());
//
//        MethodSpec viewInject = MethodSpec.methodBuilder("inject")
//                .addAnnotation(Override.class)
//                .addModifiers(Modifier.PUBLIC)
//                .returns(TypeName.VOID)
//                .addParameter(superInterface,"activity")
//                .beginControlFlow("if(activity instanceof android.support.v7.app.AppCompatActivity)")
//                .addStatement("activity.setContentView($L)",id)
//                .endControlFlow()
//                .build();
//
//        TypeSpec injectClass = TypeSpec.classBuilder(typeElement.getSimpleName()+SUFFIX)
//                .addSuperinterface(ParameterizedTypeName.get(layoutName,superInterface))
//                .addModifiers(Modifier.PUBLIC)
//                .addMethod(viewInject)
//                .build();
//        JavaFile javaFile = JavaFile.builder(elementUtils.getPackageOf(typeElement).getQualifiedName().toString(),injectClass)
//                .build();
//        try {
//            javaFile.writeTo(processingEnv.getFiler());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private boolean isValidClass(TypeElement classElement) {

        // Cast to TypeElement, has more type specific methods

        if (!classElement.getModifiers().contains(Modifier.PUBLIC)) {
            return false;
        }

        // Check if it's an abstract class
        if (classElement.getModifiers().contains(Modifier.ABSTRACT)) {
            return false;
        }

        // Check inheritance: Class must be childclass as specified in
        // @Factory.type();
        TypeElement superClassElement = elementUtils.getTypeElement(classElement
                .getQualifiedName());
        if (superClassElement.getKind() == ElementKind.INTERFACE) {
            // Check interface implemented
            return true;
        } else {
            // Check subclassing
            TypeElement currentClass = classElement;
            while (true) {
                TypeMirror superClassType = currentClass.getSuperclass();

                if (superClassType.getKind() == TypeKind.NONE) {
                    return false;
                }

                if (superClassType.toString().equals(
                        classElement.getQualifiedName())) {
                    // Required super class found
                    break;
                }

                // Moving up in inheritance tree
                currentClass = (TypeElement) typeUtils
                        .asElement(superClassType);
            }
        }
        // Check if an empty public constructor is given
        for (Element enclosed : classElement.getEnclosedElements()) {
            if (enclosed.getKind() == ElementKind.CONSTRUCTOR) {
                ExecutableElement constructorElement = (ExecutableElement) enclosed;
                if (constructorElement.getParameters().size() == 0
                        && constructorElement.getModifiers().contains(
                        Modifier.PUBLIC)) {
                    // Found an empty constructor
                    return true;
                }
            }
        }
        return true;
    }

    private void studyJavaPoet(){
        List<MethodSpec> methodSpecs = new ArrayList<>();
        MethodSpec main = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .returns(String.class)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                .addStatement("int a = $N(33)","looper1")
                .addStatement("return $S","123")
                .build();
        methodSpecs.add(main);
        MethodSpec looper = MethodSpec.methodBuilder("looper")
                .addCode(""
                        + "int total = 0;\n"
                        + "for (int i = 0; i < 10; i++) {\n"
                        + "  total += i;\n"
                        + "}\n")
                .build();
        methodSpecs.add(looper);
        MethodSpec looper1 = MethodSpec.methodBuilder("looper1")
                .addModifiers(Modifier.PRIVATE,Modifier.FINAL,Modifier.STATIC)
                .returns(int.class)
                .addStatement("$T total = 0",int.class)
                .addParameter(int.class,"arg")
                .beginControlFlow("for(int i=0;i<10;i++)")
                .addStatement("total += i")
                .endControlFlow()
                .addStatement("return total")
                .build();
        methodSpecs.add(looper1);

//        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
//                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
//                .addMethods(methodSpecs)
//                .build();

        TypeSpec comparator = TypeSpec.anonymousClassBuilder("")
                .addSuperinterface(ParameterizedTypeName.get(Comparator.class, String.class))
                .addMethod(MethodSpec.methodBuilder("compare")
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(String.class, "a")
                        .addParameter(String.class, "b")
                        .returns(int.class)
                        .addStatement("return $N.length() - $N.length()", "a", "b")
                        .build())
                .build();

        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                .addMethod(MethodSpec.methodBuilder("sortByLength")
                        .addParameter(ParameterizedTypeName.get(List.class, String.class), "strings")
                        .addStatement("$T.sort($N, $L)", Collections.class, "strings", comparator)
                        .build())
                .build();


        JavaFile javaFile = JavaFile.builder("com.example.helloworld", helloWorld)
                .build();
        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
