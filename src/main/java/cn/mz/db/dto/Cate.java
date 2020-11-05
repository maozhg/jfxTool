package cn.mz.db.dto;

import javafx.beans.property.SimpleStringProperty;

/**
 * @version 1.0
 * @Description TODD
 * @Author wmazh
 * @Date 2020-11-03 0003 20:30
 */
public class Cate {
    private SimpleStringProperty id = new SimpleStringProperty();
    private SimpleStringProperty url = new SimpleStringProperty();
    private SimpleStringProperty shop_parent_sku = new SimpleStringProperty();

    public Cate(String id, String url, String shop_parent_sku) {
        setId(id);
        setUrl(url);
        setShop_parent_sku(shop_parent_sku);
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getUrl() {
        return url.get();
    }

    public SimpleStringProperty urlProperty() {
        return url;
    }

    public void setUrl(String url) {
        this.url.set(url);
    }

    public String getShop_parent_sku() {
        return shop_parent_sku.get();
    }

    public SimpleStringProperty shop_parent_skuProperty() {
        return shop_parent_sku;
    }

    public void setShop_parent_sku(String shop_parent_sku) {
        this.shop_parent_sku.set(shop_parent_sku);
    }
}
