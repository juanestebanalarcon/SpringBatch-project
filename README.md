# Spring batch app

* H2 Database
* SpringBoot
* Spring Batch
* Maven
* Java 1.8

### Remember
A Job:
- Job Instance: **When / Unique** 
    - Job execution: **Hour** 
        It can runs again if execution was failed
      - Successfully / failed
    - A job can have multiple **Steps**
    - ItemReader, ItemWriter: mandatory.
    - ItemProcessor: Optional
* CronMaker: http://www.cronmaker.com/

### Flat File - Item Reader

- Source location or CSV File.
- Line Mapper:
  - delimiter
  - column headers
  - Line tokenizer, bean mapper.
