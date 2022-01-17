class PricingModel
{
  public Product[] products;
  public Category[] categories;
  public Map margins;

  PricingModel(productData, categoryData, marginData)
  {
    products = ConvertProductDataToProductList(productData);
    categories = ConvertCategoryDataToCategoryList(categoryData);
    margins = marginData;
  }

  def groups()
  {
    products.collect { p -> p.group }.unique { a, b -> a <=> b };
  }

  private ConvertCategoryDataToCategoryList(categories)
  {
    return categories.collect { data -> 
      def category = new Category();
      category.name = data[0];
      category.minimumCost = data[1];
      if (data[2] != null)
      {
        category.maximumCost = data[2];
      }
      else
      {
        category.maximumCost = -1;
      }
      return category;
    }
  }

  private ConvertProductDataToProductList(products)
  {
    return products.collect { productData -> 
        def product = new Product();
        product.name = productData[0];
        product.group = productData[1];
        product.cost = productData[2];

        return product;
    }
  }
}