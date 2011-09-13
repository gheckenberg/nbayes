package nbayes

class Document (text : String) {
	var wordCounts =
    TextProcessor.buildWordCountMap(
      TextProcessor.splitWords(text));
  println(wordCounts)
}


