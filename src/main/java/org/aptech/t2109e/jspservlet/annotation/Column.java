package org.aptech.t2109e.jspservlet.annotation;

import org.aptech.t2109e.jspservlet.common.SqlDataType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
    String name();
    SqlDataType dataType();
}
