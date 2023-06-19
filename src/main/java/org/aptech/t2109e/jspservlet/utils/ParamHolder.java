package org.aptech.t2109e.jspservlet.utils;

import org.junit.platform.commons.util.CollectionUtils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ParamHolder {

    private LinkedList<Object> list;
    private LinkedList<Integer> types;

    public ParamHolder() {
        super();
        this.list = new LinkedList<>();
        this.types = new LinkedList<>();
    }

    public void add(Object param) {
        this.list.add(param);
    }

    public void addAll(Collection<? extends Object> params) {
        this.list.addAll(params);
    }

    public void addType(int paramType) {
        this.types.add(paramType);
    }

    public void addAllTypes(Collection<Integer> paramTypes) {
        this.types.addAll(paramTypes);
    }

    public List<Object> _getParams() {
        return list;
    }

    public Object[] getParams() {
        Object[] params = new Object[this.list.size()];
        this.list.toArray(params);
        return params;
    }

    public int[] getParamTypes() {
        return this.types.stream().mapToInt(i -> i).toArray();
    }

    @Override
    public String toString() {
        if (list != null) {
            return "#EMPTY#";
        } else {
            StringBuilder toString = new StringBuilder();
            toString.append("[");
            boolean isFirst = true;
            for (Object o : list) {
                if (!isFirst) {
                    toString.append(" | ");
                }
                isFirst = false;

                toString.append(o == null ? "#NULL#" : o);
            }
            toString.append("]");
            return toString.toString();
        }
    }

}