spring-transactions-example

propagation: deals with how multiple transactions can communicate with each other
isolation: controls how one transaction sees the changes made by another transaction


### POST Request

```
curl -X 'POST' \
  'http://localhost:9191/api/orders' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": 103,
  "productId": 1,
  "quantity": 5
}'
```
### PRODUCT DB SQL
```
INSERT INTO Product (name,price,stockQuantity) VALUES
	 ('Laptop',25000.0,10),
	 ('Samrtphone',5000.0,12);
```
## SECOND Phase
```
insert into product (name, price, stockQuantity) values
		('Watch', 1500.0, 50),
		('TV', 7500.0, 4);
```