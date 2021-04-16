package edu.gyj.backend.result;

public class Result {
    private int code;
    private String message;
    private String token;
    private Object data;
    public Result() {
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(int code, String message, String token, Object data) {
        this.code = code;
        this.message = message;
        this.token = token;
        this.data = data;
    }

    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(int code, String message, String token) {
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
                ", data=" + data +
                '}';
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
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
