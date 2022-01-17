import Product;
import Category;
import Group;
import PricingModel;

class AveragePriceCalculator
{
  public static Map getAveragePrices(productData, categoriesData, marginData)
  {
    def pricingModel = new PricingModel(productData, categoriesData, marginData);
    def groups = pricingModel.products.collect { p -> p.group }.unique { a, b -> a.name <=> b.name };

    def averagePrices = groups.collectEntries { group ->
      def averagePrice = CalculateAveragePriceForGroup(group, pricingModel)
      [(group.name):averagePrice]
    }
    return averagePrices;
  }

  private static CalculateAveragePriceForGroup(group, pricingModel)
  {
    def prices = collectPricesFromGroup(pricingModel, group);
    def totalPrice = prices.inject(0) { sum, price -> sum += price }

    def averagePrice = (totalPrice / prices.size()).round(1);

    return averagePrice;
  }

  def private static float[] collectPricesFromGroup(pricingModel, group)
  {
    def prices = pricingModel.products.findAll(p -> p.group.name == group.name).collect { product -> 
      def markup = pricingModel.getMarkup(product);
      def price = product.cost * (1 + markup / 100);

      return price;
    }

    return prices;
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