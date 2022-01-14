class AveragePriceCalculator
{
  public static Map getAveragePrices(products, b, margins)
  {
    def product = products[0]
    def name = product[0]
    def group = product[1]
    def cost = product[2]

    def margin = margins["the-category"].replace("%", "");
    def markup = Float.parseFloat(margin);
    def price = cost * (1 + markup / 100);

    def result = [:]

    result[group] = price;

    return result;
  }
}