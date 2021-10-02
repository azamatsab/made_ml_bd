## Блок 1
   1. compose файл можно посмотреть в docker-compose.yml
   2. 3 Скриншоты в папке __screens__

## Блок 2
1. Создайте папку в корневой HDFS-папке:
         
         hdfs dfs -mkdir /task1

2. Создайте в созданной папке новую вложенную папку
         
         hdfs dfs -mkdir /task1/task2

3. Что такое Trash в распределенной FS?
Hadoop trash, как и обычная корзина, защищает от случайного удаления файлов или папок. Когда файл из HDFS удаляется, он не сразу стирается из HDFS. Удаленные файлы перемещаются в /user/<username>/.Trash/Current directory

Как сделать так, чтобы файлы удалялись сразу, минуя “Trash”? Использовать опцию -skipTrash для rm

4. Создайте пустой файл в подпапке из пункта 2
         
         hdfs dfs -touchz /task1/task2/task4

5. Удалите созданный файл
         
         hdfs dfs -rm -skipTrash /task1/task2/task4

6. Удалите созданные папки
         
         hdfs dfs -rm -R -skipTrash /task1

1. Скопируйте любой в новую папку на HDFS
         
         hdfs dfs -put test /new_directory

2. Выведите содержимое HDFS-файла на экран
         
         hdfs dfs -cat /new_directory/test
   
3. Выведите содержимое нескольких последних строчек HDFS-файла на экран
         
         hdfs dfs -tail /new_directory/test

4. Выведите содержимое нескольких первых строчек HDFS-файла на экран
         
         hdfs dfs -head /new_directory/test

5. Переместите копию файла в HDFS на новую локацию
         
         hdfs dfs -cp /new_directory/test /

2. Изменить replication factor для файла. Как долго занимает время на увеличение /
уменьшение числа реплик для файла?
         
         hdfs dfs -setrep -w 2 /test  ~ 10 sec
         hdfs dfs -setrep -w 3 /test  ~ 10 sec

3. Найдите информацию по файлу, блокам и их расположениям с помощью “hdfs fsck”
         
         hdfs fsck /prices.csv -files -blocks -locations

Результат команды в __command_results_screen/fsck_file_block_location.png__

4. Получите информацию по любому блоку из п.2 с помощью "hdfs fsck -blockId”. Обратите внимание на Generation Stamp (GS number)
         
         hdfs fsck -blockId blk_1073742445

Результат команды в __command_results_screen/fsck_blockId.png__

## Блок 3
         mapred streaming -input /prices.csv -output out00 -mapper 'python3 mapper.py' -reducer 'python3 reducer.py' -file mapper.py -file reducer.py
