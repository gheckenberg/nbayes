package nbayes

import org.junit.{Assert, Test}

class TextProcessorTest {
  @Test
  def testSimple() {
    val words = TextProcessor.splitWords("Hello, World -- test");
    Assert.assertEquals(List("hello", "world", "test"), words)
  }

  @Test
  def testNumbers() {
    val words = TextProcessor.splitWords("4 57r1ng wi7h numb3rs");
    Assert.assertEquals(List("4", "57r1ng", "wi7h", "numb3rs"), words);
  }

  @Test
  def testWordCount() {
    val counts = TextProcessor.buildWordCountMap(List("a", "b", "c", "a", "c"));
    val expected = Map(
      "a" -> 2,
      "b" -> 1,
      "c" -> 2
    )
    Assert.assertEquals(expected, counts);
  }

  @Test
  def test2Gram() {
    val ngrams = TextProcessor.createNGrams(2, List("a", "b", "c", "d"))
    Assert.assertEquals(List(List("a", "b"), List("b", "c"), List("c", "d")), ngrams)
  }

  @Test
  def test3Gram() {
    val ngrams = TextProcessor.createNGrams(3, List("a", "b", "c", "d"))
    Assert.assertEquals(List(List("a", "b", "c"), List("b", "c", "d")), ngrams)
  }

  @Test
  def test4Gram() {
    val ngrams = TextProcessor.createNGrams(4, List("a", "b", "c", "d"))
    Assert.assertEquals(List(List("a", "b", "c", "d")), ngrams)
  }

  @Test
  def testStemmedWords() {
    val text = "runniNg is haRder than riding Bikes"
    val result = TextProcessor.splitAndStem(text);
    // harder -> hard ???
    val expected = List("run", "is", "harder", "than", "ride", "bike")
    Assert.assertEquals(expected, result);
  }
}