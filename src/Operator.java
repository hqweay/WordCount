import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 执行统计操作
 */
class Operator {
  private boolean countChar = false;
  private boolean countWord = false;
  private boolean countWordByStopList = false;
  private boolean countLine = false;
  private boolean countCodeInfo = false;

  /**
   * 执行一系列的统计操作
   *
   * @param file
   * @param stopListFile
   * @return
   */
  public ArrayList<String> excuteCountOperatorToOutput(File file, File stopListFile) {
    ArrayList<String> outputTexts = new ArrayList<String>();
    //功能类
    BasicFeature basicFeature = new BasicFeature();
    EXFeature exFeature = new EXFeature();
    try {
      if (countChar) {
        outputTexts.add(basicFeature.countChar(file));
      }
      if (countWord) {
        outputTexts.add(basicFeature.countWord(file));
      }
      if (countWordByStopList) {
        outputTexts.add(exFeature.countWordByStopList(file, stopListFile));
      }
      if (countLine) {
        outputTexts.add(basicFeature.countLine(file));
      }
      if (countCodeInfo) {
        outputTexts.add(exFeature.countCodeInfo(file));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return outputTexts;
  }

  public boolean isCountChar() {
    return countChar;
  }

  public void setCountChar(boolean countChar) {
    this.countChar = countChar;
  }

  public boolean isCountWord() {
    return countWord;
  }

  public void setCountWord(boolean countWord) {
    this.countWord = countWord;
  }

  public boolean isCountWordByStopList() {
    return countWordByStopList;
  }

  public void setCountWordByStopList(boolean countWordByStopList) {
    this.countWordByStopList = countWordByStopList;
  }

  public boolean isCountLine() {
    return countLine;
  }

  public void setCountLine(boolean countLine) {
    this.countLine = countLine;
  }

  public boolean isCountCodeInfo() {
    return countCodeInfo;
  }

  public void setCountCodeInfo(boolean countCodeInfo) {
    this.countCodeInfo = countCodeInfo;
  }
}