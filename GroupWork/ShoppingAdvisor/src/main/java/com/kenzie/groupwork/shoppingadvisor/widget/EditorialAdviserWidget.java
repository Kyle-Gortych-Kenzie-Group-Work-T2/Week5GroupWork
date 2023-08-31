package com.kenzie.groupwork.shoppingadvisor.widget;

import com.kenzie.groupwork.shoppingadvisor.client.EditorialServiceClient;
import com.kenzie.groupwork.shoppingadvisor.model.ShoppingAdviserProduct;
import com.kenzie.groupwork.shoppingadvisor.model.ShoppingContext;
import com.kenzie.groupwork.shoppingadvisor.resources.EditorialRecommendedProduct;

import java.util.ArrayList;
import java.util.List;

public class EditorialAdviserWidget extends ShoppingAdviserWidget{
    private EditorialServiceClient editorialServiceClient;

    public EditorialAdviserWidget(EditorialServiceClient editorialServiceClient, ShoppingContext shoppingContext) {
        super("Editorial recommendation", shoppingContext);
        this.editorialServiceClient = editorialServiceClient;
    }

    @Override
    public List<ShoppingAdviserProduct> getAdvisedProducts() {
        ShoppingContext shoppingContext = getShoppingContext();

        List<EditorialRecommendedProduct> listOfProducts = editorialServiceClient.getEditorialRecommendedProducts(shoppingContext.getSearchTerm(), shoppingContext.getSearchCategory(), shoppingContext.getMarketplaceId());
        List<ShoppingAdviserProduct> convertedList = new ArrayList<>();

        for(EditorialRecommendedProduct editorial : listOfProducts) {
            convertedList.add(new ShoppingAdviserProduct(editorial.getRecommendation(), editorial.getProduct()));
        }

        return convertedList;
    }


}
