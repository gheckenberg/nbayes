package nbayes

import org.junit.{Assert, Test}

class DocumentCollectionTest {
  @Test
  def testCreateCollection() {
    val builder = new DocumentCollectionBuilder(2)

    builder.addDocument("a simple document")
    builder.addDocument("another simple Document")

    val collection = builder.buildCollection()

    Assert.assertEquals(2, collection.numDocs)
    Assert.assertEquals(10, collection.numTerms)

    println(collection.termFreq)
  }


}