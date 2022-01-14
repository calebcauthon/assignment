class AveragePriceCalculator
{
  public static Map getAveragePrices(products, categories, margins)
  {
    def product = products[0]
    def name = product[0]
    def group = product[1]
    def cost = product[2]

    def category = categories[0];
    def categoryName = category[0];

    def margin = margins[categoryName].replace("%", "");
    def markup = Float.parseFloat(margin);
    def price = cost * (1 + markup / 100);

    def result = [:]

    result[group] = price;

    return result;
  }
}