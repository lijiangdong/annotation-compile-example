package com.example;

import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by lijiangdong on 2016/11/1.
 */
public class InjectViewField {
    private VariableElement variableElement;
    private int resId;

    public InjectViewField(Element element) {
//        if (element.asType().getKind() != TypeKind.){
//            throw new IllegalArgumentException(String.format("Only fields can be annotated with @%s",InjectViewField.class.getSimpleName()));
//        }
        variableElement = (VariableElement) element;
        Inject inject = variableElement.getAnnotation(Inject.class);
        this.resId = inject.value();
        if (resId < 0){
            throw new IllegalArgumentException(
                    String.format("value() in %s for field %s is not valid !", Inject.class.getSimpleName(),
                            variableElement.getSimpleName()));
        }

    }

    public Name getSimpleName(){
        return variableElement.getSimpleName();
    }

    public TypeMirror getFieldType(){
        return variableElement.asType();
    }

    public int getResId() {
        return resId;
    }
}
