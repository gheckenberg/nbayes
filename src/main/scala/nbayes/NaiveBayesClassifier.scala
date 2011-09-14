package nbayes

class NaiveBayesClassifier (val classes : List[(String, DocumentCollection)] = Nil) {

  def addCollection(label: String, collection: DocumentCollection) =
    new NaiveBayesClassifier((label, collection) :: classes)

  def classify(text: String) =
    classes.maxBy(_._2.calcLogLikelihood(text))._1
}