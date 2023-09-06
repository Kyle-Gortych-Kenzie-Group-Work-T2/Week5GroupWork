<div align="right">
 
![week 2](https://img.shields.io/github/actions/workflow/status/Kyle-Gortych-Kenzie-Group-Work-T2/Week5GroupWork/main.yml?label=main) ![week 2](https://img.shields.io/github/actions/workflow/status/Kyle-Gortych-Kenzie-Group-Work-T2/Week5GroupWork/original.yml?label=original)

</div>

# Week 5 Group Project
descript

## Changes
<details>
<summary>Diff with original branch</summary>

<details>
<summary>ShoppingAdviserWidgetTestRenderer.java</summary>
 
```diff
diff --git a/GroupWork/ShoppingAdvisor/src/main/java/com/kenzie/groupwork/shoppingadvisor/ShoppingAdviserWidgetTestRenderer.java b/GroupWork/ShoppingAdvisor/src/main/java/com/kenzie/groupwork/shoppingadvisor/ShoppingAdviserWidgetTestRenderer.java
index 453575a..a74d0aa 100644
--- a/GroupWork/ShoppingAdvisor/src/main/java/com/kenzie/groupwork/shoppingadvisor/ShoppingAdviserWidgetTestRenderer.java
+++ b/GroupWork/ShoppingAdvisor/src/main/java/com/kenzie/groupwork/shoppingadvisor/ShoppingAdviserWidgetTestRenderer.java
@@ -5,7 +5,6 @@ import com.kenzie.groupwork.shoppingadvisor.client.AmazonsChoiceServiceClient;
 import com.kenzie.groupwork.shoppingadvisor.model.ShoppingContext;
 import com.kenzie.groupwork.shoppingadvisor.resources.SearchCategory;
 import com.kenzie.groupwork.shoppingadvisor.widget.AmazonsChoiceAdviserWidget;
-import com.kenzie.groupwork.shoppingadvisor.widget.ShoppingAdviserWidget;
 
 public class ShoppingAdviserWidgetTestRenderer {
 
@@ -14,7 +13,7 @@ public class ShoppingAdviserWidgetTestRenderer {
      * @param widget the widget to render
      * @return A String containing the renderable content of a widget
      */
-    public String getRenderableContent(ShoppingAdviserWidget widget) {
+    public String getRenderableContent(AmazonsChoiceAdviserWidget widget) {
         return widget.getSimpleRendering();
     }
```
</details>

<details>
<summary>EditorialServiceClient.java</summary>
 
```diff
diff --git a/GroupWork/ShoppingAdvisor/src/main/java/com/kenzie/groupwork/shoppingadvisor/client/EditorialServiceClient.java b/GroupWork/ShoppingAdvisor/src/main/java/com/kenzie/groupwork/shoppingadvisor/client/EditorialServiceClient.java
index 9332e68..e8f712a 100644
--- a/GroupWork/ShoppingAdvisor/src/main/java/com/kenzie/groupwork/shoppingadvisor/client/EditorialServiceClient.java
+++ b/GroupWork/ShoppingAdvisor/src/main/java/com/kenzie/groupwork/shoppingadvisor/client/EditorialServiceClient.java
@@ -1,6 +1,5 @@
 package com.kenzie.groupwork.shoppingadvisor.client;
 
-import com.kenzie.groupwork.shoppingadvisor.model.ShoppingContext;
 import com.kenzie.groupwork.shoppingadvisor.resources.EditorialRecommendedProduct;
 import com.kenzie.groupwork.shoppingadvisor.resources.EditorialService;
 import com.kenzie.groupwork.shoppingadvisor.resources.SearchCategory;
```
</details>

<details>
<summary>shopping_adviser.puml</summary>
 
```diff
diff --git a/GroupWork/ShoppingAdvisor/src/main/java/com/kenzie/groupwork/shoppingadvisor/shopping_adviser.puml b/GroupWork/ShoppingAdvisor/src/main/java/com/kenzie/groupwork/shoppingadvisor/shopping_adviser.puml
index 198956e..7172eb8 100644
--- a/GroupWork/ShoppingAdvisor/src/main/java/com/kenzie/groupwork/shoppingadvisor/shopping_adviser.puml
+++ b/GroupWork/ShoppingAdvisor/src/main/java/com/kenzie/groupwork/shoppingadvisor/shopping_adviser.puml
@@ -55,14 +55,6 @@ class ShoppingAdviserWidgetTestRenderer {
   + getRenderableContent(widget : AmazonsChoiceAdviserWidget) : String
 }
 
-class EditorialAdviserWidget {
-  getAdvisedProducts(): List<ShoppingAdviserProduct>
-}
-
-EditorialAdviserWidget *-- AmazonsChoiceAdviserWidget
-EditorialAdviserWidget --|> ShoppingAdviserWidget
-EditorialAdviserWidget *-- EditorialServiceClient
-
 ShoppingAdviserWidget *-- ShoppingContext
 ShoppingAdviserWidget <|-- AmazonsChoiceAdviserWidget
 AmazonsChoiceAdviserWidget *-- SearchServiceClient
```
</details>

<details>
<summary>EditorialAdviserWidget.java</summary>
 
```diff
diff --git a/GroupWork/ShoppingAdvisor/src/main/java/com/kenzie/groupwork/shoppingadvisor/widget/EditorialAdviserWidget.java b/GroupWork/ShoppingAdvisor/src/main/java/com/kenzie/groupwork/shoppingadvisor/widget/EditorialAdviserWidget.java
deleted file mode 100644
index d091a7f..0000000
--- a/GroupWork/ShoppingAdvisor/src/main/java/com/kenzie/groupwork/shoppingadvisor/widget/EditorialAdviserWidget.java
+++ /dev/null
@@ -1,34 +0,0 @@
-package com.kenzie.groupwork.shoppingadvisor.widget;
-
-import com.kenzie.groupwork.shoppingadvisor.client.EditorialServiceClient;
-import com.kenzie.groupwork.shoppingadvisor.model.ShoppingAdviserProduct;
-import com.kenzie.groupwork.shoppingadvisor.model.ShoppingContext;
-import com.kenzie.groupwork.shoppingadvisor.resources.EditorialRecommendedProduct;
-
-import java.util.ArrayList;
-import java.util.List;
-
-public class EditorialAdviserWidget extends ShoppingAdviserWidget{
-    private EditorialServiceClient editorialServiceClient;
-
-    public EditorialAdviserWidget(EditorialServiceClient editorialServiceClient, ShoppingContext shoppingContext) {
-        super("Editorial recommendation", shoppingContext);
-        this.editorialServiceClient = editorialServiceClient;
-    }
-
-    @Override
-    public List<ShoppingAdviserProduct> getAdvisedProducts() {
-        ShoppingContext shoppingContext = getShoppingContext();
-
-        List<EditorialRecommendedProduct> listOfProducts = editorialServiceClient.getEditorialRecommendedProducts(shoppingContext.getSearchTerm(), shoppingContext.getSearchCategory(), shoppingContext.getMarketplaceId());
-        List<ShoppingAdviserProduct> convertedList = new ArrayList<>();
-
-        for(EditorialRecommendedProduct editorial : listOfProducts) {
-            convertedList.add(new ShoppingAdviserProduct(editorial.getRecommendation(), editorial.getProduct()));
-        }
-
-        return convertedList;
-    }
-
-
-}
```
</details>

</details>

<div align="center">
 
### :hammer_and_wrench: Tools :

| Version Control | Build System | Languages |
| --------------- | ------------ | --------- |
| <img src="https://img.shields.io/badge/Git-white?style=plastic&logo=git&logoColor=red" title="Git" alt="Git" height="30"/> | <img src="https://img.shields.io/badge/Gradle-white?style=plastic&logo=gradle&logoColor=black" title="gradle" alt="gradle" height="30"/> | <img src="https://custom-icon-badges.demolab.com/badge/Java-white.svg?&sytle=plastic&logo=java" title="Java" alt="Java" height="30"/> |
</div>
<br>

### Gradle Commands

```console
./gradlew groupwork-shoppingadvisor-test
```
<br>

<a href="your-gmail-link?">:mailbox:</a> How to reach the maintainer
