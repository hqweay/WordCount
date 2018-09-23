import java.util.ArrayList;

public class Test {
  public static void main(String[] ss) {
    String[] args = {
            "-c",
            "ss.txt",
            "-o",
            "output.txt"
    };

    Welcome welcome = new Welcome();
    welcome.start(args);

  }
}
