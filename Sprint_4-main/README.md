# Sprint_4
---
запуск в Chrome
---
---
mvn test

---
запуск с Firefox
---
---
mvn -Dbrowser=firefox test.

---
если mvn test падает с ошибкой 500, и не находит бинарник, то запускаем с параметром
---
---
mvn -Dbrowser=firefox -Dwebdriver.firefox.bin=/usr/bin/firefox test