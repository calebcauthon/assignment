class AveragePriceCalculator
{
  public static Map getAveragePrices(products, categories, margins)
  {
    def result = [:]
    def prices = products.collect { product -> 
      def name = product[0]
      def group = product[1]
      def cost = product[2]

      def category = categories[0];
      def categoryName = category[0];

      def margin = margins[categoryName].replace("%", "");
      def markup = Float.parseFloat(margin);
      def price = cost * (1 + markup / 100);

      return price;
    }

    def totalPrice = prices.inject(0) { sum, price -> sum += price }
    def group = products[0][1];
    result[group] = totalPrice / prices.size();

    return result;
  }
}