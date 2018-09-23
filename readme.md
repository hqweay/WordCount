---
layout: post
title: WordCount 项目总结
date: 2018-09-23
categories: blog
tags: [作业]
description: 
---

# 项目地址

[https://gitee.com/hqweay007/WordCount](https://gitee.com/hqweay007/WordCount)

# 已完成需求与分析

## 需求分析

基本功能：统计文件的字符数，单词数，行数。

扩展功能：统计代码文件的注释行数，空行数，代码行数，使用忽略词组统计单词数

将统计结果按照一定格式写入输出文件。

打包为可执行文件 `wc.exe` 。

执行方式：`wc.exe 参数` 

## 注意

- 空格，水平制表符，换行符，均算字符。
- 由空格或逗号分割开的都视为单词，且不做单词的有效性校验，例如：thi#,that视为用- - 逗号隔开的2个单词。
- -c, -w, -l参数可以共用同一个输入文件，形如：wc.exe –w –c file.c 。
- -o 必须与文件名同时使用，且输出文件必须紧跟在-o参数后面，不允许单独使用-o参数。

## 截图效果

## 参数违规

![bugs](https://raw.githubusercontent.com/hqweay/WordCount/master/images/error.jpg)

## 正常

![normal](https://raw.githubusercontent.com/hqweay/WordCount/master/images/normal.jpg)

## 采用 stop list文件

![stoplist](https://raw.githubusercontent.com/hqweay/WordCount/master/images/stoplist.jpg)



# PSP表格

| **PSP2.1**             | **PSP阶段**                             | **预估耗时（分钟）** | **实际耗时（分钟）** |
| ---------------------- | --------------------------------------- | -------------------- | -------------------- |
| · Planning             | · 计划                                  | 30                   | 20                   |
| · Estimate             | · 估计这个任务需要多少时间              | 20                   | 10                   |
| · Development          | · 开发                                  | 24*60                | 10*60                |
| · Analysis             | · 需求分析 (包括学习新技术)             | 50                   | 40                   |
| · Design Spec          | · 生成设计文档                          | 10                   | 10                   |
| · Design Review        | · 设计复审 (和同事审核设计文档)         | 10                   | 0                    |
| · Coding               | · 代码规范 (为目前的开发制定合适的规范) | 10                   | 10                   |
| · Code Review          | · 具体设计                              | 20                   | 30                   |
| · Test                 | · 具体编码                              | 24*60                | 15*60                |
| · Reporting            | · 代码复审                              | 2*60                 | 50                   |
| · Test Report          | · 报告                                  | 2*60                 | 1.5*60               |
| · Size Measurement     | · 测试报告                              | 30                   | 10                   |
| · Postmortem & Process | · 计算工作量                            | 10                   | 15                   |
| · Improvement Plan     | · 事后总结, 并提出过程改进计划          | 10                   | 20                   |
|                        | · 合计                                  | 3310                 | 1825                 |

# 解题思路

统计字符与行数，第一感觉需要用到文件的输入输出知识，然后按行读取，按字符来进行一些判断，看是否满足需求。

# 类图

![uml](https://raw.githubusercontent.com/hqweay/WordCount/master/images/Top-Level%20Package.jpg)

# 程序设计与代码说明

在正式写代码之前，我先简单地组织了一下项目。

## 一

如类图所示，首先我考虑到需求分析时的基本功能，扩展功能，高级功能这三块，我注意到每一个模块要求的功能并不多，所以打算按模块建立功能类，这样扩展就只需要新建类，当我完成基本功能去实现高级功能时，就不需要修改完成的代码。

比如我在 `BasicFeature.class` 中的代码

```java
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
   
  }

  /**
   * @param file
   * @return string of countWord
   * @throws IOException
   */
  public String countWord(File file) throws IOException {
    
  }

  /**
   * @param file
   * @return string of coutLine
   * @throws IOException
   */
  public String countLine(File file) throws IOException {
  
  }
}

```

对应于需求分析中基本功能的三个功能。

## 二

其次，需求分析中涉及到对某个名词的定义，比如以什么来分隔单词，什么是空行。于是我把这类定义放在一个配置类 `Config.class` 中，可以方便地进行修改。

比如需求中提到：

> 由空格或逗号分割开的都视为单词，且不做单词的有效性校验

于是我在 `Config.class` 写了

```java
  //统计单词的分隔符
  //按需求，只需要区分 逗号，空格
  //这样定义便于扩展
  public final static String[] supportSplits = {
          ",", " ", "."
  };
```

## 三

在编码过程中，我注意到在每个小功能中，都会涉及到文件的读取操作，于是我建立了一个配置类 `Util.class` ，抽象了一个用的最多的文件操作方法。（在之后，我还在此类中放了一些其他方法。）

```java
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
```

这样，就把一个经常用到的流程抽象为了一个方法，可以方便地调用。

## 四

当我完成了各个模块，就准备将整个流程整合起来。

按需求分析所说，最终我们需要达到的是通过命令行窗口执行 `wc.exe 参数列表`。

这个流程是一定的，所以我建立了一个操作类 `Operator.class` ，通过调用功能类的方法，达到了封装整个操作的效果。

根据用户的参数，我们需要判断出要执行哪些功能，所以我在此类中添加了如下 5 个属性。在解析用户输入的参数时，就解析出哪些操作是需要执行的。

然后在 `excuteCountOperatorToOutput()` 方法中，就开始通过调用功能类的方法执行相应功能。

```java
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
}
```

整个流程有了，就可以进行阶段性的测试。我建立了一个测试类 `Test.class`，不过在实际开发过程，我都是直接在主函数中测试的...这一点做的不是很好。

最后，需要和用户交互，我建立了 `Welcome.class` 类，这个类主要用于解析用户输入的参数。

因为涉及到与用户的交互，我创建了一个内部类 `paramException.class` ，用于相关的错误提示。

```java
class paramException {
    private ArrayList<String> errors = new ArrayList<String>();

    public ArrayList<String> getErrors() {
      return errors;
    }

    public void addError(String error) {
      this.errors.add(error);
    }
  }
```

解析用户的参数，需要解决这几个问题：

1. 无意义的输入
2. 参数的顺序（比如 -o 后面跟输出文件）
3. 对用户进行提示

完成后需要封装为 exe 可执行文件，使用的工具是 **exe4j** ，用到了张波同学在 [有关exe4j打包jar包控制台没有输出的补充](https://www.cnblogs.com/mrblankspace/p/9692998.html) 的内容。

## 五

然后涉及到了参数的解析，在命令行执行 `wc.exe -a -p`，'-a'，'-p' 都会作为 args[] 参数传进来。

下面是大体流程，其中包含一些小方法，不赘叙。

```java
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
```

# 测试过程

## 思路

整个开发流程无时无刻不在测试，系统的测试在基本功能完成后。

因为我的开发流程是：各个小功能，整体，打包 exe 。

所以测试流程也随之是先通过各个小功能，再整体流程测试，再打包为 exe 可执行文件，在命令行窗口执行。

最后一步的测试效果如 **截图效果** 所示。

如类图所示，我分了 BasicFeature.class，EXFeature.class 两个功能类，在这两个功能类里我通过创建 Main 方法实现了测试方法的效果，达到模块测试。

我还建立了一个 Test.class 作为后面的整体测试，不过没怎么用上，因为代码比较简单，直接在 main 函数执行比较方便。

比如：

```java
 Welcome welcome = new Welcome();

    String[] argsd = {
            "-c",
            "-w",
            "-l",
            "-a",
            "text.txt",
            "-e",
            "stopList.txt",
            "-o",
            "output.txt"
    };
    //解析参数
    welcome.start(argsd);
```

## 侧重点

因为需求分析中没做非常严格的规定，所以每个小功能还是比较好实现的。比如单词的分隔仅仅要求以空格，逗号来分隔，不做检验。一般只需要按行读取再做匹配。

我在输入文件中写了一些字符做测试。

还有就是与用户的交互，用户执行操作的时候什么参数都可能传进来，所以要多考虑。

## 文件用例

**text.txt**

```
//public void 
/**
*
**/
ddddd
              ssss File is a
dd ss

ssss
```

/**/ 多行注释并不会被统计为注释。

## 执行用例

1. `wc.exe -c -w -l -a text.txt -e stopList.txt -o output.txt`
2. `wc.exe -xx`
3. `wc.exe -c text.txt`
4. `wc.exe -e stopList.txt`
5. `wc.exe text.txt -o oup.txt`
6. `wc.exe fffffffffffffffffffffffffffffffffffffffffffff&&*$##%&`

在 5 这个测试中，代码执行会通过，没有指定参数，但是含有输入输出文件，程序就默认不做操作。（但是还是会进入上面提到的 Operator.class 操作类的方法中）

# 其他

用 **gitee** 管理项目，需要 **git** 相关知识。基本的配置见这篇文章

[从Git合作管理gitee项目浅谈](https://hqweay.cn/2018/02/01/git/)

因为管理不当，我在 push 代码的时候遇到了本地分支和远程分支冲突的问题，一直没弄好，最后强制上传，`git push origin master -f` 把我之前的 commits 覆盖完了...