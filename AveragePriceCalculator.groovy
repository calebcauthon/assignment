import Product;
import Category;
import PricingModel;

class AveragePriceCalculator
{
  public static Map getAveragePrices(productData, categoriesData, marginData)
  {
    def pricingModel = new PricingModel(productData, categoriesData, marginData);
    def groupNames = GetUniqueGroupNames(pricingModel.products);

    def averagePrices = groupNames.collectEntries { groupName ->
      def averagePrice = CalculateAveragePriceForGroup(groupName, pricingModel)
      [(groupName):averagePrice]
    }
    return averagePrices;
  }

  private static CalculateAveragePriceForGroup(groupName, pricingModel)
  {
    def prices = collectPricesFromGroup(pricingModel, groupName);
    def totalPrice = prices.inject(0) { sum, price -> sum += price }

    def averagePrice = (totalPrice / prices.size()).round(1);

    return averagePrice;
  }

  private static GetUniqueGroupNames(products)
  {
    products.collect { p -> p.group }.unique { a, b -> a <=> b };
  }



  def private static float[] collectPricesFromGroup(pricingModel, groupName)
  {
    def prices = pricingModel.products.findAll(p -> p.group == groupName).collect { product -> 
      def markup = getMarkup(product, pricingModel);

      def price = product.cost * (1 + markup / 100);

      return price;
    }

    return prices;
  }

  def static float getMarkup(product, pricingModel)
  {
      def groupName = product.group;

      def category = pricingModel.categories
        .findAll(c -> product.cost >= c.minimumCost)
        .find(c -> c.maximumCost == -1 || (product.cost < c.maximumCost));
      def categoryName = category.name;

      def markup = ConvertStringToMarkup(pricingModel.margins[categoryName]);
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