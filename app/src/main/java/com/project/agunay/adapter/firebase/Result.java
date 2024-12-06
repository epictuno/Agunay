package com.project.agunay.adapter.firebase;

import com.project.agunay.domain.ShopItem;
import com.project.agunay.domain.User;

import java.util.List;

public abstract class Result {
    public static class GeneralSuccess extends Result {
        private final boolean success;

        public GeneralSuccess(boolean success) {
            this.success = success;
        }

        public boolean isSuccess() {
            return success;
        }
    }

    public static class Error extends Result {
        private final Exception exception;

        public Error(Exception exception) {
            this.exception = exception;
        }

        public Exception getException() {
            return exception;
        }
    }

    public static class Loading extends Result {}

    public static class LoginSuccess extends Result {
        private final User user;

        public LoginSuccess(User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }

    public static class ImageSuccess extends Result {
        private final byte[] bytes;

        public ImageSuccess(byte[] bytes) {
            this.bytes = bytes;
        }

        public byte[] getBytes() {
            return bytes;
        }
    }

    public static class ShopSuccess extends Result {
        private final List<ShopItem> items;

        public ShopSuccess(List<ShopItem> items) {
            this.items = items;
        }

        public List<ShopItem> getItems() {
            return items;
        }
    }

    public static class BuySuccess extends Result {
        private final boolean success;

        public BuySuccess(boolean success) {
            this.success = success;
        }

        public boolean isSuccess() {
            return success;
        }
    }
}
