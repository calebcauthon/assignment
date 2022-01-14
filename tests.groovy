import org.junit.Test

import AveragePriceCalculator;

class JUnit4ExampleTests {
    @Test
    void test__productGroupAverage() {
        def products = [
          ["the-product", "the-group", 100]
        ]

        def categories = [
            ["the-category", 0, 999],
        ]

        def margins = [
          "the-category" : "50%"
        ]

        def expectedResult = [
          "the-group" : 150,
        ]

        def actualResult = AveragePriceCalculator.getAveragePrices(products, categories, margins)

        assert expectedResult == actualResult;
    }
}
