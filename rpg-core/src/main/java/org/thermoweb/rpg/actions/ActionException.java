package org.thermoweb.rpg.actions;

public class ActionException extends Exception {

    public ActionException(String message) {
        super(message);
    }
    public ActionException(String message, Throwable e) {
        super(message, e);
    }
}
