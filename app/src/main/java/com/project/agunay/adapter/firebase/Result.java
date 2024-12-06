package com.project.agunay.adapter.firebase;

public class Result<T> {
    private T data;
    private Exception error;

    public Result(T data) {
        this.data = data;
    }

    public Result(Exception error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public Exception getError() {
        return error;
    }
}
