import org.junit.Before;
import org.junit.Test;

import AveragePriceCalculator;

class JUnit4ExampleTests {
    private products = []
    private categories = []
    private margins = [:]

    @Before
    void Setup()
    {
      products.add(
        ["the-product", "the-group", 100]
      );

      categories.add(
        ["the-category", 0, 999]
      );


      margins["the-category"] = "50%";
    }

    @Test
    void test__productGroupAverage() {
        def expectedResult = [
          "the-group" : 150,
        ]

        def actualResult = AveragePriceCalculator.getAveragePrices(products, categories, margins)

        assert expectedResult == actualResult;
    }

    @Test
    void test__twoProducts__productGroupAverage() {
      products.add(
        ["the-product-2", "the-group", 20]
      );
        def expectedResult = [
          "the-group" : 90, // (150 + 30) / 2
        ]

        def actualResult = AveragePriceCalculator.getAveragePrices(products, categories, margins)

        assert expectedResult == actualResult;
    }
}
