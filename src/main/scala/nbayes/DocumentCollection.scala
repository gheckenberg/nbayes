package nbayes

import annotation.tailrec

class DocumentCollection(val numDocs: Int, val termFreq: List[(String, Int)]) {
  val numTerms = termFreq.foldLeft(0)((count, termFreq) => count + termFreq._2)
}

class DocumentCollectionBuilder(val ngram: Int) {
  private var termFreq = Map[String, Int]()
  private var numDocuments = 0

  def incrTerm(term: String) {
    termFreq = termFreq.updated(term, termFreq.getOrElse(term, 0) + 1)
  }

  def addDocument(text: String) {
    @tailrec
    def addNgram(wordList: List[String], ngram: Int) {
      if (ngram > 0){
        TextProcessor.createNGrams(ngram, wordList)
          .map(term => term.foldLeft(null.asInstanceOf[String]) ((a, b) => if (a != null) a + ":" + b else b ))
          .foreach(incrTerm(_));
        addNgram(wordList, ngram - 1)
      }
    }
    val wordList = TextProcessor.splitAndStem(text)
    numDocuments += 1
    addNgram(wordList, ngram)
  }

  def buildCollection() = {
    new DocumentCollection(numDocuments, termFreq.toList.sortBy(_._1))
  }
}
