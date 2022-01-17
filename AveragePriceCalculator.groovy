import Product
import Category

class AveragePriceCalculator
{
  private static _margins = [:];
  private static Category[] _categories = [];
  private static Product[] _products = [];

  public static Map getAveragePrices(products, categories, margins)
  {
    
    _margins = margins;
    _categories = ConvertCategoryDataToCategoryList(categories);
    _products = ConvertProductDataToProductList(products);
    def groupNames = GetUniqueGroupNames(_products);

    def result = groupNames.collectEntries { groupName ->
      def prices = collectPricesFromGroup(groupName);
      def totalPrice = prices.inject(0) { sum, price -> sum += price }

      def averagePrice = (totalPrice / prices.size()).round(1);

      [(groupName):averagePrice]
    }
    return result;
  }

  private static GetUniqueGroupNames(products)
  {
    products.collect { p -> p.group }.unique { a, b -> a <=> b };
  }

  private static ConvertCategoryDataToCategoryList(categories)
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

  private static ConvertProductDataToProductList(products)
  {
    return products.collect { productData -> 
        def product = new Product();
        product.name = productData[0];
        product.group = productData[1];
        product.cost = productData[2];

        return product;
    }
  }



  def private static float[] collectPricesFromGroup(groupName)
  {
    def prices = _products.findAll(p -> p.group == groupName).collect { product -> 
      def markup = getMarkup(product);

      def price = product.cost * (1 + markup / 100);

      return price;
    }

    return prices;
  }

  def static float getMarkup(product)
  {
      def groupName = product.group;

      def category = _categories
        .findAll(c -> product.cost >= c.minimumCost)
        .find(c -> c.maximumCost == -1 || (product.cost < c.maximumCost));
      def categoryName = category.name;

      def markup = ConvertStringToMarkup(_margins[categoryName]);
      return markup;
  }

  def static float ConvertStringToMarkup(markupText)
  {
    if (markupText.contains("%")) {
      def numberText = markupText.replace("%", "");
      return Float.parseFloat(numberText);
    } else {
      def numberText = markupText;
      return Float.parseFloat(markupText) * 100;
    }
  }
}