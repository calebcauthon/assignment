import Product
import Category

class AveragePriceCalculator
{
  private static _margins = [:];
  private static Category[] _categories = [];

  private static ConvertCategoryDataToCategoryList(categories)
  {
    return categories.collect { data -> 
      def category = new Category();
      category.name = data[0];
      category.minimumCost = data[1];
      category.maximumCost = data[2];
      return category;
    }
  }

  public static Map getAveragePrices(products, categories, margins)
  {
    
    _margins = margins;
    _categories = ConvertCategoryDataToCategoryList(categories);

    def _products = products.collect { productData -> 
        def product = new Product();
        product.name = productData[0];
        product.group = productData[1];
        product.cost = productData[2];

        return product;
    }

    def groupNames = _products.collect { p -> p.group }.unique { a, b -> a <=> b };

    def result = groupNames.collectEntries { groupName ->
      def prices = _products.findAll(p -> p.group == groupName).collect { product -> 
        def markup = getMarkup(product);

        def price = product.cost * (1 + markup / 100);

        return price;
      }

      def totalPrice = prices.inject(0) { sum, price -> sum += price }
      def averagePrice = totalPrice / prices.size();

      [(groupName):averagePrice]
    }
    return result;
  }

  def static float getMarkup(product)
  {
      def groupName = product.group;

      def category = _categories.find(c -> product.cost > c.minimumCost && product.cost < c.maximumCost);
      def categoryName = category.name;

      def margin = _margins[categoryName].replace("%", "");
      def markup = Float.parseFloat(margin);
      return markup;
  }
}