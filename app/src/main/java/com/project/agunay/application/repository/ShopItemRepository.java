package com.project.agunay.application.repository;

import com.project.agunay.adapter.firebase.ErrorCallback;
import com.project.agunay.adapter.firebase.SuccessCallback;
import com.project.agunay.domain.ShopItem;

import java.util.List;

public interface ShopItemRepository {
    void getShopItemById(String id, SuccessCallback<ShopItem> callback, ErrorCallback callError);
    void getAllShopItems(SuccessCallback<List<ShopItem>> callback, ErrorCallback callError);
}
