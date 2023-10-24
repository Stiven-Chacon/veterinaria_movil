package com.example.animalagro.data;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class LoginResponse implements Parcelable {
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
    private List<Integer> groups;

    protected LoginResponse(Parcel in) {
        id = in.readInt();
        username = in.readString();
        password = in.readString();
        first_name = in.readString();
        last_name = in.readString();
        email = in.readString();
        userNoDoc = in.readString();
        userTelefono = in.readString();
        userFoto = in.readString();
        success = in.readByte() != 0;
        jwt = in.readString();
        // Leer la lista de grupos
        in.readList(groups, Integer.class.getClassLoader());
        // Puedes implementar la lectura de 'error' si es necesario
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(first_name);
        dest.writeString(last_name);
        dest.writeString(email);
        dest.writeString(userNoDoc);
        dest.writeString(userTelefono);
        dest.writeString(userFoto);
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeString(jwt);
        // Escribir la lista de grupos
        dest.writeList(groups);
        // Puedes implementar la escritura de 'error' si es necesario
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LoginResponse> CREATOR = new Creator<LoginResponse>() {
        @Override
        public LoginResponse createFromParcel(Parcel in) {
            return new LoginResponse(in);
        }

        @Override
        public LoginResponse[] newArray(int size) {
            return new LoginResponse[size];
        }
    };

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

    public List<Integer> getGroups() {
        return groups;
    }

    public void setGroups(List<Integer> groups) {
        this.groups = groups;
    }
}
