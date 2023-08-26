# Week5GroupWork

<details>
<summary><h2>Refactored or created Files</h2></summary>

<details>
  <summary>shopping_adviser.puml</summary>
  
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

class EditorialAdviserWidget {
  getAdvisedProducts(): List<ShoppingAdviserProduct>
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
```
</details>

<details>
  <summary>EditorialServiceClient.java</summary>
  
``` java
package com.kenzie.groupwork.shoppingadvisor.client;

import com.kenzie.groupwork.shoppingadvisor.model.ShoppingContext;
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

<details>
  <summary>ShoppingAdviserWidgetTestRenderer.java</summary>
  
``` java
package com.kenzie.groupwork.shoppingadvisor;

import com.kenzie.groupwork.shoppingadvisor.client.AmazonSearchServiceClient;
import com.kenzie.groupwork.shoppingadvisor.client.AmazonsChoiceServiceClient;
import com.kenzie.groupwork.shoppingadvisor.model.ShoppingContext;
import com.kenzie.groupwork.shoppingadvisor.resources.SearchCategory;
import com.kenzie.groupwork.shoppingadvisor.widget.AmazonsChoiceAdviserWidget;
import com.kenzie.groupwork.shoppingadvisor.widget.ShoppingAdviserWidget;

public class ShoppingAdviserWidgetTestRenderer {

    /**
     * Returns the renderable content for a widget in String format.
     * @param widget the widget to render
     * @return A String containing the renderable content of a widget
     */
    public String getRenderableContent(ShoppingAdviserWidget widget) {
        return widget.getSimpleRendering();
    }

    public static void main(String[] args) {

        ShoppingAdviserWidgetTestRenderer renderer = new ShoppingAdviserWidgetTestRenderer();

        AmazonsChoiceAdviserWidget amazonsChoiceAdviserWidget = new AmazonsChoiceAdviserWidget(
            new ShoppingContext("grill", SearchCategory.HOME_AND_GARDEN, "ATVPDKIKX0DER"),
            new AmazonsChoiceServiceClient(), new AmazonSearchServiceClient());

        System.out.println(renderer.getRenderableContent(amazonsChoiceAdviserWidget));
    }
}
```
</details>


</details>
