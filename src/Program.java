


import java.io.File;
import java.util.ArrayList;

public class Program {

  public static void main(String[] args) {

    Util util = new Util();

    Welcome welcome = new Welcome();

//    String[] argsd = {
//            "-c",
//            "-w",
//            "-l",
//            "-a",
//            "text.txt",
//            "-e",
//            "stopList.txt",
//            "-o",
//            "output.txt"
//    };
    //解析参数
    welcome.start(args);

    //获取参数中的需要同级的文件
    String wordCountFilePath = welcome.getWordCountFilePath();


    File file = new File(wordCountFilePath);
    //获取 stop list
    String stopListFilePath = welcome.getStopListFilePath();

    File stopListFile = new File(stopListFilePath);

    //执行操作，获取统计结果，存入 arrayList
    ArrayList<String> outputText = welcome.operator.excuteCountOperatorToOutput(file, stopListFile);

    //获取写入文件
    String outputFilePath = welcome.getOutputFilePath();

    File outputFile = new File(outputFilePath);
    //将统计结果写入文件
    util.writeToFile(outputText, outputFile);


//    File directory = new File("");// 设定为当前文件夹
//
//    try {
//      System.out.println(directory.getCanonicalPath());// 获取标准的路径
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//    System.out.println(directory.getAbsolutePath());// 获取绝对路径

  }
}
