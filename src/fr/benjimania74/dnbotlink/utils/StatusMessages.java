package fr.benjimania74.dnbotlink.utils;

public enum StatusMessages {
    EXIST("SERVICE is already existing"),

    NOT_EXISTING("SERVICE is not a Service"),
    ALREADY_RUNNING("SERVICE is Already Running"),
    NOT_RUNNING("SERVICE is not Running"),
    NOT_SERVER("SERVICE is not a Server"),
    NOT_PROXY("SERVICE is not a Proxy"),
    NOW_STARTED("SERVICE is now Started"),
    NOW_STARTING("SERVICE is now Starting"),
    NOW_STOPPED("SERVICE is now Stopped"),
    NOW_STOPPING("SERVICE is now Stopping"),
    NO_SERVICE_RUNNING("There is no Service Running"),
    NO_PROXY_STARTED("There is no Proxy Started"),
    DOUBLE_SERVICE("SERVICE is a Server and a Proxy"),
    INCORRECT_TYPE("Incorrect Service Type"),
    DYNAMIC_SERVICE("SERVICE is a dynamic service, please use 'COMMAND dynamic' command");

    private final String message;
    StatusMessages(String message){
        this.message = message;
    }

    public String getMessage() {return message;}
}