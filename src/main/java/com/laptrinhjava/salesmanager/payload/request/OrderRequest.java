package com.laptrinhjava.salesmanager.payload.request;

import javax.validation.constraints.NotBlank;
import java.util.Collection;

public class OrderRequest {
    @NotBlank
    private Collection<Long> products;

    public Collection<Long> getProducts() {
        return products;
    }

    public void setProducts(Collection<Long> products) {
        this.products = products;
    }
}
