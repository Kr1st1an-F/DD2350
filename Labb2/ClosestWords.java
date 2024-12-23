/* Labb 2 i DD2350 Algoritmer, datastrukturer och komplexitet    */
/* Se labbinstruktionerna i kursrummet i Canvas                  */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */
import java.util.LinkedList;
import java.util.List;

public class ClosestWords {
  LinkedList<String> closestWords = null;

  int closestDistance = -1;

  /*
  int partDist(String w1, String w2, int w1len, int w2len) {
    if (w1len == 0)
      return w2len;
    if (w2len == 0)
      return w1len;
    int res = partDist2(w1, w2, w1len - 1, w2len - 1) +
	(w1.charAt(w1len - 1) == w2.charAt(w2len - 1) ? 0 : 1);
    int addLetter = partDist2(w1, w2, w1len - 1, w2len) + 1;
    if (addLetter < res)
      res = addLetter;
    int deleteLetter = partDist2(w1, w2, w1len, w2len - 1) + 1;
    if (deleteLetter < res)
      res = deleteLetter;
    return res;
  }
  */

  /*
  int partDist(String w1, String w2, int w1len, int w2len) {
    int[][] dp = new int[w1len + 1][w2len + 1]; // delproblemen (m matris)

    for(int i = 0; i <= w1len; ++i) {
        for(int j = 0; j <= w2len; ++j) {
            if(i == 0) {
              dp[i][j] = j;
            } else if(j == 0) {
              dp[i][j] = i;
            } else if (w1.charAt(i - 1) == w2.charAt(j - 1)) {
                dp[i][j] = dp[i - 1][j - 1];
            } else {
                dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
            }
        }
    }

    return dp[w1len][w2len];
  }
  */

  /*
  int partDist(String w1, String w2, int w1len, int w2len) {
    if(w1len < w2len)
      return partDist(w2, w1, w2len, w1len);
    if(w1len == 0)
      return w2len;
    if(w2len == 0)
      return w1len;

    int[] previousRow = new int[w2len+1];
    int[] currentRow = new int[w2len+1];

    for(int i = 0; i <= w2len; i++)
      previousRow[i] = i;
    for(int i = 1; i <= w1len; i++) {
      currentRow[0] = i;
      char w1c = w1.charAt(i-1);
      for(int j = 1; j <= w2len; j++) {
        int cost = (w1c == w2.charAt(j-1)) ? 0 : 1;
        currentRow[j] = Math.min(currentRow[j-1]+1, Math.min(previousRow[j-1]+cost, previousRow[j]+1));
      }
      int[] temp = previousRow;
      previousRow = currentRow;
      currentRow = temp;
    }

    return previousRow[w2len];
  }
  */

  int[][] dp = new int[40][40];
  String lastWord = "";

  int partDist(String w1, String w2, int w1len, int w2len) {
    int skips = 1;
    for(int i = 0; i < Math.min(w2len, lastWord.length()); i++) {
      if(w2.charAt(i) == lastWord.charAt(i))
        skips++;
      else
        break;
    }

    for(int i = 1; i <= w1len; ++i) {
      for(int j = skips; j <= w2len; ++j) {
        int cost = (w1.charAt(i - 1) == w2.charAt(j - 1)) ? 0 : 1;
        dp[i][j] = Math.min(Math.min(dp[i - 1][j]+1, dp[i][j - 1]+1), dp[i - 1][j - 1]+cost);
      }
    }

    lastWord = w2;
    return dp[w1len][w2len];
  }

  int distance(String w1, String w2) {
    return partDist(w1, w2, w1.length(), w2.length());
  }

  /*
  public ClosestWords(String w, List<String> wordList) {
    for (String s : wordList) {
      int dist = distance(w, s);
      // System.out.println("d(" + w + "," + s + ")=" + dist);
      if (dist < closestDistance || closestDistance == -1) {
        closestDistance = dist;
        closestWords = new LinkedList<String>();
        closestWords.add(s);
      }
      else if (dist == closestDistance)
        closestWords.add(s);
    }
  }
  */

  public ClosestWords(String w, List<String> wordList) {
    for(int i = 0; i < 40; i++) {
      dp[0][i] = i;
      dp[i][0] = i;
    }

    for (String s : wordList) {
      // if we have to remove or add more letters than the current closest distance then we already know it is not closer
      if(closestDistance != -1 && Math.abs(w.length()-s.length()) > closestDistance)
        continue;

      int dist = distance(w, s);
      //System.out.println("d(" + w + "," + s + ")=" + dist);
      if (dist < closestDistance || closestDistance == -1) {
        closestDistance = dist;
        closestWords = new LinkedList<String>();
        closestWords.add(s);
      }
      else if (dist == closestDistance)
        closestWords.add(s);
    }

  }

  int getMinDistance() {
    return closestDistance;
  }

  List<String> getClosestWords() {
    return closestWords;
  }
}
