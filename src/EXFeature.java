import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 扩展功能
 * 统计代码相关信息
 * 代码行 空行 注释行
 * 统计单词并采用 stopList
 */
public class EXFeature {

  Util util = new Util();

  /**
   * 代码行 空行 注释行
   *
   * @param file
   * @return
   * @throws IOException
   */
  public String countCodeInfo(File file) throws IOException {
    BufferedReader bufferedReader = util.getBufferedReader(file);
    int codeCount = 0;
    int blankCount = 0;
    int commentCount = 0;

    String line = "";

    while ((line = bufferedReader.readLine()) != null) {

      //删除 起始 结尾 的空格符 去掉格式控制符的影响
      line = line.trim();

      //判断空行
      if (this.isLineBlank(line)) {
        blankCount++;
      }

      //判断注释
      if (this.isLineComment(line)) {
        commentCount++;
      }

      //判断代码行
      if (!this.isLineBlank(line) && !this.isLineComment(line) && this.isLineCode(line)) {
        codeCount++;
      }
    }
    bufferedReader.close();
    System.out.println(file.getName() + ", 代码行/空行/注释行: " + codeCount + "/" + blankCount + "/" + commentCount + "/");
    return file.getName() + ", 代码行/空行/注释行: " + codeCount + "/" + blankCount + "/" + commentCount + "/";
  }

  /**
   * 统计单词并忽略 stopList 中的 单词
   *
   * @param file
   * @param stopListFile
   * @return
   * @throws IOException
   */
  public String countWordByStopList(File file, File stopListFile) throws IOException {
    ArrayList<String> stopList = util.getStopList(stopListFile);
    BufferedReader bufferedReader = util.getBufferedReader(file);
    int wordCount = 0;
    String line = "";
    String[] words = {};
    while ((line = bufferedReader.readLine()) != null) {
      //判断空行
      if (!line.equals("")) {
        for (String index : Config.supportSplits) {
          line = line.replace(index, "-");
        }
        words = line.split("-");
        for (String word : words) {
          if (!stopList.contains(word)) {
            wordCount++;
          }
        }
      }
    }
    bufferedReader.close();
    System.out.println(file.getName() + ", 单词数: " + wordCount);
    return file.getName() + ", 单词数: " + wordCount;
  }


  private boolean isLineComment(String line) {

    //匹配行级别的注释
    String regexRowIsComment = "\\/\\/[^\\n]*";

    if (line.matches(regexRowIsComment)) {
      return true;
    } else {
      return false;
    }
  }

  private boolean isLineBlank(String line) {
//    int len = line.length();
    //判断是否为空
    if ("".equals(line)) {
      return true;

      //格式控制符的时候 不能忽略空格影响？
      //好像没什么问题
//    }else if ((len == 1) && ((line.equals("{")) || (line.equals("}")) || (line.equals(" ")) )){
    } else if (((line.equals("{")) || (line.equals("}")) || (line.equals(" ")))) {
      return true;
    } else {
      return false;
    }

  }

  private boolean isLineCode(String line) {

    //本行包括多于一个字符的代码
    if (line.length() > 1) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 测试方法
   *
   * @param args
   */
  public static void main(String[] args) {


    File directory = new File("");// 设定为当前文件夹
    String wordCountFilePath = "src/static/text.txt";
    File file = new File(wordCountFilePath);

    String stopListFilePath = "src/static/stopList.txt";
    File stopListFile = new File(stopListFilePath);


    EXFeature exFeature = new EXFeature();

    try {
      exFeature.countWordByStopList(file, stopListFile);
    } catch (IOException e) {
      e.printStackTrace();
    }


  }
}
