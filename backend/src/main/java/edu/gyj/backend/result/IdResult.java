package edu.gyj.backend.result;

public class IdResult {
    private String id;

    public IdResult() {
    }

    public IdResult(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Id{" +
                "id='" + id + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
