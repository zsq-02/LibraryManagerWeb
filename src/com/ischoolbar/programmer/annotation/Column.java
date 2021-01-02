package com.ischoolbar.programmer.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ���ݿ���ע����
 * @author llq
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	String name();
	boolean autoIncrease() default false;
	boolean primaryKey() default false;
	boolean updateEnable() default true;//�ɸ��£�Ĭ����true
	boolean isForeignEntity() default false;//�Ƿ����Զ������
	boolean autoCreateTime() default false;//�Զ�����ʱ��
}
