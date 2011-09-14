package nbayes

import annotation.tailrec
import scala.math.log

class DocumentCollection(val numDocs: Int, val ngrams: Int, val termFreq: Map[String, Int]) {

  def this(ngrams: Int = 1) = this(0, ngrams, Map())

  def addDocument(text: String) =
    new DocumentCollection(numDocs + 1, ngrams,
      TextProcessor.createAllNGramTerms(text, ngrams)
        .foldLeft(termFreq) ((m, s) => m.updated(s, m.getOrElse(s,0) + 1)))

  def numTerms =
    termFreq.foldLeft(0)((count, entry) => count + entry._2)

  // calculates log likelihood, ignores prior denominator with total number of documents.
  def calcLogLikelihood(text: String) = {
    @tailrec
    def calcTermLikelihood(terms: List[String], accum: Double = 0) : Double =  terms match {
      case x :: xs => calcTermLikelihood(xs, accum + log(termFreq.getOrElse(x, 0) + 1))
      case Nil => accum
    }
    val terms = TextProcessor.createAllNGramTerms(text, ngrams)
    log(numDocs + 1) + calcTermLikelihood(terms) - terms.length * log(numTerms + 1)
  }
}

