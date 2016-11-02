package com.example;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.type.TypeMirror;

/**
 * Created by lijiangdong on 2016/11/2.
 */
public class OnClickMethod {
    private ExecutableElement methodElement;
    private int[] resId;

    public OnClickMethod(Element element) {
//        if (element.asType().getKind() != TypeKind.){
//            throw new IllegalArgumentException(String.format("Only fields can be annotated with @%s",InjectViewField.class.getSimpleName()));
//        }
        methodElement = (ExecutableElement) element;
        OnClick onClick = methodElement.getAnnotation(OnClick.class);
        this.resId = onClick.value();
        if (resId == null){
            throw new IllegalArgumentException(
                    String.format("value() in %s for field %s is not valid !", OnClick.class.getSimpleName(),
                            methodElement.getSimpleName()));
        }

    }

    public Name getSimpleName(){
        return methodElement.getSimpleName();
    }

    public TypeMirror getFieldType(){
        return methodElement.asType();
    }

    public int[] getResId() {
        return resId;
    }
}
