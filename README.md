Download compiled jar files:
```
https://drive.google.com/drive/folders/1lQYFIg-xvXOaPY-vL2Da3XrhDfHQSd-l
```

2 maven projects

* BTL: normal svm

* Cascade: cascade svm with hadoop

For each project, run:

```
mvn install
```

to compile .jar files

## Cascade SVM

1. File test và train tương ứng là files/a1a.t và data-d.txt

2. Chạy:

```bash
./createfolder.sh # copy file to hdfs
./runhadoop.sh # train & predict
```
