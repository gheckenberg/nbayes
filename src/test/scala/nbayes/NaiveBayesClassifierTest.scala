package nbayes

import org.junit.{Assert, Test}

class NaiveBayesClassifierTest {

  @Test
  def testClassify() {
    val firstCollection = new DocumentCollection()
      .addDocument("a first test document")
      .addDocument("a test document in the first collection")

    val secondCollection = new DocumentCollection()
      .addDocument("junk that does not interest you")
      .addDocument("A document in a collection")

    val classifier = new NaiveBayesClassifier()
      .addCollection("first", firstCollection)
      .addCollection("second", secondCollection)

    Assert.assertEquals("first", classifier.classify("a test set"))
    Assert.assertEquals("first", classifier.classify("plural documents for collections"))
    Assert.assertEquals("second", classifier.classify("uninteresting junk"))
  }

}