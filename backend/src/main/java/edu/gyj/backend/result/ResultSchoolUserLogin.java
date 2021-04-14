package edu.gyj.backend.result;

public class ResultSchoolUserLogin {
    private int code;
    private String message;
    public ResultSchoolUserLogin() {
    }

    public ResultSchoolUserLogin(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResultSchoolUserLogin{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
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
