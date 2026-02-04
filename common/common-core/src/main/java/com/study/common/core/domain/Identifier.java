package com.study.common.core.domain;

import java.io.Serializable;

public interface Identifier<T> extends Serializable {
    T getValue();
}
