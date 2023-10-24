package com.example.animalagro.data;

public class UpdateUserRequest {
    private int id;
    private String username;
    private String password;
    private String first_name;
    private String last_name;
    private String email;
    private String userNoDoc;
    private String userTelefono;
    private String userFoto;
    private boolean success;
    private String jwt;
    private Object error;

    public UpdateUserRequest(int id, String username, String password, String first_name, String last_name, String email, String userNoDoc, String userTelefono, String userFoto, boolean success, String jwt, Object error) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.userNoDoc = userNoDoc;
        this.userTelefono = userTelefono;
        this.userFoto = userFoto;
        this.success = success;
        this.jwt = jwt;
        this.error = error;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserNoDoc() {
        return userNoDoc;
    }

    public void setUserNoDoc(String userNoDoc) {
        this.userNoDoc = userNoDoc;
    }

    public String getUserTelefono() {
        return userTelefono;
    }

    public void setUserTelefono(String userTelefono) {
        this.userTelefono = userTelefono;
    }

    public String getUserFoto() {
        return userFoto;
    }

    public void setUserFoto(String userFoto) {
        this.userFoto = userFoto;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }
}