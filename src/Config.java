import java.io.*;

/**
 * 统计一个文本的字符数，单词数，行数
 */
public class Config {
  //统计支持的字符
  //默认要加上换行
  //实际没有用到，默认每读取一个字符都算作字符
  public final static char[] supportChars = {
          'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
          'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
          'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
          'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
          '\t', ' '
  };

  public final static int supportCharsLen = supportChars.length;

  //统计单词的分隔符
  //按需求，只需要区分 逗号，空格
  //这样定义便于扩展
  public final static String[] supportSplits = {
          ",", " ", "."
  };

  //支持的命令
  public final static String[] supportCommands = {
          "-c", "-w", "-l", "-o", "-a", "-e"
  };


}
