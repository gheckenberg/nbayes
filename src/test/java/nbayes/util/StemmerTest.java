package nbayes.util;

import org.junit.Assert;
import org.junit.Test;

public class StemmerTest {
  public String getStemmedWord(String w) {
    Stemmer stemmer = new Stemmer();
    stemmer.add(w.toCharArray(), w.length());
    stemmer.stem();
    return stemmer.toString();
  }

  @Test
  public void testStem() throws Exception {
    Assert.assertEquals("run", getStemmedWord("running"));
  }
}
