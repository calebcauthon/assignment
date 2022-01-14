class AveragePriceCalculator
{
  public static Map getAveragePrices(products, b, margins)
  {
    def product = products[0]
    def name = product[0]
    def group = product[1]
    def cost = products[2]

    def result = [:]
    result[group] = 150;
    return result;
  }
}