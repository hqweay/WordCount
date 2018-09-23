import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * 工具类
 */
public class Util {
  /**
   * 把获取输入流独立出来
   *
   * @param file
   * @return bufferedReader
   */
  public BufferedReader getBufferedReader(File file) {
    InputStreamReader inputStreamReader = null;
    try {
      inputStreamReader = new InputStreamReader(new FileInputStream(file));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      //可能出错勒
    }
    return new BufferedReader(inputStreamReader);
  }

  /**
   * 返回 ArrayList
   * contains 便于判断是否含有某元素
   *
   * @param file
   * @return
   * @throws IOException
   */
  public ArrayList<String> getStopList(File file) throws IOException {
    ArrayList<String> stopList = new ArrayList<String>();
    String line = "";
    BufferedReader bufferedReader = this.getBufferedReader(file);

    while ((line = bufferedReader.readLine()) != null) {
      String[] stopListArray = line.split(" ");
      for (String stopItem : stopListArray) {
        stopList.add(stopItem);
      }
    }

    return stopList;
  }

  /**
   * 把统计结果写入文件
   *
   * @param outputTexts
   * @param file
   */
  public void writeToFile(ArrayList<String> outputTexts, File file) {
    BufferedWriter out = null;
    try {
      out = new BufferedWriter(new OutputStreamWriter(
              new FileOutputStream(file, false)));
      for (String line : outputTexts) {
        out.write(line);
        out.write("\r\n");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        out.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
