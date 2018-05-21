package org.insbaixcamp.proyectofinal;


public class ToDo {
    private String user;
    private String message;

    public ToDo(){

    }

    public ToDo(String user, String message){
        this.user = user;
        this.message = message;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getUser() {
        return user;
    }
}
