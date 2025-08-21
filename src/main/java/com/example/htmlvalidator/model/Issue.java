package com.example.htmlvalidator.model;


public record Issue(Severity severity, String message, String selector) {

    @Override
    public String toString() {
        String where = (selector == null || selector.isEmpty()) ? "" : " [" + selector + "]";
        return severity + " " + message + where;
    }
}
