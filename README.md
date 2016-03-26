# HASCLogger計測データの時間&TAG削除ツール
HASC Loggerで計測したデータの左2列分(時間,TAG)の情報を取り除くツール

## 使い方
1. `data`ディレクトリに変換するデータを入れる
2. `javac RemoveTimeAndTag.java`でコンパイル
3. `java RemoveTimeAndTag`で実行
4. `output`ディレクトリに変換されたデータが生成
   **利用したあとはoutputディレクトリを削除**

```
.  
data  
├── hasc-20160226-182122-acc.log
├── hasc-20160226-182122-gyro.log
├── hasc-20160226-182122-mag.log
└── ...
```

Developed by icchi  
2016/02/26
