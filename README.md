# Week5GroupWork

<details>
<summary><h2>Refactored or created Files</h2></summary>

<details>
  <summary>Shopping_advisor.puml</summary>
  
``` puml
@startuml
class ShoppingAdviserWidget {
  - String title

  + getAdvisedProducts() : List<ShoppingAdviserProduct>
}

class AmazonsChoiceAdviserWidget {
  - AmazonsChoiceServiceClient amazonsChoiceServiceClient
  - SearchServiceClient searchServiceClient
  + getAdvisedProducts() : List<ShoppingAdviserProduct>
}

class ShoppingContext {
  - SearchCategory searchCategory
  - String marketplaceId
  - String searchTerm
}

class ShoppingAdviserProduct {
  - String adviceLabel
  - Product product
}

class AmazonsChoiceServiceClient {
  + getChoiceProduct(searchTerm : String, marketplaceId : String) : AmazonChoiceProduct
}

class AmazonChoiceProduct {
  - String searchTerm
  - Product product
}

class SearchServiceClient {
  + getRelatedSearchTerms(searchTerm : String, searchCategory : SearchCategory) : List<String>
}

class EditorialServiceClient {
  + getEditorialRecommendedProducts(searchTerm : String, searchCategory : SearchCategory, marketplaceId : String) : List<EditorialRecommendedProduct>
}

class EditorialRecommendedProduct {
  - String recommendation
  - Product product
}

enum SearchCategory {
  BEAUTY, BOOKS, CLOTHING,
  ELECTRONICS, HEALTH,
  HOME_AND_GARDEN, PET_SUPPLIES, SPORTS, TOYS;
}

class ShoppingAdviserWidgetTestRenderer {
  + main(String[] args)
  + getRenderableContent(widget : AmazonsChoiceAdviserWidget) : String
}
class EditorialAdviserWidget{
+ getAdvisedProducts() : List<ShoppingAdviserProduct>
}

EditorialAdviserWidget *-- AmazonsChoiceAdviserWidget
EditorialAdviserWidget --|> ShoppingAdviserWidget
EditorialAdviserWidget *-- EditorialServiceClient

ShoppingAdviserWidget *-- ShoppingContext
ShoppingAdviserWidget <|-- AmazonsChoiceAdviserWidget
AmazonsChoiceAdviserWidget *-- SearchServiceClient
AmazonsChoiceAdviserWidget *-- AmazonsChoiceServiceClient
ShoppingAdviserWidget -. ShoppingAdviserProduct: references
EditorialServiceClient -. EditorialRecommendedProduct: references
AmazonsChoiceServiceClient -. AmazonChoiceProduct: references
ShoppingAdviserWidgetTestRenderer -. AmazonsChoiceAdviserWidget: references
ShoppingContext *-- SearchCategory
@enduml
```
</details>

<details>
<summary>EditorialAdviserWidget.java</summary>
  
``` java
package com.kenzie.groupwork.shoppingadvisor.widget;

import com.kenzie.groupwork.shoppingadvisor.client.EditorialServiceClient;
import com.kenzie.groupwork.shoppingadvisor.model.ShoppingAdviserProduct;
import com.kenzie.groupwork.shoppingadvisor.model.ShoppingContext;
import com.kenzie.groupwork.shoppingadvisor.resources.EditorialRecommendedProduct;
import com.kenzie.groupwork.shoppingadvisor.resources.Product;
import com.kenzie.groupwork.shoppingadvisor.resources.SearchCategory;

import java.util.ArrayList;
import java.util.List;


public class EditorialAdviserWidget extends ShoppingAdviserWidget{
    private EditorialServiceClient editorialServiceClient;
    public EditorialAdviserWidget(EditorialServiceClient editorialServiceClient,ShoppingContext shoppingContext) {
        super("Editorial recommendation", shoppingContext);
        this.editorialServiceClient = editorialServiceClient;
    }
    @Override
    public List<ShoppingAdviserProduct> getAdvisedProducts() {
        List<ShoppingAdviserProduct> advisedProducts = new ArrayList<>();

        // Get the necessary information from the ShoppingContext
        String searchTerm = getShoppingContext().getSearchTerm();
        SearchCategory searchCategory = getShoppingContext().getSearchCategory();
        String marketplaceId = getShoppingContext().getMarketplaceId();

        // Use the EditorialServiceClient to get editorial recommended products
        List<EditorialRecommendedProduct> editorialRecommendedProducts =
                editorialServiceClient.getEditorialRecommendedProducts(searchTerm, searchCategory, marketplaceId);

        // Convert EditorialRecommendedProducts to ShoppingAdviserProducts
        for (EditorialRecommendedProduct editorialProduct : editorialRecommendedProducts) {
            Product product = editorialProduct.getProduct(); // Get the Product instance
            ShoppingAdviserProduct shoppingAdviserProduct = new ShoppingAdviserProduct(
                    product.getTitle(),      // Use the appropriate method from the Product class
                    product.getListPrice(),  // Use the appropriate method from the Product class
                    editorialProduct.getRecommendation()
            );
            advisedProducts.add(shoppingAdviserProduct);
        }

        return advisedProducts;
    }
}
```
</details>

<details>
  <summary>EditorialServiceClient.java</summary>
  
``` java
package com.kenzie.groupwork.shoppingadvisor.client;

import com.kenzie.groupwork.shoppingadvisor.resources.EditorialRecommendedProduct;
import com.kenzie.groupwork.shoppingadvisor.resources.EditorialService;
import com.kenzie.groupwork.shoppingadvisor.resources.SearchCategory;

import java.util.List;

/**
 * Connects tot he EditorialService to provide recommendations from a related
 * editorial.
 */
public class EditorialServiceClient {

    private EditorialService editorialService;

    public EditorialServiceClient() {
        editorialService = new EditorialService();
    }

    /**
     * Retrieve recommendations from an editorial. A relevant editorial will be found, if one exists,
     * and the recommendations will be returned from it. The editorial must be relevant to the searchTerm
     * and further refined by SearchCategory. Recommended products not sold in the specified marketplace
     * will not be returned.
     *
     * @param searchTerm     used to search for relevant editorials
     * @param searchCategory acts as a filter to ensure the editorial is related to the search
     * @param marketplaceId  acts as a filter to ensure the recommended products are sold in the marketplace
     * @return a list of products recommended by a relevant editorial
     */
    public List<EditorialRecommendedProduct> getEditorialRecommendedProducts(
            String searchTerm,
            SearchCategory searchCategory,
            String marketplaceId) {
        return editorialService.getEditorialRecommendedProducts(searchTerm, searchCategory, marketplaceId);
    }
}
```
</details>

</details>
