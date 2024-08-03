DISTINCT :去重 在表中，一个列可能会包含多个重复值，有时您也许希望仅仅列出不同（distinct）的值。
JION 连接多个表
```sql
# 交集 返回id相同的name 和 count字段
SELECT 表1.name 表2.count
FROM 表1
INNER JOIN 表2
ON 表1.id=表2.id
```
```sql
# 左表有返回行,没有不返回; 右表没有返回null
SELECT 表1.name 表2.count
FROM 表1
LEFT  JOIN 表2
ON 表1.id=表2.id
```
```sql
# 同上
SELECT 表1.name 表2.count
FROM 表1
RIGHT JOIN 表2
ON 表1.id=表2.id
```
```sql
# 左右有一个匹配, 即返回行
SELECT 表1.name 表2.count
FROM 表1
FULL OUTER JOIN 表2
ON 表1.id=表2.id
```
