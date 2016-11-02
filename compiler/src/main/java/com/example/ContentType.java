package com.example;

import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;

/**
 * Created by lijiangdong on 2016/11/1.
 */
public class ContentType {
    private TypeElement typeElement;
    private int resId;

    public ContentType(Element element) {
//        if (element.asType().getKind() != TypeKind.TYPEVAR){
//            throw new IllegalArgumentException(String.format("Only type can be annotated with @%s",ContentView.class.getSimpleName()));
//        }
        typeElement = (TypeElement)element;
        ContentView contentView = typeElement.getAnnotation(ContentView.class);
        this.resId = contentView.value();
        if (resId < 0){
            throw new IllegalArgumentException(
                    String.format("value() in %s for field %s is not valid !", ContentView.class.getSimpleName(),
                            typeElement.getSimpleName()));
        }

    }

    public Name getSimpleName(){
        return typeElement.getSimpleName();
    }

    public Name getQualifiedName(){
        return typeElement.getQualifiedName();
    }

    public int getResId() {
        return resId;
    }

}
