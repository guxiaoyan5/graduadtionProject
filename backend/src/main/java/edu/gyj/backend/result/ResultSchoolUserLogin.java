package edu.gyj.backend.result;

public class ResultSchoolUserLogin {
    private int code;
    private String message;
    private String token;
    public ResultSchoolUserLogin() {
    }

    public ResultSchoolUserLogin(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultSchoolUserLogin(int code, String message, String token) {
        this.code = code;
        this.message = message;
        this.token = token;
    }

    @Override
    public String toString() {
        return "ResultSchoolUserLogin{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
