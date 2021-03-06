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

    @Test
    void test__twoProducts__differentMarkups__productGroupAverage() {
      products = [
        ["prod-1", "the-group", 10],
        ["prod-2", "the-group", 20]
      ];

      categories = [
        ["cat-1", 0, 12],
        ["cat-2", 15, 30]
      ];

      margins["cat-1"] = "10%";
      margins["cat-2"] = "50%";

      def expectedResult = [
        "the-group" : 20.5, // (11 + 30) / 2
      ]

      def actualResult = AveragePriceCalculator.getAveragePrices(products, categories, margins)

      assert expectedResult == actualResult;
    }

    @Test
    void test__twoProducts__differentGroups__productGroupAverage() {
      products = [
        ["prod-1", "group-1", 10],
        ["prod-2", "group-2", 20]
      ];

      categories = [
        ["cat-1", 0, 9999],
      ];

      margins["cat-1"] = "10%";

      def expectedResult = [
        "group-1" : 11,
        "group-2" : 22,
      ]

      def actualResult = AveragePriceCalculator.getAveragePrices(products, categories, margins)

      assert expectedResult == actualResult;
    }

    @Test
    void test__twoProducts__differentMarkupTypes__productGroupAverage() {
      products = [
        ["prod-1", "the-group", 10],
        ["prod-2", "the-group", 20]
      ];

      categories = [
        ["cat-1", 0, 12],
        ["cat-2", 15, 30]
      ];

      margins["cat-1"] = "10%";
      margins["cat-2"] = ".5";

      def expectedResult = [
        "the-group" : 20.5, // (11 + 30) / 2
      ]

      def actualResult = AveragePriceCalculator.getAveragePrices(products, categories, margins)

      assert expectedResult == actualResult;
    }

    @Test
    void test__twoProducts__categoryFloorInclusive() {
      products = [
        ["prod-1", "the-group", 10],
      ];

      categories = [
        ["cat-1", 10, 20],
      ];

      margins["cat-1"] = "10%";

      def expectedResult = [
        "the-group" : 11
      ]

      def actualResult = AveragePriceCalculator.getAveragePrices(products, categories, margins)

      assert expectedResult == actualResult;
    }

    @Test
    void test__twoProducts__categoryCeilingExclusive() {
      products = [
        ["prod-1", "the-group", 20],
      ];

      categories = [
        ["cat-1", 10, 20],
        ["cat-2", 20, 21],
      ];

      margins["cat-1"] = "10%";
      margins["cat-2"] = "50%";

      def expectedResult = [
        "the-group" : 30
      ]

      def actualResult = AveragePriceCalculator.getAveragePrices(products, categories, margins)

      assert expectedResult == actualResult;
    }

    @Test
    void test__NullCeiling__TreatedAsInfinity() {
      products = [
        ["prod-1", "the-group", 20],
      ];

      categories = [
        ["cat-1", 10, null],
      ];

      margins["cat-1"] = "10%";

      def expectedResult = [
        "the-group" : 22
      ]

      def actualResult = AveragePriceCalculator.getAveragePrices(products, categories, margins)

      assert expectedResult == actualResult;
    }

    @Test
    void test__roundToCents() {
      products.add(
        ["the-product-2", "the-group", 20.5]
      );

      def expectedResult = [
        "the-group" : 90.4
      ]

      def actualResult = AveragePriceCalculator.getAveragePrices(products, categories, margins)

      assert expectedResult == actualResult;
    }
}
