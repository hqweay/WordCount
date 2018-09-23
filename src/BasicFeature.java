import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

/**
 * 基本功能
 * 统计 字符，单词，行数
 */
public class BasicFeature {
  Util util = new Util();

  /**
   * @param file
   * @return string of countChar
   * @throws IOException
   */
  public String countChar(File file) throws IOException {
    BufferedReader bufferedReader = util.getBufferedReader(file);
    int charCount = 0;
    String line = "";
    while ((line = bufferedReader.readLine()) != null) {
      //换行符占一个
      charCount++;
      int lineLen = line.length();
      //遍历字符
//      for (int j = 0; j < supportCharsLen; j++) {
//        for (int i = 0; i < lineLen; i++) {
//          if (line.charAt(i) == supportChars[j]) {
//            charCount++;
//          }
//        }
//      }
      //不从 supportChars 判断
      for (int i = 0; i < lineLen; i++) {
        charCount++;
      }
    }
    bufferedReader.close();
    charCount--;
    System.out.println(file.getName() + ", 字符数: " + charCount);
    return file.getName() + " ,字符数: " + charCount;
  }

  /**
   * @param file
   * @return string of countWord
   * @throws IOException
   */
  public String countWord(File file) throws IOException {
    BufferedReader bufferedReader = util.getBufferedReader(file);
    int wordCount = 0;
    String line = "";
    String[] words = {};

    while ((line = bufferedReader.readLine()) != null) {
      line = line.trim();

      //判断空行
      if (!line.equals("")) {
        for (String index : Config.supportSplits) {
          line = line.replace(index, "-");
        }
        words = line.split("-");
        wordCount += words.length;
      } else {
      }
    }
    bufferedReader.close();
    System.out.println(file.getName() + ", 单词数: " + wordCount);
    return file.getName() + ", 单词数: " + wordCount;
  }

  /**
   * @param file
   * @return string of coutLine
   * @throws IOException
   */
  public String countLine(File file) throws IOException {
    BufferedReader bufferedReader = util.getBufferedReader(file);
    int lineCount = 0;
    String line = "";
    while ((line = bufferedReader.readLine()) != null) {
      lineCount++;
    }
    bufferedReader.close();
    System.out.println(file.getName() + ", 行数: " + lineCount);
    return file.getName() + ", 行数: " + lineCount;
  }
}
