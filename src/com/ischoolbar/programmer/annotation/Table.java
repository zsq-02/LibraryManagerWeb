package com.ischoolbar.programmer.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ���ݿ��ע����
 * @author llq
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
	String prefix() default "";//����ǰ׺
	String tableName();//����
	String sufix() default "";//������׺
}
