package nbayes

import annotation.tailrec


object TextProcessor {

  def getStemmedWord(w: String) = {
    val s = new nbayes.util.Stemmer();
    s.add(w.toLowerCase.toCharArray, w.length());
    s.stem();
    s.toString;
  }

  // Break into lower case words.
  def splitWords(text: String) = {
    val wordList = List.newBuilder[String];
    val builder = new StringBuilder();
    for (c <- text.toCharArray)
      if (Character.isDigit(c) || Character.isLetter(c))
        builder.append(Character.toLowerCase(c))
      else {
        if (builder.size > 0)
          wordList += builder.toString()
        builder.clear()
      }
    if (builder.size > 0)
      wordList += builder.toString()
    wordList.result()
  }

  def splitAndStem(text : String) = {
    stemWords(splitWords(text))
  }

  def createAllNGramTerms(text: String, ngram : Int) = {
    @tailrec
    def getNgrams(wordList: List[String], ngram: Int, accum : List[String]) : List[String] = {
      if (ngram > 0)
        getNgrams(wordList, ngram - 1, createNGrams(ngram, wordList)
          .map(term => term.foldLeft(null.asInstanceOf[String]) ((a, b) => if (a != null) a + ":" + b else b ))
          .foldRight(accum) ((term, list) => term :: list))
      else
        accum
    }
    getNgrams(splitAndStem(text), ngram, Nil).sorted
  }

  def stemWords(words: List[String]) = {
    words.map(getStemmedWord(_))
  }

  def buildWordCountMap(words : List[String]) = {
    val wordFreqs = collection.mutable.HashMap[String, Int]()
    words.foreach(w => {
      wordFreqs(w) = if (wordFreqs.contains(w)) wordFreqs(w) + 1 else 1
    })
    wordFreqs.toMap
  }

  def createNGrams(n: Int, words: List[String]) = {
    @tailrec
    def construct(prefix: List[String], remaining: List[String],
                  retList: List[List[String]]) : List[List[String]] =
      remaining match {
        case x :: xs => {
          val ngram = prefix ::: List(x)
          construct(ngram.drop(1), xs, ngram :: retList)
        }
        case _ => retList
      }
    if (words.length < n) Nil
    else construct(words.take(n-1), words.drop(n - 1), Nil).reverse
  }

}
