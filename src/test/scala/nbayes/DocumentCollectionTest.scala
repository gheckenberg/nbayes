package nbayes

import org.junit.{Assert, Test}

class DocumentCollectionTest {
  @Test
  def testCreateCollection() {
    val collection = new DocumentCollection(2)
      .addDocument("a simple document")
      .addDocument("another simple Document")

    Assert.assertEquals(2, collection.numDocs)
    Assert.assertEquals(10, collection.numTerms)

    println(collection.termFreq)
  }
}