# HBase

# Shell

* list of the tables
```
list
```

* create table 
```
create 'mytable1', 'myspace'
```

* description of the table
```
describe 'mytable1'
```

* count records
```
count 'mytable1'
```

* delete table
```
drop table 'mytable1'
disable table 'mytable1'
```

* insert data, update data
```
put 'mytable1', 'row1', 'myspace:name', 'Alice'
put 'mytable1', 'row1', 'myspace:age', '30'
put 'mytable1', 'row1', 'myspace:city', 'Madrid'
```

* read data
```
scan 'mytable1'

get 'mytable1', 'row1'
```
