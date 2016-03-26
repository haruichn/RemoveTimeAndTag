/*
* Title：Remove Time and Tag
* 説明 ： HASCLoggerデータの時間とTAG部分を消去する
* @date Created on: 2016/02/26
* @author Author: Haruyuki Ichino
* @version 1.0
*
*/

import java.io.File;

import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.LineNumberReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;

public class RemoveTimeAndTag {
   public static void main(String[] args){
      // データの場所指定
      String data_path = "./data/";
      // 軸補正後のデータの格納場所
      String output_path = "./output/";

      // もし出力フォルダがなければ作成
      File output_dir = new File(output_path);
      if(output_dir.exists() == false){
         output_dir.mkdir();
      }


      // 通常のファイル(隠しファイルでない)のみを取り出すフィルタの作成
      FilenameFilter normalFileFilter = new FilenameFilter() {
         public boolean accept(File file, String name) {
            if (file.isHidden() == false){
               return true;
            } else {
               return false;
            }
         }
      };
      // 加速度ファイルのみを取り出すフィルタの作成
      FilenameFilter accFileFilter = new FilenameFilter() {
         public boolean accept(File file, String name) {
            if (name.matches(".*acc.*")){
               return true;
            } else {
               return false;
            }
         }
      };

      System.out.println("========================================================================");
      System.out.println("1.ファイルの読み込み");
      System.out.println("========================================================================");
      File data_dir = new File(data_path);

      // data内のファイルを取得
      File[] files = data_dir.listFiles(normalFileFilter);

      System.out.println("file count = " + files.length);

      // 各ファイルにアクセス
      for(File file : files){

         String file_name = file.getName();
         System.out.println(file_name);

         dispFileLineSize(file);

         // 出力ディレクトリに軸補正用のファイルを作成
         File output_file = new File(output_dir.getPath()+"/"+file_name);
         if(output_file.exists() == false){
            try{
               output_file.createNewFile();
            }catch(IOException e){
               e.printStackTrace();
            }
         }

         // ファイル内容にアクセスしファイルに追加
         try {
            //ファイルを読み込む
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            //出力先を作成
            FileWriter fw = new FileWriter(output_file, true);  //追記モードでファイル展開
            PrintWriter pw = new PrintWriter(new BufferedWriter(fw));


            //読み込んだファイルを１行ずつ処理する
            String line_str;
            StringTokenizer token;
            for(int i=0; (line_str = br.readLine()) != null; i++){
               //区切り文字TABで分割する
               token = new StringTokenizer(line_str, "\t");

               // System.out.println(file.getName());
               //分割した文字を配列に代入
               for(int j=0; token.hasMoreTokens(); j++){
                  String element = token.nextToken();
                  if(j==2){
                     // 内容を書き込む
                     System.out.println(element);
                     pw.println(element);
                  }
               }
            }

            //ファイル読み込みの終了処理
            br.close();
            //ファイル書き込みの終了処理
            pw.close();

         } catch (IOException ex) {
            //例外発生時処理
            ex.printStackTrace();
         }
      }
   }

   static void disp2dArray(float[][] data){
      for(int i=0; i<2; i++){
         for(int j=0; j<data[0].length; j++){
            System.out.print(data[i][j] + " ");
         }
         System.out.println();
      }

      for(int i=0; i<2; i++){
         System.out.println("\t\t.");
      }

      for(int i=data.length-2; i<data.length; i++){
         for(int j=0; j<data[0].length; j++){
            System.out.print(data[i][j] + " ");
         }
         System.out.println();
      }
   }

   static void dispFileLineSize(File file){
      System.out.print("(行数, 列数) = (");
      System.out.print(getLineNumber(file));
      System.out.print(", ");
      System.out.print(getRowNumber(file));
      System.out.println(")");
   }

   static int getLineNumber(File file){
      int line_number = -1;
      String aLine;
      LineNumberReader file_lnr = null;

      try{
         file_lnr = new LineNumberReader(new FileReader(file));
         // 最後の行まで選択行をすすめる
         while(null!=(aLine = file_lnr.readLine())){}
         line_number = file_lnr.getLineNumber();
      }catch(IOException e){
         //例外発生時処理
         e.printStackTrace();
      }
      return line_number;
   }

   static int getRowNumber(File file){
      int row_number = -1;

      try {
         //ファイルを読み込む
         FileReader fr = new FileReader(file);
         BufferedReader br = new BufferedReader(fr);

         //最初の１行目の文字列を取得
         String line = br.readLine();

         //区切り文字","で分割する
         StringTokenizer token = new StringTokenizer(line, ",");

         // ,で分割された数(1行の要素数)を取得
         row_number = token.countTokens();

         //終了処理
         br.close();

      } catch (IOException ex) {
         //例外発生時処理
         ex.printStackTrace();
      }
      return row_number;
   }

}
