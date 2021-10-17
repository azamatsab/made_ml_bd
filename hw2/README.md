#### Блок 1. Развертывание локального Hive
1. Развернуть локальный Hive в любой конфигурации - результаты в task1.1.png
2. Подключиться к развернутому Hive с помощью любого инструмента: Hue, Python Driver, Zeppelin - подключился через Hue (hue.png) и beeline (beeline.png)
3. Сделать скриншоты поднятого Hive и подключений в выбранными вами инструментах, добавить в репозиторий - task1.1.png, hue.png, beeline.png

#### Блок 2. Работа с Hive
1. Сделать таблицу artists в Hive и вставить туда значения, используя датасет https://www.kaggle.com/pieca111/music-artists-popularity
        
        CREATE TABLE artists (mbid VARCHAR(100), artist_mb VARCHAR(100), artist_lastfm VARCHAR(200), country_mb VARCHAR(100), country_lastfm VARCHAR(100), tags_mb VARCHAR(300), tags_lastfm VARCHAR(300), listeners_lastfm INT, scrobbles_lastfm INT, ambiguous_artist BOOLEAN) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',';

        LOAD DATA LOCAL INPATH "artists.csv" OVERWRITE INTO TABLE artists;

2. a. Исполнителя с максимальным числом скробблов:

        SELECT t.artist_lastfm FROM (SELECT artist_lastfm, scrobbles_lastfm FROM `default`.`artists` ORDER BY scrobbles_lastfm DESC LIMIT 1) t;
 
Результат:
    
        1   The Beatles

b. Самый популярный тэг на ластфм:

        SELECT tags, sum(scrobbles_lastfm) as total FROM artists LATERAL VIEW explode(SPLIT(tags_lastfm, ";")) adTable AS tags GROUP BY tags ORDER BY total DESC LIMIT 1;

Результат:
         
 	    tags	total
 
       seen live	83227766375

Тут использовал __LATERAL VIEW explode(SPLIT(tags_lastfm, ";"))__ так как у одной песни могут быть несколько жанров, поэтому я их поделил по делиметру __;__

c. Самые популярные исполнители 10 самых популярных тегов ластфм

        SELECT DISTINCT k.scrobbles_lastfm, k.artist_lastfm FROM 
            (SELECT f.tags, f.artist_lastfm, f.scrobbles_lastfm FROM 
                (SELECT tags, artist_lastfm, scrobbles_lastfm 
                    FROM artists LATERAL VIEW explode(SPLIT(tags_lastfm, ";")) intable AS tags) f WHERE f.tags in
            (SELECT g.tags FROM 
                (SELECT t.tags, sum(scrobbles_lastfm) as total FROM 
                    (SELECT tags, artist_lastfm, scrobbles_lastfm FROM artists LATERAL VIEW explode(SPLIT(tags_lastfm, ";")) intable AS tags) t 
                        GROUP BY t.tags ORDER BY total DESC LIMIT 10) g)) k ORDER BY k.scrobbles_lastfm DESC LIMIT 10;
                        
Результат:
        
 	    k.scrobbles_lastfm	        k.artist_lastfm
    1	517126254	            The Beatles
    2	499548797	            Radiohead
    3	360111850	            Coldplay
    4	344838631	            Muse
    5	332306552	            Arctic Monkeys
    6	313236119	            Pink Floyd
    7	294986508	            Linkin Park
    8	293784041	            Red Hot Chili Peppers
    9	285469647	            Lady Gaga
    10	281172228	            Metallica
                        
10 популярных тэгов:

 	        g.tags
    1	 seen live
    2	 rock
    3	 alternative
    4	 pop
    5	 indie
    6	 american
    7	 electronic
    8	 alternative rock
    9	 male vocalists
    10	 indie rock
 
d. Любой другой инсайт на ваше усмотрение: Исполнитель с самым большим количеством слушателей

        SELECT t.artist_lastfm FROM (SELECT artist_lastfm, listeners_lastfm FROM `default`.`artists` ORDER BY listeners_lastfm DESC LIMIT 1) t;
        
Результат:

 	        t.artist_lastfm
    1	Coldplay
