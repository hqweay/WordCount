import java.io.File;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;

/**
 * 命令匹配类
 */
public class Welcome {

  class paramException {
    private ArrayList<String> errors = new ArrayList<String>();

    public ArrayList<String> getErrors() {
      return errors;
    }

    public void addError(String error) {
      this.errors.add(error);
    }
  }

  //初始化各个文件路径
  private String wordCountFilePath = "";
  private String stopListFilePath = "";
  private String outputFilePath = "output.txt";

  ArrayList<String> filePaths = new ArrayList<String>();
  Operator operator = new Operator();
  paramException paramException = new paramException();

  public void start(String[] args) {
    if (!paramsExecute(args)) {
      printError();
      System.exit(0);
    }

    if (!validPathParam()) {
      printError();
      System.exit(0);
    }
  }

  public void printError() {
    System.out.println("---------Error--------");
    System.out.println(paramException.getErrors().toString());
    System.out.println("----------------------");
  }

  public boolean paramsExecute(String[] args) {
    for (int i = 0; i < args.length; i++) {
      String param = args[i];
      switch (param) {
        case "-c":
          countChar();
          break;
        case "-w":
          countWord(args);
          break;
        case "-l":
          countLine();
          break;
        case "-a":
          countCodeInfo();
          break;
        case "-o":
          if (i < args.length - 1 && args[i + 1].contains(".")) {
            //输出文件 ok
          } else {
            String error = "没找到输出文件 " + args[i];
            paramException.addError(error);
            return false;
          }
          break;
        case "-e":
          if (i < args.length - 1 && args[i + 1].contains(".")) {
            // count word by stop list
            operator.setCountWordByStopList(true);
            operator.setCountWord(false);
          } else {
            String error = "参数 " + args[i] + " 后没找到 stop list 文件 ";
            paramException.addError(error);
            return false;
          }
          break;
        default:
          //文件名或者报错
          filePaths.add(param);
          break;
      }
    }
    return true;
  }

  public boolean validPathParam() {
    ArrayList<String> supportCommands = new ArrayList<String>();
    for (String command : Config.supportCommands) {
      supportCommands.add(command);
    }
    //先把参数格式出错解决了
    for (String filePath : filePaths) {
      //不是文件名 但是 也没加 -

      if (filePath.charAt(0) != '-' && !filePath.contains(".")) {
        String error = "参数前需要加 - ,如 -w";
        paramException.addError(error);
        return false;
        //是参数的格式 但是不支持该参数
      } else if (filePath.charAt(0) == '-' && !supportCommands.contains(filePath)) {
        String error = "没有找到该参数 " + filePath;
        paramException.addError(error);
        return false;
      }
    }

    if (filePaths.size() > 3) {
      //文件路径不可能大于三
      //参数报错
      String error = "出错了，请检查一下参数";
      paramException.addError(error);
      return false;
    } else if (filePaths.size() == 3) {
      this.wordCountFilePath = filePaths.get(0);
      this.stopListFilePath = filePaths.get(1);
      this.outputFilePath = filePaths.get(2);
    } else if (filePaths.size() == 2) {
      this.wordCountFilePath = filePaths.get(0);
      this.outputFilePath = filePaths.get(1);
    } else {
      String error = "参数不足, 至少应包括输入输出文件。";
      paramException.addError(error);
      return false;
    }
    return true;
  }

  private void countChar() {
    operator.setCountChar(true);
  }

  private void countWord(String[] args) {
    boolean isCountByList = false;
    for (int j = 0; j < args.length; j++) {
      if ("-e" == args[j] && j < args.length - 1 && args[j + 1].indexOf(".") == 1) {
        //-w -e file.txt
        isCountByList = true;
      }
    }
    if (isCountByList) {
      //count word by stop list
      operator.setCountWordByStopList(true);
    } else {
      //count word
      operator.setCountWord(true);
    }
  }

  private void countLine() {
    //System.out.println("count line");
    operator.setCountLine(true);
  }

  private void countCodeInfo() {
    operator.setCountCodeInfo(true);
    //System.out.println("code info");
  }

  public String getWordCountFilePath() {
    return wordCountFilePath;
  }

  public String getStopListFilePath() {
    return stopListFilePath;
  }

  public String getOutputFilePath() {
    return outputFilePath;
  }
}
